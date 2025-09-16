package gestione.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "idProgetto", "codProgetto", "descrProgetto", "tipo_progetto", "importoProgetto", "importoConsuntivato", "dtInizio", "dtFine", "stato", "tasks" })
public class ProgettoDto {

    private Integer idProgetto;
    private String codProgetto;
    private String descrProgetto;
    private String tipoProgetto;
    
    private String dtInizio;
    private String dtFine;
    private String stato;
    
    private BigDecimal importoProgetto;
    private BigDecimal importoConsuntivato;
    
    private List<TaskDto> tasks;

    public ProgettoDto(Integer idProgetto, String codProgetto, String descrProgetto) {
        this.idProgetto = idProgetto;
        this.codProgetto = codProgetto;
        this.descrProgetto = descrProgetto;
    }
    
    public ProgettoDto(Integer idProgetto, String codProgetto, String descrProgetto, BigDecimal importoTotaleTasks, String dtInizio, String dtFine, String stato) {
        this.idProgetto = idProgetto;
        this.codProgetto = codProgetto;
        this.descrProgetto = descrProgetto;
        this.importoProgetto = importoTotaleTasks;
        this.dtInizio = dtInizio;
        this.dtFine = dtFine;
        this.stato = stato;
    }

    // Getter e Setter


    public ProgettoDto() {
	}

}

