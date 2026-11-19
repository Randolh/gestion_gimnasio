package campus.randolh.gestion_gimnasio.config;

import campus.randolh.gestion_gimnasio.entity.Cliente;
import campus.randolh.gestion_gimnasio.entity.Rutina;
import campus.randolh.gestion_gimnasio.repository.ClienteRepository;
import campus.randolh.gestion_gimnasio.repository.RutinaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final RutinaRepository rutinaRepository;

    public DataLoader(ClienteRepository clienteRepository, RutinaRepository rutinaRepository) {
        this.clienteRepository = clienteRepository;
        this.rutinaRepository = rutinaRepository;
    }

    @Override
    public void run(String... args) {
        if (rutinaRepository.count() > 0 || clienteRepository.count() > 0) {
            return;
        }

        Rutina r1 = rutinaRepository.save(new Rutina("Pierna Hipertrofia", "INTERMEDIO"));
        Rutina r2 = rutinaRepository.save(new Rutina("Cardio Funcional", "BASICO"));
        Rutina r3 = rutinaRepository.save(new Rutina("Espalda y Biceps", "AVANZADO"));

        Cliente c1 = new Cliente("Carlos Perez", "1001", true);
        c1.agregarRutina(r1);
        c1.agregarRutina(r2);
        clienteRepository.save(c1);

        Cliente c2 = new Cliente("Maria Gomez", "1002", true);
        c2.agregarRutina(r1);
        clienteRepository.save(c2);

        Cliente c3 = new Cliente("Juan Rodriguez", "1003", false);
        clienteRepository.save(c3);
    }
}
