package gestione.service;

import gestione.dto.AmbienteDto;
import gestione.mapper.AmbienteMapper;
import gestione.model.Ambiente;
import gestione.repository.AmbienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmbienteService {

    @Autowired
    private AmbienteRepository ambienteRepository;

    @Autowired
    private AmbienteMapper ambienteMapper;

    @Transactional(readOnly = true)
    public List<AmbienteDto> findAll() {
        return ambienteRepository.findAll().stream()
                .map(ambienteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AmbienteDto findById(Integer id) {
        Ambiente ambiente = ambienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambiente non trovato con ID: " + id));
        return ambienteMapper.toDto(ambiente);
    }

    @Transactional
    public AmbienteDto save(AmbienteDto ambienteDto) {
        Ambiente ambiente = ambienteMapper.toEntity(ambienteDto);
        Ambiente savedAmbiente = ambienteRepository.save(ambiente);
        return ambienteMapper.toDto(savedAmbiente);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!ambienteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambiente non trovato con ID: " + id);
        }
        ambienteRepository.deleteById(id);
    }
}
