package gestione.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gestione.dto.ProgettoDto;
import gestione.dto.TaskDto;
import gestione.mapper.ProgettoMapper;
import gestione.model.Progetto;
import gestione.model.Task;
import gestione.repository.ProgettoRepository;
import gestione.repository.TaskRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgettoService {

    private final ProgettoRepository progettoRepo;
    private final TaskRepository taskRepo;
    
    private final ProgettoMapper progettoMapper;
    
    private final ConsuntivoService consuntivoService;

    public ProgettoService(ProgettoRepository progettoRepo, TaskRepository taskRepo, ConsuntivoService consuntivoService, ProgettoMapper progettoMapper) {
        this.progettoRepo = progettoRepo;
        this.taskRepo = taskRepo; 
        this.consuntivoService = consuntivoService;
        this.progettoMapper = progettoMapper;
    }

    public List<ProgettoDto> getAll() {
        return progettoRepo.findAll()
                .stream()
                .map(progettoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional 
    public ProgettoDto save(ProgettoDto dto) {
        Progetto saved = progettoRepo.save(progettoMapper.toEntity(dto));
        return progettoMapper.toDto(saved);
    }
    
    public Optional<ProgettoDto> findById(Integer id) {
    	return progettoRepo.findById(id).map(progetto -> {
            ProgettoDto dto = progettoMapper.toDto(progetto);
            BigDecimal sommaImporti = taskRepo.sommaImportiByProgetto(id);
            dto.setImportoProgetto(sommaImporti);
            return dto;
        });
    }
    
    public Optional<ProgettoDto> findByCodice(String codice) {
		return progettoRepo.findByCodProgetto(codice).map(progetto -> {
            ProgettoDto dto = progettoMapper.toDto(progetto);
            BigDecimal sommaImporti = taskRepo.sommaImportiByProgetto(progetto.getIdProgetto());
            dto.setImportoProgetto(sommaImporti);
            return dto;
        });
}
    
    public List<ProgettoDto> findAll(){
    	List<Progetto> progetti = progettoRepo.findAll();

        return progetti.stream().map(progetto -> {
            ProgettoDto dto = new ProgettoDto();
            dto.setIdProgetto(progetto.getIdProgetto());
            dto.setCodProgetto(progetto.getCodProgetto());
            dto.setDescrProgetto(progetto.getDescrProgetto());
            dto.setDtInizio(progetto.getDtInizio());
            dto.setDtFine(progetto.getDtFine());
            dto.setTipoProgetto(progetto.getTipoProgetto());

            BigDecimal somma = taskRepo.sommaImportiByProgetto(progetto.getIdProgetto());
            dto.setImportoProgetto(somma);

            return dto;
        }).toList();
    }
    
    
    @Transactional 
    public void deleteById(Integer id) {
    	progettoRepo.deleteById(id);
    }
    
    public ProgettoDto getProgettoConTasks(Integer id) {
        Progetto progetto = progettoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Progetto non trovato"));

        List<Task> tasks = taskRepo.findByProgettoIdProgetto(id);

        ProgettoDto dto = new ProgettoDto();
        dto.setIdProgetto(progetto.getIdProgetto());
        dto.setCodProgetto(progetto.getCodProgetto());
        dto.setDescrProgetto(progetto.getDescrProgetto());
        dto.setTipoProgetto(progetto.getTipoProgetto());
        dto.setDtInizio(progetto.getDtInizio());
        dto.setDtFine(progetto.getDtFine());
        
        BigDecimal somma = taskRepo.sommaImportiByProgetto(progetto.getIdProgetto());
        dto.setImportoProgetto(somma);
        
        BigDecimal importoTotaleConsuntivi = consuntivoService.getSommaPerProgetto(id);
        dto.setImportoConsuntivato(importoTotaleConsuntivi);

        List<TaskDto> taskDtos = tasks.stream()
            .map(task -> {
                TaskDto t = new TaskDto();
                t.setIdTask(task.getIdTask());
                t.setCodTask(task.getCodTask());
                t.setDescrTask(task.getDescrTask());
                t.setImportoTask(task.getImportoTask());
                dto.setDtInizio(progetto.getDtInizio());
                dto.setDtFine(progetto.getDtFine());
                t.setIdProgetto(task.getProgetto().getIdProgetto());
                
                BigDecimal sommaConsuntivatoPerTask = consuntivoService.getSommaPerTask(task.getIdTask());
                t.setSommaTaskConsuntivi(sommaConsuntivatoPerTask);
                return t;
            })
            .toList();

        dto.setTasks(taskDtos);

        return dto;
    }
    
    public List<TaskDto> getTasksByIdProgetto(Integer id) {
        Progetto progetto = progettoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Progetto non trovato"));

        List<Task> tasks = taskRepo.findByProgettoIdProgetto(id);

        List<TaskDto> taskDtos = tasks.stream()
            .map(task -> {
                TaskDto t = new TaskDto();
                t.setIdTask(task.getIdTask());
                t.setCodTask(task.getCodTask());
                t.setDescrTask(task.getDescrTask());
                t.setImportoTask(task.getImportoTask());
                t.setDtInizio(progetto.getDtInizio());
                t.setDtFine(progetto.getDtFine());
                t.setIdProgetto(task.getProgetto().getIdProgetto());
                
                BigDecimal sommaConsuntivatoPerTask = consuntivoService.getSommaPerTask(task.getIdTask());
                t.setSommaTaskConsuntivi(sommaConsuntivatoPerTask);
                return t;
            })
            .toList();

        return taskDtos;
    }
    
    public List<ProgettoDto> findAllWithTasks(){
    	List<Progetto> progetti = progettoRepo.findAll();

        return progetti.stream().map(progetto -> {
            ProgettoDto dto = new ProgettoDto();
            dto.setIdProgetto(progetto.getIdProgetto());
            dto.setCodProgetto(progetto.getCodProgetto());
            dto.setDescrProgetto(progetto.getDescrProgetto());
            dto.setTipoProgetto(progetto.getTipoProgetto());
            dto.setDtInizio(progetto.getDtInizio());
            dto.setDtFine(progetto.getDtFine());
            
            BigDecimal importoTotaleConsuntivi = consuntivoService.getSommaPerProgetto(progetto.getIdProgetto());
            dto.setImportoConsuntivato(importoTotaleConsuntivi);
            
            List<Task> tasks = taskRepo.findByProgettoIdProgetto(progetto.getIdProgetto());
            List<TaskDto> taskDtos = tasks.stream()
                    .map(task -> {
                        TaskDto t = new TaskDto();
                        t.setIdTask(task.getIdTask());
                        t.setCodTask(task.getCodTask());
                        t.setDescrTask(task.getDescrTask());
                        t.setImportoTask(task.getImportoTask());
                        t.setDtInizio(task.getDtInizio());
                        t.setDtFine(task.getDtFine());
                        t.setIdProgetto(task.getProgetto().getIdProgetto());
                        
                        BigDecimal sommaConsuntivatoPerTask = consuntivoService.getSommaPerTask(task.getIdTask());
                        t.setSommaTaskConsuntivi(sommaConsuntivatoPerTask);
                        
                        return t;
                    })
                    .toList();

                dto.setTasks(taskDtos);
                
            BigDecimal somma = taskRepo.sommaImportiByProgetto(progetto.getIdProgetto());
            dto.setImportoProgetto(somma);

            return dto;
        }).toList();
    }
    
}

