package campus.randolh.gestion_gimnasio.service;

import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaRequestDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.entity.Rutina;
import campus.randolh.gestion_gimnasio.repository.RutinaRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;

    public RutinaService(RutinaRepository rutinaRepository) {
        this.rutinaRepository = rutinaRepository;
    }

    public RutinaResponseDTO toDTO(Rutina r) {
        return new RutinaResponseDTO(r.getId(), r.getNombre(), r.getNivel());
    }

    public List<RutinaResponseDTO> listarTodas() {
        return rutinaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public RutinaResponseDTO obtenerPorId(Long id) {
        Optional<Rutina> op = rutinaRepository.findById(id);
        return op.map(this::toDTO).orElse(null);
    }

    public RutinaResponseDTO crear(RutinaRequestDTO dto) {
        if (rutinaRepository.existsByNombre(dto.getNombre())) {
            return null;
        }
        Rutina rutina = new Rutina(dto.getNombre(), dto.getNivel());
        return toDTO(rutinaRepository.save(rutina));
    }

    public RutinaResponseDTO actualizar(Long id, RutinaRequestDTO dto) {
        Optional<Rutina> op = rutinaRepository.findById(id);
        if (op.isEmpty()) {
            return null;
        }

        Rutina rutina = op.get();
        rutina.setNombre(dto.getNombre());
        rutina.setNivel(dto.getNivel());
        return toDTO(rutinaRepository.save(rutina));
    }

    public boolean eliminar(Long id) {
        if (!rutinaRepository.existsById(id)) {
            return false;
        }
        rutinaRepository.deleteById(id);
        return true;
    }

    public List<ClienteResponseDTO> listarClientesDeRutina(Long rutinaId) {
        Optional<Rutina> op = rutinaRepository.findById(rutinaId);
        if (op.isEmpty()) {
            return null;
        }

        return op.get().getClientes().stream()
                .map(c -> new ClienteResponseDTO(c.getId(), c.getNombre(), c.getDocumento(), c.isActivo()))
                .collect(Collectors.toList());
    }
}
