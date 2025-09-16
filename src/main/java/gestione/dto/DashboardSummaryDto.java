package gestione.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDto {
	private Double budgetAnnuoTotale; // Wrapper
    private Double consuntivatoTotaleAnnuo; // Wrapper
    private Double percentualeConsuntivatoSulBudget; // Wrapper
    private Double budgetResiduoAnnuo; // Wrapper
    private Long numeroProgettiAttivi; // Wrapper
    private Long numeroBdoInGestione; // Wrapper
    private Double importoMedioConsuntivo; // Wrapper
    private Long totaleConsuntiviRegistrati; // Wrapper
    private Double totaleImpegnatoBdo; // Wrapper
    private Double residuoDaImpegnare; // Wrapper
}
