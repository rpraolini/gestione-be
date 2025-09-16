package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "PROGETTI")
@Data
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROGETTO", columnDefinition = "INTEGER")
    private Integer idProgetto;

    @Column(name = "COD_PROGETTO", length = 20, columnDefinition = "TEXT")
    private String codProgetto;

    @Column(name = "DESCR_PROGETTO", length = 250, columnDefinition = "TEXT")
    private String descrProgetto;
    
    @Column(name = "DT_INIZIO", columnDefinition = "TEXT")
    private String dtInizio;
    
    @Column(name = "DT_FINE", columnDefinition = "TEXT")
    private String dtFine;
    
    @Column(name = "STATO", columnDefinition = "TEXT")
    private String stato;
    
    @Column(name = "TIPO_PROGETTO", columnDefinition = "TEXT")
    private String tipoProgetto;
    
    	
}
