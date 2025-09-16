package gestione.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TASKS")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TASK", columnDefinition = "INTEGER")
    private Integer idTask;

    @Column(name = "COD_TASK", length = 30, columnDefinition = "TEXT")
    private String codTask;

    @Column(name = "DESCR_TASK", length = 250, columnDefinition = "TEXT")
    private String descrTask;
    
    @Column(name = "IMPORTO_TASK", columnDefinition = "NUMERIC(10,2)")
    private BigDecimal importoTask;
    
    @Column(name = "DT_INIZIO", columnDefinition = "TEXT")
    private String dtInizio;
    
    @Column(name = "DT_FINE", columnDefinition = "TEXT")
    private String dtFine;
      
    @ManyToOne
    @JoinColumn(name = "ID_PROGETTO")
    private Progetto progetto;
    
    
}
