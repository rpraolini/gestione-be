package gestione.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import gestione.dto.BdoDto;
import gestione.model.Bdo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BdoMapper {
    BdoDto toDto(Bdo bdo);
    Bdo toEntity(BdoDto dto);

}