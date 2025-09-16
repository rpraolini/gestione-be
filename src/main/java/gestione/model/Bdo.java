package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BDO")
@Data
public class Bdo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_BDO")
    private Integer idBdo;

    @Column(name = "COD_BDO")
    private Integer codBdo;

    @Column(name = "COD_IF")
    private Integer codIf;

    @Column(name = "DESCR_BDO", length = 200)
    private String descrBdo;

    @Column(name = "DT_INIZIO", length = 20)
    private String dtInizio;

    @Column(name = "DT_FINE", length = 20)
    private String dtFine;

    @Column(name = "TIPO_BDO", length = 20)
    private String tipoBdo;

    @Column(name = "CUP", length = 20)
    private String cup;
    
    @Column(name = "STATO", length = 1)
    private String stato;
    
    
}

