package campus.randolh.gestion_gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de una rutina")
public class RutinaResponseDTO {

    @Schema(description = "ID autoincremental de la rutina", example = "1")
    private Long id;

    @Schema(description = "Nombre de la rutina", example = "Pierna Hipertrofia")
    private String nombre;

    @Schema(description = "Nivel de dificultad", example = "INTERMEDIO")
    private String nivel;

    public RutinaResponseDTO() {
    }

    public RutinaResponseDTO(Long id, String nombre, String nivel) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
