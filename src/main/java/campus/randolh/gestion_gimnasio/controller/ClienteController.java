package campus.randolh.gestion_gimnasio.controller;

import campus.randolh.gestion_gimnasio.dto.ClienteRequestDTO;
import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Clientes", description = "Operaciones CRUD y gestion de rutinas de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cliente", description = "Registra un cliente con documento unico")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o documento duplicado")
    })
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO creado = clienteService.crear(dto);
        if (creado == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes")
    public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(@Parameter(description = "ID del cliente", example = "1") @PathVariable Long id) {
        ClienteResponseDTO c = clienteService.obtenerPorId(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        ClienteResponseDTO c = clienteService.actualizar(id, dto);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID del cliente", example = "1") @PathVariable Long id) {
        boolean eliminado = clienteService.eliminar(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{clienteId}/rutinas/{rutinaId}")
    @Operation(summary = "Asignar una rutina a un cliente", description = "Vincula la rutina indicada con el cliente en la tabla intermedia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rutina asignada correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente o Rutina no encontrados")
    })
    public ResponseEntity<String> asignarRutina(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long clienteId,
            @Parameter(description = "ID de la rutina", example = "1") @PathVariable Long rutinaId) {
        boolean ok = clienteService.asignarRutina(clienteId, rutinaId);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente o Rutina no encontrados");
        }
        return ResponseEntity.ok("Rutina asignada correctamente al cliente");
    }

    @GetMapping("/{clienteId}/rutinas")
    @Operation(summary = "Listar todas las rutinas asignadas a un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de rutinas"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<List<RutinaResponseDTO>> listarRutinasDeCliente(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long clienteId) {
        List<RutinaResponseDTO> rutinas = clienteService.listarRutinasDeCliente(clienteId);
        if (rutinas == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rutinas);
    }

    @DeleteMapping("/{clienteId}/rutinas/{rutinaId}")
    @Operation(summary = "Quitar una rutina a un cliente", description = "Desvincula la rutina del cliente eliminando el registro de la tabla intermedia")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rutina desvinculada correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente o Rutina no encontrados")
    })
    public ResponseEntity<String> quitarRutina(
            @Parameter(description = "ID del cliente", example = "1") @PathVariable Long clienteId,
            @Parameter(description = "ID de la rutina", example = "1") @PathVariable Long rutinaId) {
        boolean ok = clienteService.quitarRutina(clienteId, rutinaId);
        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente o Rutina no encontrados");
        }
        return ResponseEntity.ok("Rutina retirada del cliente exitosamente");
    }
}
