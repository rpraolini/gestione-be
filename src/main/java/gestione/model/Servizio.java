package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Z_SERVIZI")
@Data
public class Servizio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVIZIO")
    private Integer idServizio;

    @Column(name = "SERVIZIO")
    private String servizio;
}