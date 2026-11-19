package campus.randolh.gestion_gimnasio.controller;

import campus.randolh.gestion_gimnasio.dto.ClienteRequestDTO;
import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.service.ClienteService;
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
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO creado = clienteService.crear(dto);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@PathVariable Long id) {
        ClienteResponseDTO c = clienteService.obtenerPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO c = clienteService.actualizar(id, dto);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = clienteService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/rutinas/{rutinaId}")
    public ResponseEntity<String> asignarRutina(@PathVariable Long clienteId, @PathVariable Long rutinaId) {
        boolean ok = clienteService.asignarRutina(clienteId, rutinaId);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente o Rutina no encontrados");
        }
        return ResponseEntity.ok("Rutina asignada correctamente al cliente");
    }

    @GetMapping("/{clienteId}/rutinas")
    public ResponseEntity<List<RutinaResponseDTO>> listarRutinasDeCliente(@PathVariable Long clienteId) {
        List<RutinaResponseDTO> rutinas = clienteService.listarRutinasDeCliente(clienteId);
        if (rutinas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rutinas);
    }

    @DeleteMapping("/{clienteId}/rutinas/{rutinaId}")
    public ResponseEntity<String> quitarRutina(@PathVariable Long clienteId, @PathVariable Long rutinaId) {
        boolean ok = clienteService.quitarRutina(clienteId, rutinaId);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente o Rutina no encontrados");
        }
        return ResponseEntity.ok("Rutina retirada del cliente exitosamente");
    }
}
