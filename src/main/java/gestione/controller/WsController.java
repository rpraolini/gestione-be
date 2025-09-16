package gestione.controller;

import gestione.dto.WsDto;
import gestione.service.WsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ws")
@CrossOrigin(origins = "http://localhost:4200")
public class WsController {

    @Autowired
    private WsService wsService;

    @GetMapping
    public List<WsDto> findAll() {
        return wsService.findAll();
    }

    @GetMapping("/{id}")
    public WsDto findById(@PathVariable Integer id) {
        return wsService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WsDto save(@RequestBody WsDto wsDto) {
        return wsService.save(wsDto);
    }

    @PutMapping("/{id}")
    public WsDto update(@PathVariable Integer id, @RequestBody WsDto wsDto) {
        wsDto.setIdWs(id);
        return wsService.save(wsDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        wsService.deleteById(id);
    }
}
