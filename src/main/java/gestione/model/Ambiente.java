package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Z_AMBIENTI")
@Data
public class Ambiente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AMBIENTE")
    private Integer idAmbiente;

    @Column(name = "AMBIENTE")
    private String ambiente;
}
