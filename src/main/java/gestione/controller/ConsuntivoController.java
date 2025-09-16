package gestione.controller;

import gestione.dto.ConsuntivoDto;
import gestione.service.ConsuntivoService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consuntivi")
public class ConsuntivoController {

    private final ConsuntivoService service;

    public ConsuntivoController(ConsuntivoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ConsuntivoDto> getAll() {
        return service.findAll();
    }
    
    @GetMapping(params = {"idLineaBdo"}) // Questo mapping risponde a /api/consuntivi?idLineaBdo=X
    public List<ConsuntivoDto> getConsuntiviByLineaBdoId(@RequestParam(required = true) Integer idLineaBdo) {
        // Chiama il servizio per recuperare i consuntivi filtrati
        return service.findByLineaBdoId(idLineaBdo);
    }

    
    @GetMapping(params = {"idTask"}) // Questo mapping risponde a /api/consuntivi?idTask=X
    public List<ConsuntivoDto> getConsuntiviByTaskId(@RequestParam(required = true) Integer idTask) {
        return service.findByTaskId(idTask);
    }

    
    @GetMapping("/{id}")
    public Optional<ConsuntivoDto> getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public ConsuntivoDto create(@RequestBody ConsuntivoDto dto) {
        return service.save(dto);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
    
    @GetMapping("/somma/task/{idTask}")
    public BigDecimal getSommaPerTask(@PathVariable Integer idTask) {
        return service.getSommaPerTask(idTask);
    }

    @GetMapping("/somma/linea/{idLineaBdo}")
    public BigDecimal getSommaPerLinea(@PathVariable Integer idLineaBdo) {
        return service.getSommaPerLinea(idLineaBdo);
    }
    
    @GetMapping("/somma/bdo/{idBdo}")
    public BigDecimal getSommaPerBdo(@PathVariable Integer idBdo) {
        return service.getSommaPerBdo(idBdo);
    }
    
    @GetMapping("/somma/progetto/{idProgetto}")
    public BigDecimal getSommaPerProgetto(@PathVariable Integer idProgetto) {
        return service.getSommaPerProgetto(idProgetto);
    }
    
 // ConsuntivoController.java
    @GetMapping("/byLinea/{idLineaBdo}")
    public List<ConsuntivoDto> getConsuntiviByLineaBdo(@PathVariable Integer idLineaBdo) {
        // Dovrai aggiungere un metodo nel ConsuntivoRepository per trovare per idLineaBdo
        // Esempio: List<Consuntivo> findByLineaBdo_IdLineaBdo(Integer idLineaBdo);
        return service.findByLineaBdoId(idLineaBdo); // implementa questo nel service
    }
}
