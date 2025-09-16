package gestione.repository;

import gestione.model.Consuntivo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsuntivoRepository extends JpaRepository<Consuntivo, Integer> {

    @Query("SELECT SUM(c.importoConsuntivo) FROM Consuntivo c WHERE c.task.idTask = :idTask")
    BigDecimal sommaPerTask(@Param("idTask") Integer idTask);

    @Query("SELECT SUM(c.importoConsuntivo) FROM Consuntivo c WHERE c.lineaBdo.idLineaBdo = :idLineaBdo")
    BigDecimal sommaPerLinea(@Param("idLineaBdo") Integer idLineaBdo);

    @Query("""
        SELECT COALESCE(SUM(c.importoConsuntivo), 0)
        FROM Consuntivo c
        WHERE c.lineaBdo.bdo.idBdo = :idBdo
    """)
    BigDecimal getSommaConsuntiviPerBdo(@Param("idBdo") Integer idBdo);

    @Query("""
        SELECT COALESCE(SUM(c.importoConsuntivo), 0)
        FROM Consuntivo c
        WHERE c.task.progetto.idProgetto = :idProgetto
    """)
    BigDecimal getSommaConsuntiviPerProgetto(@Param("idProgetto") Integer idProgetto);

    
    @Query("SELECT c FROM Consuntivo c WHERE c.lineaBdo.idLineaBdo = :idLineaBdo ORDER BY SUBSTR(c.dtConsuntivo, 7, 4) || '-' || SUBSTR(c.dtConsuntivo, 4, 2) || '-' || SUBSTR(c.dtConsuntivo, 1, 2) ASC, c.importoConsuntivo ASC")
     List<Consuntivo> findByLineaBdo_IdLineaBdoOrderByDtConsuntivoChronologicallyAsc(@Param("idLineaBdo") Integer idLineaBdo) ;


    // Questo è CORRETTO: Cerca i Consuntivo per task.idTask
    List<Consuntivo> findByTask_IdTask(Integer idTask);


    // Questo è CORRETTO: Cerca i Consuntivo per lineaBdo.idLineaBdo e dove task è null
    List<Consuntivo> findByLineaBdo_IdLineaBdoAndTaskIsNull(Integer idLineaBdo); // Consuntivi diretti della linea
    
    
    @Query("SELECT SUM(c.importoConsuntivo) FROM Consuntivo c WHERE c.lineaBdo.idLineaBdo = :idLineaBdo AND c.task.idTask = :idTask")
    Optional<BigDecimal> sumImportoConsuntivoByLineaBdoIdAndTaskId(
        @Param("idLineaBdo") Integer idLineaBdo,
        @Param("idTask") Integer idTask
    );
    

}
