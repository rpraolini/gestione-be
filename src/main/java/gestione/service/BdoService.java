package gestione.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestione.dto.BdoDto;
import gestione.dto.BdoLineaDto;
import gestione.dto.ConsuntivoDto;
import gestione.mapper.BdoLineaMapper;
import gestione.mapper.BdoMapper;
import gestione.mapper.ConsuntivoMapper;
import gestione.mapper.TaskMapper;
import gestione.model.Bdo;
import gestione.model.BdoLinea;
import gestione.model.Consuntivo;
import gestione.repository.BdoLineaRepository;
import gestione.repository.BdoRepository;
import gestione.repository.ConsuntivoRepository;
import gestione.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BdoService {

	private final BdoRepository bdoRepository;
	private final BdoLineaRepository bdoLineaRepository;
	private final ConsuntivoRepository consuntivoRepository;
	private final TaskRepository taskRepository;

	private final BdoMapper bdoMapper;
	private final BdoLineaMapper bdoLineaMapper;
	private final TaskMapper taskMapper;
	private final ConsuntivoMapper consuntivoMapper;

	private final ConsuntivoService consuntivoService;

	public BdoService(BdoRepository bdoRepository, BdoLineaRepository bdoLineaRepository, BdoMapper bdoMapper,
			BdoLineaMapper bdoLineaMapper, TaskMapper taskMapper, ConsuntivoMapper consuntivoMapper,
			ConsuntivoService consuntivoService, ConsuntivoRepository consuntivoRepository,
			TaskRepository taskRepository) {
		this.bdoRepository = bdoRepository;
		this.bdoLineaRepository = bdoLineaRepository;
		this.bdoMapper = bdoMapper;
		this.bdoLineaMapper = bdoLineaMapper;
		this.taskMapper = taskMapper;
		this.consuntivoService = consuntivoService;
		this.consuntivoRepository = consuntivoRepository;
		this.consuntivoMapper = consuntivoMapper;
		this.taskRepository = taskRepository;
	}

	// --- Metodi CRUD base per BDO ---

	public List<BdoDto> findAll() {
		return bdoRepository.findAll().stream().map(bdoMapper::toDto).collect(Collectors.toList());
	}

	public Optional<BdoDto> findById(Integer id) {
		return bdoRepository.findById(id).map(bdoMapper::toDto);
	}

	@Transactional // Un metodo che salva nel DB deve essere transazionale
	public BdoDto save(BdoDto dto) {
		Bdo entity = bdoMapper.toEntity(dto);
		Bdo saved = bdoRepository.save(entity);
		return bdoMapper.toDto(saved);
	}

	@Transactional // Un metodo che aggiorna nel DB deve essere transazionale
	public BdoDto update(Integer id, BdoDto dto) {
		return bdoRepository.findById(id).map(existingBdo -> {
			Bdo updatedBdo = bdoMapper.toEntity(dto);
			updatedBdo.setIdBdo(existingBdo.getIdBdo()); // Mantiene l'ID del BDO esistente
			return bdoMapper.toDto(bdoRepository.save(updatedBdo));
		}).orElseThrow(() -> new EntityNotFoundException("Bdo not found with id " + id));
	}

	@Transactional // Un metodo che elimina dal DB deve essere transazionale
	public void deleteById(Integer id) {
		if (!bdoRepository.existsById(id)) {
			throw new EntityNotFoundException("Bdo not found with id " + id);
		}

		bdoLineaRepository.deleteByBdo_IdBdo(id); // Usa il metodo corretto del repository

		bdoRepository.deleteById(id);
	}

	// Metodo per recuperare un singolo BDO con tutte le sue linee e totali
	// aggregati
	public BdoDto getBdoWithLineeAndTotals(Integer idBdo) {
		Bdo bdo = bdoRepository.findById(idBdo)
				.orElseThrow(() -> new EntityNotFoundException("BDO non trovata con ID: " + idBdo));

		List<BdoLinea> linee = bdoLineaRepository.findByBdo_IdBdo(idBdo); // Corretto a findByBdo_IdBdo

		List<BdoLineaDto> lineeDto = linee.stream().map(bdoLineaMapper::toDto).collect(Collectors.toList());

		BigDecimal totaleLinee = linee.stream().map(BdoLinea::getImportoLinea).filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);

		BdoDto dto = bdoMapper.toDto(bdo);
		dto.setLinee(lineeDto);
		dto.setImportoTotaleLinee(totaleLinee);

		BigDecimal consuntivoBdoTotale = consuntivoService.getSommaPerBdo(idBdo);
		dto.setSommaBdoConsuntivi(consuntivoBdoTotale);

		return dto;
	}


	@Transactional(readOnly = true)
	public List<BdoDto> getAllBdosWithLineeAndTotals() {
		List<Bdo> bdos = bdoRepository.findAll();

		return bdos.stream().map(bdo -> {
			BdoDto bdoDto = bdoMapper.toDto(bdo);

			List<BdoLinea> bdoLinee = bdoLineaRepository.findByBdo_IdBdo(bdo.getIdBdo());

			List<BdoLineaDto> bdoLineaDtos = bdoLinee.stream().map(linea -> {
				BdoLineaDto lineaDto = bdoLineaMapper.toDto(linea);

				// Questa lista è già usata per calcolare importoConsuntivoTotaleLinea
				List<Consuntivo> allConsuntiviForLinea = consuntivoRepository
						.findByLineaBdo_IdLineaBdoOrderByDtConsuntivoChronologicallyAsc(linea.getIdLineaBdo());
				List<ConsuntivoDto> allConsuntiviForLineaDtos = allConsuntiviForLinea.stream().map(consuntivo -> {
					ConsuntivoDto consuntivoDto = consuntivoMapper.toDto(consuntivo);
					// Popola il TaskDto annidato anche per i consuntivi a livello di linea (se hanno un task)
					taskRepository.findById(consuntivo.getTask().getIdTask()).ifPresent(taskInConsuntivo -> {
						consuntivoDto.setTaskDto(taskMapper.toDto(taskInConsuntivo));
						// Calcolo per 'sommaConsuntivi' (somma dei consuntivi per il task)

						List<Consuntivo> consuntiviTask = consuntivoRepository
								.findByTask_IdTask(consuntivoDto.getTaskDto().getIdTask());
						BigDecimal sommaTaskConsuntivi = consuntiviTask.stream().map(Consuntivo::getImportoConsuntivo)
								.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

						consuntivoDto.getTaskDto()
								.setImportoTaskResiduo((consuntivoDto.getTaskDto().getImportoTask() != null
										? consuntivoDto.getTaskDto().getImportoTask()
										: BigDecimal.ZERO).subtract(sommaTaskConsuntivi));

						consuntivoDto.getTaskDto().setSommaTaskConsuntivi(sommaTaskConsuntivi);
					});
					return consuntivoDto;
				}).collect(Collectors.toList());

				lineaDto.setConsuntivi(allConsuntiviForLineaDtos); // Imposta il campo 'consuntivi' con TUTTI i consuntivi della linea

				// Calcolo Importo Consuntivo Totale per Linea BDO
				BigDecimal sommaLineeConsuntivi = allConsuntiviForLinea.stream()
						.map(Consuntivo::getImportoConsuntivo).filter(Objects::nonNull) // Gestisce i null in importoConsuntivo
						.reduce(BigDecimal.ZERO, BigDecimal::add);

				lineaDto.setSommaLineaConsuntivi(sommaLineeConsuntivi);
				lineaDto.setImportoLineaResiduo(
						(linea.getImportoLinea() != null ? linea.getImportoLinea() : BigDecimal.ZERO)
								.subtract(lineaDto.getSommaLineaConsuntivi()));

				return lineaDto;
			}).collect(Collectors.toList());

			BigDecimal importoTotaleLinee = bdoLinee.stream().map(BdoLinea::getImportoLinea).filter(Objects::nonNull)
					.reduce(BigDecimal.ZERO, BigDecimal::add);

			BigDecimal sommaBdoConsuntivi = bdoLineaDtos.stream().map(BdoLineaDto::getSommaLineaConsuntivi)
					.filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

			bdoDto.setLinee(bdoLineaDtos);
			bdoDto.setImportoTotaleLinee(importoTotaleLinee);
			bdoDto.setSommaBdoConsuntivi(sommaBdoConsuntivi);
			bdoDto.setImportoBdoResiduo((bdoDto.getImportoTotaleLinee() != null ? bdoDto.getImportoTotaleLinee() : BigDecimal.ZERO)
					.subtract(sommaBdoConsuntivi));

			return bdoDto;
		}).collect(Collectors.toList());
	}

}