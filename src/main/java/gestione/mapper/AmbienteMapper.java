package gestione.mapper;

import gestione.dto.AmbienteDto;
import gestione.model.Ambiente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AmbienteMapper {
    AmbienteDto toDto(Ambiente ambiente);
    Ambiente toEntity(AmbienteDto ambienteDto);
}
