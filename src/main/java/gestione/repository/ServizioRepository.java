package gestione.repository;

import gestione.model.Servizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServizioRepository extends JpaRepository<Servizio, Integer> {
}
