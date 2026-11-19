package campus.randolh.gestion_gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos para registrar o actualizar un cliente")
public class ClienteRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Schema(description = "Nombre completo del cliente", example = "Carlos Perez")
    private String nombre;

    @NotBlank(message = "El documento es obligatorio")
    @Schema(description = "Documento de identidad unico", example = "1098765432")
    private String documento;

    @Schema(description = "Estado activo del cliente en el gimnasio", example = "true", defaultValue = "true")
    private boolean activo = true;

    public ClienteRequestDTO() {
    }

    public ClienteRequestDTO(String nombre, String documento, boolean activo) {
        this.nombre = nombre;
        this.documento = documento;
        this.activo = activo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
