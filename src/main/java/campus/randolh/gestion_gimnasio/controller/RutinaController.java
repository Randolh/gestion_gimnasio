package campus.randolh.gestion_gimnasio.controller;

import campus.randolh.gestion_gimnasio.dto.ClienteResponseDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaRequestDTO;
import campus.randolh.gestion_gimnasio.dto.RutinaResponseDTO;
import campus.randolh.gestion_gimnasio.service.RutinaService;
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
@RequestMapping("/api/rutinas")
@Tag(name = "Rutinas", description = "Operaciones CRUD y consultas relacionadas a rutinas")
public class RutinaController {

    private final RutinaService rutinaService;

    public RutinaController(RutinaService rutinaService) {
        this.rutinaService = rutinaService;
    }

    @PostMapping
    @Operation(summary = "Crear una nueva rutina", description = "Registra una rutina con nombre unico y nivel (BASICO, INTERMEDIO, AVANZADO)")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Rutina creada con exito"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos o nombre ya existente")
    })
    public ResponseEntity<RutinaResponseDTO> crear(@Valid @RequestBody RutinaRequestDTO dto) {
        RutinaResponseDTO creada = rutinaService.crear(dto);
        if (creada == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    @Operation(summary = "Listar todas las rutinas")
    @ApiResponse(responseCode = "200", description = "Lista de rutinas obtenida")
    public ResponseEntity<List<RutinaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(rutinaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una rutina por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rutina encontrada"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    public ResponseEntity<RutinaResponseDTO> obtenerPorId(@Parameter(description = "ID de la rutina", example = "1") @PathVariable Long id) {
        RutinaResponseDTO r = rutinaService.obtenerPorId(id);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una rutina existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Rutina actualizada"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    public ResponseEntity<RutinaResponseDTO> actualizar(
            @Parameter(description = "ID de la rutina", example = "1") @PathVariable Long id,
            @Valid @RequestBody RutinaRequestDTO dto) {
        RutinaResponseDTO r = rutinaService.actualizar(id, dto);
        if (r == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(r);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una rutina")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Rutina eliminada"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    public ResponseEntity<Void> eliminar(@Parameter(description = "ID de la rutina", example = "1") @PathVariable Long id) {
        boolean eliminada = rutinaService.eliminar(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{rutinaId}/clientes")
    @Operation(summary = "Listar clientes que tienen asignada esta rutina")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes de la rutina"),
        @ApiResponse(responseCode = "404", description = "Rutina no encontrada")
    })
    public ResponseEntity<List<ClienteResponseDTO>> listarClientesDeRutina(
            @Parameter(description = "ID de la rutina", example = "1") @PathVariable Long rutinaId) {
        List<ClienteResponseDTO> clientes = rutinaService.listarClientesDeRutina(rutinaId);
        if (clientes == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clientes);
    }
}
