package campus.randolh.gestion_gimnasio.controller;

import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaRequestDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.service.RutinaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {

    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @PostMapping
    public ResponseEntity<RutinaResponseDTO> crear(@Valid @RequestBody RutinaRequestDTO dto) {
        RutinaResponseDTO creada = rutinaService.crear(dto);
        if (creada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<RutinaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(rutinaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutinaResponseDTO> obtenerPorId(@PathVariable Long id) {
        RutinaResponseDTO r = rutinaService.obtenerPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RutinaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody RutinaRequestDTO dto) {
        RutinaResponseDTO r = rutinaService.actualizar(id, dto);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminada = rutinaService.eliminar(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{rutinaId}/clientes")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesDeRutina(@PathVariable Long rutinaId) {
        List<ClienteResponseDTO> clientes = rutinaService.listarClientesDeRutina(rutinaId);
        if (clientes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }
}
