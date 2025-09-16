package gestione.repository;

import gestione.model.BdoLinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BdoLineaRepository extends JpaRepository<BdoLinea, Integer> {
    List<BdoLinea> findByBdoIdBdo(Integer idBdo);
    
    List<BdoLinea> findByBdo_IdBdo(Integer idBdo); // Metodo per trovare linee per ID BDO
    void deleteByBdo_IdBdo(Integer idBdo); // Per eliminare tutte le linee di un BDO
}
