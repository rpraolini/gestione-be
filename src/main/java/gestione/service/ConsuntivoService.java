package gestione.service;

import gestione.dto.ConsuntivoDto;
import gestione.mapper.ConsuntivoMapper;
import gestione.model.BdoLinea;
import gestione.model.Consuntivo;
import gestione.model.Task;
import gestione.repository.BdoLineaRepository;
import gestione.repository.ConsuntivoRepository;
import gestione.repository.TaskRepository;
import jakarta.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsuntivoService {

    private final ConsuntivoRepository repository;
    private final ConsuntivoMapper consuntivoMapper;
    
    private final BdoLineaRepository bdoLineaRepository; 
    private final TaskRepository taskRepository;         


    public ConsuntivoService(ConsuntivoRepository repository, ConsuntivoMapper consuntivoMapper,
    		BdoLineaRepository bdoLineaRepository, TaskRepository taskRepository) {
        this.repository = repository;
        this.consuntivoMapper = consuntivoMapper;
        this.bdoLineaRepository = bdoLineaRepository;
        this.taskRepository = taskRepository;
        	
    }

    public List<ConsuntivoDto> findAll() {
        return repository.findAll()
                .stream()
                .map(consuntivoMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ConsuntivoDto> findById(Integer id) {
        return repository.findById(id).map(consuntivoMapper::toDto);
    }

    @Transactional
    public ConsuntivoDto save(ConsuntivoDto dto) {
        Consuntivo consuntivo = consuntivoMapper.toEntity(dto); // Mappa i campi base (come idConsuntivo, dtConsuntivo, importoConsuntivo)

        // 1. Recupera e imposta l'entità LineaBdo
        if (dto.getIdLineaBdo() != null) {
            BdoLinea lineaBdo = bdoLineaRepository.findById(dto.getIdLineaBdo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "LineaBdo con ID " + dto.getIdLineaBdo() + " non trovata."));
            consuntivo.setLineaBdo(lineaBdo); // Imposta l'oggetto entità LineaBdo
        } else {
            // Gestisci il caso in cui idLineaBdo è nullo (es. se è obbligatorio)
            throw new IllegalArgumentException("L'ID della LineaBdo non può essere nullo per un Consuntivo.");
        }

        // 2. Recupera e imposta l'entità Task (se l'ID del task è presente)
        if (dto.getIdTask() != null) {
            Task task = taskRepository.findById(dto.getIdTask())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task con ID " + dto.getIdTask() + " non trovato."));
            consuntivo.setTask(task); // Imposta l'oggetto entità Task
        }

        Consuntivo saved = repository.save(consuntivo); 
        return consuntivoMapper.toDto(saved); 
    }

    
    @Transactional
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public List<ConsuntivoDto> findByLineaBdoId(Integer idLineaBdo) {
        return repository.findByLineaBdo_IdLineaBdoOrderByDtConsuntivoChronologicallyAsc(idLineaBdo)
                .stream()
                .map(consuntivoMapper::toDto)
                .collect(Collectors.toList());
    }
    
    
    public List<ConsuntivoDto> findByTaskId(Integer idTask) {
        return repository.findByTask_IdTask(idTask)
                .stream()
                .map(consuntivoMapper::toDto)
                .collect(Collectors.toList());
    }
    
    public BigDecimal getSommaPerTask(Integer idTask) {
        return repository.sommaPerTask(idTask) != null ? repository.sommaPerTask(idTask) : BigDecimal.ZERO;
    }

    public BigDecimal getSommaPerLinea(Integer idLineaBdo) {
        return repository.sommaPerLinea(idLineaBdo) != null ? repository.sommaPerLinea(idLineaBdo) : BigDecimal.ZERO;
    }
    
    public BigDecimal getSommaPerBdo(Integer idBdo) {
    	return repository.getSommaConsuntiviPerBdo(idBdo) != null ? repository.getSommaConsuntiviPerBdo(idBdo) : BigDecimal.ZERO;
    }
    
    public BigDecimal getSommaPerProgetto(Integer idProgetto) {
        return repository.getSommaConsuntiviPerProgetto(idProgetto);
    }
}
