package gestione.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestione.model.Bdo;

@Repository
public interface BdoRepository extends JpaRepository<Bdo, Integer> {
    // Puoi aggiungere metodi custom qui
}
