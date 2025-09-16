package gestione.repository;

import gestione.model.TipoServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoServerRepository extends JpaRepository<TipoServer, Integer> {
}
