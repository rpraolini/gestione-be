package gestione.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class ServizioDto implements Serializable {
    private Integer idServizio;
    private String servizio;
}
