package gestione.service;

import gestione.dto.ServerDto;
import gestione.mapper.ServerMapper;
import gestione.model.Ambiente;
import gestione.model.Server;
import gestione.model.Servizio;
import gestione.model.TipoServer;
import gestione.repository.AmbienteRepository;
import gestione.repository.ServerRepository;
import gestione.repository.ServizioRepository;
import gestione.repository.TipoServerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;
    @Autowired
    private ServerMapper serverMapper;
    @Autowired
    private TipoServerRepository tipoServerRepository;
    @Autowired
    private AmbienteRepository ambienteRepository;
    @Autowired
    private ServizioRepository servizioRepository;

    @Transactional(readOnly = true)
    public List<ServerDto> findAll() {
        return serverRepository.findAll().stream()
                .map(serverMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ServerDto findById(Integer id) {
        Server server = serverRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Server non trovato con ID: " + id));
        return serverMapper.toDto(server);
    }

    @Transactional
    public ServerDto save(ServerDto serverDto) {
        Server server = serverMapper.toEntity(serverDto);
        setDependencies(server, serverDto);
        Server savedServer = serverRepository.save(server);
        return serverMapper.toDto(savedServer);
    }

    @Transactional
    public void deleteById(Integer id) {
        if (!serverRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Server non trovato con ID: " + id);
        }
        serverRepository.deleteById(id);
    }

    private void setDependencies(Server server, ServerDto serverDto) {
        if (serverDto.getIdTipo() != null) {
            TipoServer tipoServer = tipoServerRepository.findById(serverDto.getIdTipo())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TipoServer non trovato con ID: " + serverDto.getIdTipo()));
            server.setTipoServer(tipoServer);
        }
        if (serverDto.getIdAmbiente() != null) {
            Ambiente ambiente = ambienteRepository.findById(serverDto.getIdAmbiente())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ambiente non trovato con ID: " + serverDto.getIdAmbiente()));
            server.setAmbiente(ambiente);
        }
        if (serverDto.getIdServizio() != null) {
            Servizio servizio = servizioRepository.findById(serverDto.getIdServizio())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servizio non trovato con ID: " + serverDto.getIdServizio()));
            server.setServizio(servizio);
        }
    }
}
