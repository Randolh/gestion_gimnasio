package campus.randolh.gestion_gimnasio.service;

import campus.randolh.gestion_gimnasio.dto.ClienteRequestDTO;
import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.entity.Cliente;
import campus.randolh.gestion_gimnasio.entity.Rutina;
import campus.randolh.gestion_gimnasio.repository.ClienteRepository;
import campus.randolh.gestion_gimnasio.repository.RutinaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final RutinaRepository rutinaRepository;

    public ClienteService(ClienteRepository clienteRepository, RutinaRepository rutinaRepository) {
        this.clienteRepository = clienteRepository;
        this.rutinaRepository = rutinaRepository;
    }

    public ClienteResponseDTO toDTO(Cliente c) {
        return new ClienteResponseDTO(c.getId(), c.getNombre(), c.getDocumento(), c.isActivo());
    }

    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO obtenerPorId(Long id) {
        return clienteRepository.findById(id).map(this::toDTO).orElse(null);
    }

    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        if (clienteRepository.existsByDocumento(dto.getDocumento())) {
            return null;
        }
        Cliente cliente = new Cliente(dto.getNombre(), dto.getDocumento(), dto.isActivo());
        return toDTO(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        Optional<Cliente> op = clienteRepository.findById(id);
        if (op.isEmpty()) {
            return null;
        }

        Cliente cliente = op.get();
        cliente.setNombre(dto.getNombre());
        cliente.setDocumento(dto.getDocumento());
        cliente.setActivo(dto.isActivo());
        return toDTO(clienteRepository.save(cliente));
    }

    public boolean eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            return false;
        }
        clienteRepository.deleteById(id);
        return true;
    }

    public boolean asignarRutina(Long clienteId, Long rutinaId) {
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);
        Optional<Rutina> rutinaOp = rutinaRepository.findById(rutinaId);

        if (clienteOp.isEmpty() || rutinaOp.isEmpty()) {
            return false;
        }

        Cliente cliente = clienteOp.get();
        Rutina rutina = rutinaOp.get();
        cliente.agregarRutina(rutina);
        clienteRepository.save(cliente);
        return true;
    }

    public List<RutinaResponseDTO> listarRutinasDeCliente(Long clienteId) {
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);
        if (clienteOp.isEmpty()) {
            return null;
        }

        return clienteOp.get().getRutinas().stream()
                .map(r -> new RutinaResponseDTO(r.getId(), r.getNombre(), r.getNivel()))
                .collect(Collectors.toList());
    }

    public boolean quitarRutina(Long clienteId, Long rutinaId) {
        Optional<Cliente> clienteOp = clienteRepository.findById(clienteId);
        Optional<Rutina> rutinaOp = rutinaRepository.findById(rutinaId);

        if (clienteOp.isEmpty() || rutinaOp.isEmpty()) {
            return false;
        }

        Cliente cliente = clienteOp.get();
        Rutina rutina = rutinaOp.get();
        cliente.eliminarRutina(rutina);
        clienteRepository.save(cliente);
        return true;
    }
}
