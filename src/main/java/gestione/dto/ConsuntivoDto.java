package gestione.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ConsuntivoDto {

    private Integer idConsuntivo;
    private String dtConsuntivo;
    private Integer idLineaBdo;
    private Integer idTask;
    private BigDecimal importoConsuntivo;
    private TaskDto taskDto;
    }
