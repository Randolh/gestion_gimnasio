package campus.randolh.gestion_gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para crear o actualizar una rutina")
public class RutinaRequestDTO {

    @NotBlank(message = "El nombre de la rutina es obligatorio")
    @Schema(description = "Nombre unico de la rutina", example = "Pierna Hipertrofia")
    private String nombre;

    @NotBlank(message = "El nivel es obligatorio")
    @Schema(description = "Nivel de dificultad", example = "INTERMEDIO", allowableValues = {"BASICO", "INTERMEDIO", "AVANZADO"})
    private String nivel;

    public RutinaRequestDTO() {
    }

    public RutinaRequestDTO(String nombre, String nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
