package gestione.service;

import gestione.dto.ServizioDto;
import gestione.mapper.ServizioMapper;
import gestione.model.Servizio;
import gestione.repository.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServizioService {

    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private ServizioMapper servizioMapper;

    @Transactional(readOnly = true)
    public List<ServizioDto> findAll() {
        return servizioRepository.findAll().stream()
                .map(servizioMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServizioDto findById(Integer id) {
        Servizio servizio = servizioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servizio non trovato con ID: " + id));
        return servizioMapper.toDto(servizio);
    }

    @Transactional
    public ServizioDto save(ServizioDto servizioDto) {
        Servizio servizio = servizioMapper.toEntity(servizioDto);
        Servizio savedServizio = servizioRepository.save(servizio);
        return servizioMapper.toDto(savedServizio);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!servizioRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Servizio non trovato con ID: " + id);
        }
        servizioRepository.deleteById(id);
    }
}
