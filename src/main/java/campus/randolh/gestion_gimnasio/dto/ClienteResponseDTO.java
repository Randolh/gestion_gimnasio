package campus.randolh.gestion_gimnasio.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Respuesta con los datos de un cliente")
public class ClienteResponseDTO {

    @Schema(description = "ID del cliente", example = "1")
    private Long id;

    @Schema(description = "Nombre completo del cliente", example = "Carlos Perez")
    private String nombre;

    @Schema(description = "Documento unico", example = "1098765432")
    private String documento;

    @Schema(description = "Indica si el cliente esta activo", example = "true")
    private boolean activo;

    public ClienteResponseDTO() {
    }

    public ClienteResponseDTO(Long id, String nombre, String documento, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.documento = documento;
        this.activo = activo;
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
