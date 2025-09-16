package gestione.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import gestione.dto.BdoLineaDto;
import gestione.model.BdoLinea;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BdoLineaMapper {
	
	@Mapping(source = "bdo.idBdo", target = "idBdo")
	BdoLineaDto toDto(BdoLinea bdoLinea);
	
	@Mapping(target = "bdo", ignore = true)
	BdoLinea toEntity(BdoLineaDto bdoLineaDto);
}
