package gestione.mapper;

import gestione.dto.ServerDto;
import gestione.model.Server;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServerMapper {
    @Mapping(source = "tipoServer.idTipo", target = "idTipo")
    @Mapping(source = "ambiente.idAmbiente", target = "idAmbiente")
    @Mapping(source = "servizio.idServizio", target = "idServizio")
    ServerDto toDto(Server server);

    @Mapping(target = "tipoServer", ignore = true)
    @Mapping(target = "ambiente", ignore = true)
    @Mapping(target = "servizio", ignore = true)
    Server toEntity(ServerDto serverDto);
}
