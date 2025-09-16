package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Z_WS")
@Data
public class Ws {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_WS")
    private Integer idWs;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "URL")
    private String url;

    @Column(name = "PORTA")
    private Integer porta;

    @ManyToOne
    @JoinColumn(name = "ID_AMBIENTE")
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "ID_SERVIZIO")
    private Servizio servizio;

    @Column(name = "NOTE")
    private String note;
}
