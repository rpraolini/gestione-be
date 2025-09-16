package gestione.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gestione.dto.ProgettoDto;
import gestione.dto.TaskDto;
import gestione.model.Progetto;
import gestione.service.ProgettoService;

import java.util.List;

@RestController
@RequestMapping("/api/progetti")
public class ProgettoController {

	private static final Logger log = LoggerFactory.getLogger(ProgettoController.class);
    private final ProgettoService progettoService;

    @Autowired
    public ProgettoController(ProgettoService progettoService) {
        this.progettoService = progettoService;
    }

    @GetMapping
    public List<ProgettoDto> getAll() {
        return progettoService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<ProgettoDto> getById(@PathVariable Integer id) {
        return progettoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/codice/{codice}")
    public ResponseEntity<ProgettoDto> getByCodice(@PathVariable String codice) {
        return progettoService.findByCodice(codice)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ProgettoDto create(@RequestBody ProgettoDto progetto) {
        return progettoService.save(progetto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgettoDto> update(@PathVariable Integer id, @RequestBody Progetto updated) {
    	log.info("Aggiorno o inserisco il progetto con id: {}", id);
        return progettoService.findById(id)
                .map(existing -> {
                    existing.setCodProgetto(updated.getCodProgetto());
                    existing.setDescrProgetto(updated.getDescrProgetto());
                    existing.setDtInizio(updated.getDtInizio()) ;
                    existing.setDtFine(updated.getDtFine());
                    existing.setTipoProgetto(updated.getTipoProgetto());
                    return ResponseEntity.ok(progettoService.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (progettoService.findById(id).isPresent()) {
            progettoService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/withtasks/id/{id}")
    public ResponseEntity<ProgettoDto> getProgettoConTasks(@PathVariable Integer id) {
    	ProgettoDto dto = progettoService.getProgettoConTasks(id);
        return ResponseEntity.ok(dto);
    }
    
    @GetMapping("/{id}/tasks")
    public List<TaskDto> getProgettoConTask(@PathVariable Integer id) {
    	return progettoService.getTasksByIdProgetto(id);
    }
    
    @GetMapping("/withtask")
    public List<ProgettoDto> getProgettiConTasks() {
    	return progettoService.findAllWithTasks();
    }
}

