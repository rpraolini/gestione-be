package gestione.service;

import gestione.dto.WsDto;
import gestione.mapper.WsMapper;
import gestione.model.Ambiente;
import gestione.model.Servizio;
import gestione.model.Ws;
import gestione.repository.AmbienteRepository;
import gestione.repository.ServizioRepository;
import gestione.repository.WsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WsService {

    @Autowired
    private WsRepository wsRepository;
    @Autowired
    private WsMapper wsMapper;
    @Autowired
    private AmbienteRepository ambienteRepository;
    @Autowired
    private ServizioRepository servizioRepository;

    @Transactional(readOnly = true)
    public List<WsDto> findAll() {
        return wsRepository.findAll().stream()
                .map(wsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WsDto findById(Integer id) {
        Ws ws = wsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ws non trovato con ID: " + id));
        return wsMapper.toDto(ws);
    }

    @Transactional
    public WsDto save(WsDto wsDto) {
        Ws ws = wsMapper.toEntity(wsDto);
        setDependencies(ws, wsDto);
        Ws savedWs = wsRepository.save(ws);
        return wsMapper.toDto(savedWs);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!wsRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ws non trovato con ID: " + id);
        }
        wsRepository.deleteById(id);
    }

    private void setDependencies(Ws ws, WsDto wsDto) {
        if (wsDto.getIdAmbiente() != null) {
            Ambiente ambiente = ambienteRepository.findById(wsDto.getIdAmbiente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambiente non trovato con ID: " + wsDto.getIdAmbiente()));
            ws.setAmbiente(ambiente);
        }
        if (wsDto.getIdServizio() != null) {
            Servizio servizio = servizioRepository.findById(wsDto.getIdServizio())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servizio non trovato con ID: " + wsDto.getIdServizio()));
            ws.setServizio(servizio);
        }
    }
}
