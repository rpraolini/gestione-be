package gestione.repository;

import gestione.model.Ws;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WsRepository extends JpaRepository<Ws, Integer> {
}
