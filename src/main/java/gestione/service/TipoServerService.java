package gestione.service;

import gestione.dto.TipoServerDto;
import gestione.mapper.TipoServerMapper;
import gestione.model.TipoServer;
import gestione.repository.TipoServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoServerService {

    @Autowired
    private TipoServerRepository tipoServerRepository;

    @Autowired
    private TipoServerMapper tipoServerMapper;

    @Transactional(readOnly = true)
    public List<TipoServerDto> findAll() {
        return tipoServerRepository.findAll().stream()
                .map(tipoServerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TipoServerDto findById(Integer id) {
        TipoServer tipoServer = tipoServerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TipoServer non trovato con ID: " + id));
        return tipoServerMapper.toDto(tipoServer);
    }

    @Transactional
    public TipoServerDto save(TipoServerDto tipoServerDto) {
        TipoServer tipoServer = tipoServerMapper.toEntity(tipoServerDto);
        TipoServer savedTipoServer = tipoServerRepository.save(tipoServer);
        return tipoServerMapper.toDto(savedTipoServer);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!tipoServerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TipoServer non trovato con ID: " + id);
        }
        tipoServerRepository.deleteById(id);
    }
}
