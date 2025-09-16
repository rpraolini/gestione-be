package gestione.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class BdoLineaDto {

    private Integer idLineaBdo;
    private Integer idBdo;
    private String descrLinea;
    private String dtConsegna;
    private String azienda;
    private String tipoFatturazione;
    private BigDecimal numCanoni;
    private BigDecimal importoCanoni;
    
    private BigDecimal importoLinea;
    private BigDecimal importoLineaResiduo;
    private BigDecimal sommaLineaConsuntivi ;
    
    private List<TaskDto> tasks;
    private List<ConsuntivoDto> consuntivi;
    
    
}
