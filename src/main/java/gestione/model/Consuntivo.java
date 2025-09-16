package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "CONSUNTIVI")
@Data
public class Consuntivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CONSUNTIVO")
    private Integer idConsuntivo;

    @Column(name = "DT_CONSUNTIVO")
    private String dtConsuntivo;

    @ManyToOne
    @JoinColumn(name = "ID_LINEA_BDO")
    private BdoLinea lineaBdo;

    @ManyToOne
    @JoinColumn(name = "ID_TASK")
    private Task task;

    @Column(name = "IMPORTO_CONSUNTIVO")
    private BigDecimal importoConsuntivo;
}
