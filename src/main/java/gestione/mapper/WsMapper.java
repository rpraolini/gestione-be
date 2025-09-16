package gestione.mapper;

import gestione.dto.WsDto;
import gestione.model.Ws;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WsMapper {
    @Mapping(source = "ambiente.idAmbiente", target = "idAmbiente")
    @Mapping(source = "servizio.idServizio", target = "idServizio")
    WsDto toDto(Ws ws);

    @Mapping(target = "ambiente", ignore = true)
    @Mapping(target = "servizio", ignore = true)
    Ws toEntity(WsDto wsDto);
}
