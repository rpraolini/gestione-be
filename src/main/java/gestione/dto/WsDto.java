package gestione.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class WsDto implements Serializable {
    private Integer idWs;
    private String nome;
    private String url;
    private Integer porta;
    private Integer idAmbiente; // ID della relazione
    private Integer idServizio; // ID della relazione
    private String note;
}
