package gestione.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "idBdo", "codBdo", "codIf", "descrBdo", "dtInizio", "dtFine","stato", "tipoBdo", "cup", 
					 "importoTotaleLinee", "importoBdoResiduo", "sommaBdoConsuntivi",  
					"linee" })
@Data
public class BdoDto {

    private Integer idBdo;
    private Integer codBdo;
    private Integer codIf;
    private String descrBdo;
    private String dtInizio;
    private String dtFine;
    private String tipoBdo;
    private String cup;
    private String stato;
    
    private BigDecimal importoTotaleLinee;
    private BigDecimal importoBdoResiduo;
    private BigDecimal sommaBdoConsuntivi;

    private List<BdoLineaDto> linee;             
          
    
    
	

    
}
