package gestione.controller;

import gestione.dto.AmbienteDto;
import gestione.service.AmbienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ambienti")
public class AmbienteController {

    @Autowired
    private AmbienteService ambienteService;

    @GetMapping
    public List<AmbienteDto> findAll() {
        return ambienteService.findAll();
    }

    @GetMapping("/{id}")
    public AmbienteDto findById(@PathVariable Integer id) {
        return ambienteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AmbienteDto save(@RequestBody AmbienteDto ambienteDto) {
        return ambienteService.save(ambienteDto);
    }

    @PutMapping("/{id}")
    public AmbienteDto update(@PathVariable Integer id, @RequestBody AmbienteDto ambienteDto) {
        ambienteDto.setIdAmbiente(id);
        return ambienteService.save(ambienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        ambienteService.deleteById(id);
    }
}
