package campus.randolh.gestion_gimnasio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String documento;

    @Column(nullable = false)
    private boolean activo = true;

    @ManyToMany
    @JoinTable(
        name = "cliente_rutina",
        joinColumns = @JoinColumn(name = "cliente_id"),
        inverseJoinColumns = @JoinColumn(name = "rutina_id")
    )
    private Set<Rutina> rutinas = new HashSet<>();

    public Cliente() {
    }

    public Cliente(String nombre, String documento, boolean activo) {
        this.nombre = nombre;
        this.documento = documento;
        this.activo = activo;
    }

    // Metodos de conveniencia para sincronizar la relacion
    public void agregarRutina(Rutina rutina) {
        this.rutinas.add(rutina);
        rutina.getClientes().add(this);
    }

    public void eliminarRutina(Rutina rutina) {
        this.rutinas.remove(rutina);
        rutina.getClientes().remove(this);
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

    public Set<Rutina> getRutinas() {
        return rutinas;
    }

    public void setRutinas(Set<Rutina> rutinas) {
        this.rutinas = rutinas;
    }
}
