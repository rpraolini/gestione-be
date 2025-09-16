package gestione.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gestione.dto.TaskDto;
import gestione.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	private final TaskService taskservice;

    @Autowired
    public TaskController(TaskService TaskController) {
        this.taskservice = TaskController;
    }

    @GetMapping
    public List<TaskDto> getAll() {
        return taskservice.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Integer id) {
        return taskservice.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codice/{codice}")
    public ResponseEntity<TaskDto> getByCodice(@PathVariable String codice) {
        return taskservice.findByCodice(codice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TaskDto create(@RequestBody TaskDto task) {
        return taskservice.save(task);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (taskservice.findById(id).isPresent()) {
            taskservice.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
