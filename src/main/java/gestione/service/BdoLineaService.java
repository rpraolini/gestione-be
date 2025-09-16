package gestione.service;

import gestione.dto.BdoLineaDto;
import gestione.mapper.BdoLineaMapper;
import gestione.model.Bdo;
import gestione.model.BdoLinea;
import gestione.repository.BdoLineaRepository;
import gestione.repository.BdoRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BdoLineaService {

    private final BdoLineaRepository repository;
    private final BdoRepository bdoRepository;
    private final BdoLineaMapper bdoLineaMapper;

    public BdoLineaService(BdoLineaRepository repository, BdoRepository bdoRepository, BdoLineaMapper bdoLineaMapper) {
        this.repository = repository;
        this.bdoRepository = bdoRepository;
        this.bdoLineaMapper = bdoLineaMapper;
    }

    public List<BdoLineaDto> findAll() {
        return repository.findAll()
            .stream()
            .map(bdoLineaMapper::toDto)
            .collect(Collectors.toList());
    }

    public Optional<BdoLineaDto> findById(Integer id) {
        return repository.findById(id)
            .map(bdoLineaMapper::toDto);
    }

    public BdoLineaDto save(BdoLineaDto dto) {
    	BdoLinea entity = bdoLineaMapper.toEntity(dto);
    	if (dto.getIdBdo() != null) {
            Bdo bdo = bdoRepository.findById(dto.getIdBdo())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "BDO non trovato con ID " + dto.getIdBdo()));
            entity.setBdo(bdo);;
        } else {
            throw new IllegalArgumentException("L'ID del bdo non può essere nullo sulla linea.");
        }
        
        return bdoLineaMapper.toDto(repository.save(entity));
    }

    public void deleteById(Integer id) {
        repository.deleteById(id);
    }
}
