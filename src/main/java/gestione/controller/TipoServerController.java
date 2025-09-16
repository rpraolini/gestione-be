package gestione.controller;

import gestione.dto.TipoServerDto;
import gestione.service.TipoServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipi-server")
@CrossOrigin(origins = "http://localhost:4200")
public class TipoServerController {

    @Autowired
    private TipoServerService tipoServerService;

    @GetMapping
    public List<TipoServerDto> findAll() {
        return tipoServerService.findAll();
    }

    @GetMapping("/{id}")
    public TipoServerDto findById(@PathVariable Integer id) {
        return tipoServerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TipoServerDto save(@RequestBody TipoServerDto tipoServerDto) {
        return tipoServerService.save(tipoServerDto);
    }

    @PutMapping("/{id}")
    public TipoServerDto update(@PathVariable Integer id, @RequestBody TipoServerDto tipoServerDto) {
        tipoServerDto.setIdTipo(id);
        return tipoServerService.save(tipoServerDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        tipoServerService.deleteById(id);
    }
}
