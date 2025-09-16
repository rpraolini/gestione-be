package gestione.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class ServerDto implements Serializable {
    private Integer idServer;
    private String ip;
    private String nome;
    private Integer porta;
    private String istanza;
    private Integer idTipo; // ID della relazione
    private Integer idAmbiente; // ID della relazione
    private Integer idServizio; // ID della relazione
    private String note;
}
