package gestione.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import gestione.dto.ConsuntivoDto;
import gestione.model.Consuntivo;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ConsuntivoMapper {

	@Mapping(source = "lineaBdo.idLineaBdo", target = "idLineaBdo")
    @Mapping(source = "task.idTask", target = "idTask")
    ConsuntivoDto toDto(Consuntivo c);

	@Mapping(target = "lineaBdo", ignore = true)
    @Mapping(target = "task", ignore = true)
    Consuntivo toEntity(ConsuntivoDto dto);
}