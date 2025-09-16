package gestione.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gestione.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

	
	Optional<Task> findByCodTask(String codTask);
	List<Task> findByProgettoIdProgetto(Integer idProgetto);
	
	@Query("SELECT COALESCE(SUM(t.importoTask), 0) FROM Task t WHERE t.progetto.idProgetto = :idProgetto")
    BigDecimal sommaImportiByProgetto(@Param("idProgetto") Integer idProgetto);
	
	@Query("SELECT DISTINCT t FROM Task t JOIN Consuntivo c ON t.idTask = c.task.idTask WHERE c.lineaBdo.idLineaBdo = :idLineaBdo")
    List<Task> findTasksByBdoLineaId(@Param("idLineaBdo") Integer idLineaBdo);
	
}




