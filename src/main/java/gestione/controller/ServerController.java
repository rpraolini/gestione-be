package gestione.controller;

import gestione.dto.ServerDto;
import gestione.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/server")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @GetMapping
    public List<ServerDto> findAll() {
        return serverService.findAll();
    }

    @GetMapping("/{id}")
    public ServerDto findById(@PathVariable Integer id) {
        return serverService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServerDto save(@RequestBody ServerDto serverDto) {
        return serverService.save(serverDto);
    }

    @PutMapping("/{id}")
    public ServerDto update(@PathVariable Integer id, @RequestBody ServerDto serverDto) {
        serverDto.setIdServer(id);
        return serverService.save(serverDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Integer id) {
        serverService.deleteById(id);
    }
}
