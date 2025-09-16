package gestione.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AmbienteDto implements Serializable {
    private Integer idAmbiente;
    private String ambiente;
}
