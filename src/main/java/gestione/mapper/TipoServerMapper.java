package gestione.mapper;

import gestione.dto.TipoServerDto;
import gestione.model.TipoServer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TipoServerMapper {
    TipoServerDto toDto(TipoServer tipoServer);
    TipoServer toEntity(TipoServerDto tipoServerDto);
}
