package gestione.controller;

import gestione.dto.BdoDto;
import gestione.service.BdoService;
import jakarta.persistence.EntityNotFoundException; // Assicurati di importare questa eccezione
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bdo")
@CrossOrigin(origins = "http://localhost:4200")
public class BdoController {

    private final BdoService bdoService;

    public BdoController(BdoService bdoService) {
        this.bdoService = bdoService;
    }

    @GetMapping
    public ResponseEntity<List<BdoDto>> getAllBdos() {
        List<BdoDto> bdos = bdoService.getAllBdosWithLineeAndTotals();
        return ResponseEntity.ok(bdos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BdoDto> getBdoById(@PathVariable Integer id) {
        try {
            // Chiama il servizio che restituisce direttamente BdoDto o lancia EntityNotFoundException
            BdoDto bdo = bdoService.getBdoWithLineeAndTotals(id);
            return ResponseEntity.ok(bdo);
        } catch (EntityNotFoundException e) {
            // Se il servizio non trova il BDO, restituisce 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<BdoDto> createBdo(@RequestBody BdoDto bdoDto) {
        BdoDto createdBdo = bdoService.save(bdoDto);
        return new ResponseEntity<>(createdBdo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BdoDto> updateBdo(@PathVariable Integer id, @RequestBody BdoDto bdoDto) {
        try {
            BdoDto updatedBdo = bdoService.update(id, bdoDto);
            return ResponseEntity.ok(updatedBdo);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBdo(@PathVariable Integer id) {
        try {
            bdoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}