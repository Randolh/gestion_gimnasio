package campus.randolh.gestion_gimnasio.repository;

import campus.randolh.gestion_gimnasio.entity.Rutina;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    boolean existsByNombre(String nombre);
    Optional<Rutina> findByNombre(String nombre);
}
