package gestione.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import gestione.dto.ProgettoDto;
import gestione.model.Progetto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgettoMapper {

	ProgettoDto toDto(Progetto progetto);

	
	Progetto toEntity(ProgettoDto progettoDto);

}
