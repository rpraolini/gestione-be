package gestione.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import gestione.dto.TaskDto;
import gestione.mapper.ConsuntivoMapper;
import gestione.mapper.TaskMapper;
import gestione.model.Consuntivo;
import gestione.model.Progetto;
import gestione.model.Task;
import gestione.repository.ConsuntivoRepository;
import gestione.repository.ProgettoRepository;
import gestione.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository repository;
    private final ProgettoRepository progettoRepository;
    private final ConsuntivoRepository consuntivoRepository;
    private final TaskMapper mapper;
    private final ConsuntivoMapper consuntivoMapper;

    public TaskService(TaskRepository repository, ConsuntivoService consuntivoService, TaskMapper taskMapper, ProgettoRepository progettoRepository,
    		ConsuntivoRepository consuntivoRepository, ConsuntivoMapper consuntivoMapper) {
        this.repository = repository;
        this.mapper = taskMapper;
        this.progettoRepository = progettoRepository;
        this.consuntivoRepository = consuntivoRepository;
        this.consuntivoMapper =  consuntivoMapper;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> findAll() {
    	 List<Task> tasks = repository.findAll();
         return tasks.stream()
             .map(this::mapTaskToDtoWithDerivedFields) // Utilizza il metodo helper
             .collect(Collectors.toList());
    }
    
    private TaskDto mapTaskToDtoWithDerivedFields(Task task) {
        TaskDto taskDto = mapper.toDto(task); // Mappa i campi base da Task a TaskDto

        // --- Calcola sommaTaskConsuntivi e importoTaskResiduo ---
        // Recupera i consuntivi per questo task.
        // Usiamo findByTask_IdTask dal ConsuntivoRepository per avere gli oggetti Consuntivo completi
        List<Consuntivo> consuntiviTaskEntities = consuntivoRepository.findByTask_IdTask(task.getIdTask());

        // Calcola la somma dei consuntivi per questo task
        BigDecimal sommaTaskConsuntivi = consuntiviTaskEntities.stream()
            .map(Consuntivo::getImportoConsuntivo)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        taskDto.setSommaTaskConsuntivi(sommaTaskConsuntivi);

        // Calcola importoTaskResiduo
        BigDecimal importoTaskVal = (task.getImportoTask() != null ? task.getImportoTask() : BigDecimal.ZERO);
        taskDto.setImportoTaskResiduo(importoTaskVal.subtract(sommaTaskConsuntivi));
        
        taskDto.setConsuntivi(consuntivoRepository.findByTask_IdTask(task.getIdTask())
                .stream()
                .map(consuntivoMapper::toDto)
                .collect(Collectors.toList())
                );
        
        return taskDto;
    }

    @Transactional 
    public TaskDto save(TaskDto dto) {
    	Task entity = mapper.toEntity(dto);
    	if (dto.getIdProgetto() != null) {
            Progetto progetto = progettoRepository.findById(dto.getIdProgetto())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Progetto con ID " + dto.getIdProgetto() + " non trovato."));
            entity.setProgetto(progetto);
        } else {
            throw new IllegalArgumentException("L'ID del progetto non può essere nullo sul task.");
        }
        Task saved = repository.save(entity);
        return mapper.toDto(saved);
    }
    
    public Optional<TaskDto> findById(Integer id) {
        return repository.findById(id)
            .map(mapper::toDto);
    }
    
    public Optional<TaskDto> findByCodice(String codice) {
        return repository.findByCodTask(codice)
            .map(mapper::toDto);
    }
      
    @Transactional 
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
    
    public List<Task> findByProgettoId(Integer id) {
        return repository.findByProgettoIdProgetto(id);
    }
}
