package gestione.service;

import gestione.dto.DashboardSummaryDto;
import gestione.model.Bdo;
import gestione.model.BdoLinea;
import gestione.model.Consuntivo;
import gestione.model.Progetto;
import gestione.model.Task;
import gestione.repository.BdoLineaRepository;
import gestione.repository.BdoRepository;
import gestione.repository.ConsuntivoRepository;
import gestione.repository.ProgettoRepository;
import gestione.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
public class DashboardService {

    private final TaskRepository taskRepository;
    private final ConsuntivoRepository consuntivoRepository;
    private final ProgettoRepository progettoRepository;
    private final BdoRepository bdoRepository;
    private final BdoLineaRepository bdoLineaRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public DashboardService(TaskRepository taskRepository,
                            ConsuntivoRepository consuntivoRepository,
                            ProgettoRepository progettoRepository,
                            BdoRepository bdoRepository,
                            BdoLineaRepository bdoLineaRepository) {
        this.taskRepository = taskRepository;
        this.consuntivoRepository = consuntivoRepository;
        this.progettoRepository = progettoRepository;
        this.bdoRepository = bdoRepository;
        this.bdoLineaRepository = bdoLineaRepository;
    }

    private LocalDate parseDateString(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return null;
        }
        try {
            String datePart = dateString.split(" ")[0];
            return LocalDate.parse(datePart, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Errore nel parsing della data '" + dateString + "': " + e.getMessage());
            return null;
        }
    }

    public DashboardSummaryDto getDashboardSummary() {
        int currentYear = LocalDate.now().getYear();

        BigDecimal budgetAnnuoTotale = BigDecimal.ZERO;
        BigDecimal consuntivatoTotaleAnnuo = BigDecimal.ZERO;
        Long totaleConsuntiviRegistrati = 0L;
        BigDecimal totaleImpegnatoBdo = BigDecimal.ZERO;

        List<Task> allTasks = taskRepository.findAll();
        for (Task task : allTasks) {
            if (task.getImportoTask() != null) {
                budgetAnnuoTotale = budgetAnnuoTotale.add(task.getImportoTask());
            }
        }

        List<Consuntivo> allConsuntivi = consuntivoRepository.findAll();
        for (Consuntivo cons : allConsuntivi) {
            LocalDate consDate = parseDateString(cons.getDtConsuntivo());
            if (consDate != null && consDate.getYear() == currentYear) {
                if (cons.getImportoConsuntivo() != null) {
                    consuntivatoTotaleAnnuo = consuntivatoTotaleAnnuo.add(cons.getImportoConsuntivo());
                }
                totaleConsuntiviRegistrati++;
            }
        }

        List<Bdo> allBdos = bdoRepository.findAll();
        for (Bdo bdo : allBdos) {
        	List<BdoLinea> bdoLinee = bdoLineaRepository.findByBdo_IdBdo(bdo.getIdBdo());
        	BigDecimal importoTotaleLinee = bdoLinee.stream().map(BdoLinea::getImportoLinea).filter(Objects::nonNull)
					.reduce(BigDecimal.ZERO, BigDecimal::add);
            /*if (bdo.getImportoBdo() != null) {
                totaleImpegnatoBdo = totaleImpegnatoBdo.add(bdo.getImportoBdo());
            }*/
        	totaleImpegnatoBdo = totaleImpegnatoBdo.add(importoTotaleLinee);
        }

        BigDecimal residuoDaImpegnare = budgetAnnuoTotale.subtract(totaleImpegnatoBdo);

        Double percentualeConsuntivatoSulBudget = 0.0;
        if (budgetAnnuoTotale.compareTo(BigDecimal.ZERO) > 0) {
            percentualeConsuntivatoSulBudget = consuntivatoTotaleAnnuo
                    .divide(budgetAnnuoTotale, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .doubleValue();
        }

        BigDecimal budgetResiduoAnnuo = budgetAnnuoTotale.subtract(consuntivatoTotaleAnnuo);

        List<Progetto> allProgetti = progettoRepository.findAll();
        Long numeroProgettiAttivi = allProgetti.stream()
                .filter(progetto -> "ATTIVO".equalsIgnoreCase(progetto.getStato()))
                .count();

        List<Bdo> allBdosInGestione = bdoRepository.findAll();
        Long numeroBdoInGestione = allBdosInGestione.stream()
                .filter(bdo -> {
                    LocalDate dtFineBdo = parseDateString(bdo.getDtFine());
                    return dtFineBdo == null || dtFineBdo.isAfter(LocalDate.now());
                })
                .count();

        BigDecimal importoMedioConsuntivo = BigDecimal.ZERO;
        if (totaleConsuntiviRegistrati > 0) {
            importoMedioConsuntivo = consuntivatoTotaleAnnuo
                    .divide(new BigDecimal(totaleConsuntiviRegistrati), 2, RoundingMode.HALF_UP);
        }


        return new DashboardSummaryDto(
                budgetAnnuoTotale.doubleValue(),         
                consuntivatoTotaleAnnuo.doubleValue(),   
                percentualeConsuntivatoSulBudget,       
                budgetResiduoAnnuo.doubleValue(),       
                numeroProgettiAttivi,                   
                numeroBdoInGestione,                    
                importoMedioConsuntivo.doubleValue(),   
                totaleConsuntiviRegistrati,             
                totaleImpegnatoBdo.doubleValue(),       
                residuoDaImpegnare.doubleValue()        
        );

    }
}