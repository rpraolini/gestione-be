package gestione.mapper;

import gestione.dto.ServizioDto;
import gestione.model.Servizio;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServizioMapper {
    ServizioDto toDto(Servizio servizio);
    Servizio toEntity(ServizioDto servizioDto);
}
