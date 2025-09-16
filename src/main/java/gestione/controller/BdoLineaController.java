package gestione.controller;

import gestione.dto.BdoLineaDto;
import gestione.service.BdoLineaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bdo-linee")
public class BdoLineaController {

    private final BdoLineaService service;

    public BdoLineaController(BdoLineaService service) {
        this.service = service;
    }

    @GetMapping
    public List<BdoLineaDto> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<BdoLineaDto> getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public BdoLineaDto save(@RequestBody BdoLineaDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.deleteById(id);
    }
}
