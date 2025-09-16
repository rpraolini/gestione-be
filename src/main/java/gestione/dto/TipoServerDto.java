package gestione.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class TipoServerDto implements Serializable {
    private Integer idTipo;
    private String tipo;
}
