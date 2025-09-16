package gestione.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

@Data
@JsonPropertyOrder({ "idTask", "codTask", "descrTask", "importoTask","sommaTaskConsuntivi", "importoTaskResiduo", "dtInizio", "dtFine", "idProgetto", "consuntivi" })
public class TaskDto {

	    private Integer idTask;
	    private String codTask;
	    private String descrTask;
	   
	    private Integer idProgetto;
	    private String dtInizio;
	    private String dtFine;
	    
	    private BigDecimal importoTask;
	    private BigDecimal sommaTaskConsuntivi ;
	    private BigDecimal importoTaskResiduo ;
	    
	    private List<ConsuntivoDto> consuntivi;
	    
	    public TaskDto() {};
	    
		public TaskDto(Integer idTask, String codTask, String descrTask, BigDecimal importoTask,
				String dtInizio, String dtFine, Integer idProgetto, List<ConsuntivoDto> consuntivi, 
				BigDecimal importoResiduoTask) {
			super();
			this.idTask = idTask;
			this.codTask = codTask;
			this.descrTask = descrTask;
			this.importoTask = importoTask;
			this.dtInizio = dtInizio;
			this.dtFine = dtFine;
			this.idProgetto = idProgetto;
			this.consuntivi = consuntivi;
			this.importoTaskResiduo = importoResiduoTask;
		}
		
}
