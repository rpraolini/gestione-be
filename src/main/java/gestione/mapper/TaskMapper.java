package gestione.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import gestione.dto.TaskDto;
import gestione.model.Task;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {
	@Mapping(source = "progetto.idProgetto", target = "idProgetto")
	TaskDto toDto(Task progetto);

	@Mapping(target = "progetto", ignore = true)
	Task toEntity(TaskDto progettoDto);
}