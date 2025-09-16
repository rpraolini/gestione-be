package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "BDO_LINEE")
@Data
public class BdoLinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LINEA_BDO")
    private Integer idLineaBdo;

    @ManyToOne
    @JoinColumn(name = "ID_BDO", referencedColumnName = "ID_BDO")
    private Bdo bdo;

    @Column(name = "IMPORTO_LINEA")
    private BigDecimal importoLinea;

    @Column(name = "DESCR_LINEA")
    private String descrLinea;

    @Column(name = "DT_CONSEGNA")
    private String dtConsegna;

    @Column(name = "AZIENDA")
    private String azienda;
    
    @Column(name = "TIPO_FATTURAZIONE")
    private String tipoFatturazione;
    
    @Column(name = "NUM_CANONI")
    private BigDecimal numCanoni;
    
    @Column(name = "IMPORTO_CANONE")
    private BigDecimal importoCanoni;
}
