package gestione.controller;

import gestione.dto.ServizioDto;
import gestione.service.ServizioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servizi")
public class ServizioController {

    @Autowired
    private ServizioService servizioService;

    @GetMapping
    public List<ServizioDto> findAll() {
        return servizioService.findAll();
    }

    @GetMapping("/{id}")
    public ServizioDto findById(@PathVariable Integer id) {
        return servizioService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServizioDto save(@RequestBody ServizioDto servizioDto) {
        return servizioService.save(servizioDto);
    }

    @PutMapping("/{id}")
    public ServizioDto update(@PathVariable Integer id, @RequestBody ServizioDto servizioDto) {
        servizioDto.setIdServizio(id);
        return servizioService.save(servizioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        servizioService.deleteById(id);
    }
}
