package campus.randolh.gestion_gimnasio.dto;

import jakarta.validation.constraints.NotBlank;

public class RutinaRequestDTO {

    @NotBlank(message = "El nombre de la rutina es obligatorio")
    private String nombre;

    @NotBlank(message = "El nivel es obligatorio")
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
