package gestione.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Z_TIPI_SERVER")
@Data
public class TipoServer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TIPO")
    private Integer idTipo;

    @Column(name = "TIPO")
    private String tipo;
}
