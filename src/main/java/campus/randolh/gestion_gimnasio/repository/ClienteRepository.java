package campus.randolh.gestion_gimnasio.repository;

import campus.randolh.gestion_gimnasio.entity.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByDocumento(String documento);
    Optional<Cliente> findByDocumento(String documento);
}
