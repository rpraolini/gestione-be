package gestione.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Z_SERVER")
@Data
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVER")
    private Integer idServer;

    @Column(name = "IP", unique = true)
    private String ip;

    @Column(name = "NOME")
    private String nome;

    @Column(name = "PORTA")
    private Integer porta;

    @Column(name = "ISTANZA")
    private String istanza;

    @ManyToOne
    @JoinColumn(name = "ID_TIPO")
    private TipoServer tipoServer;

    @ManyToOne
    @JoinColumn(name = "ID_AMBIENTE")
    private Ambiente ambiente;

    @ManyToOne
    @JoinColumn(name = "ID_SERVIZIO")
    private Servizio servizio;

    @Column(name = "NOTE")
    private String note;
}
