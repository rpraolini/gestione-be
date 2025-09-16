package gestione.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gestione.model.Progetto;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {

    // Esempio di metodo per cercare un progetto per codice
	Optional<Progetto> findByCodProgetto(String codProgetto);

}
