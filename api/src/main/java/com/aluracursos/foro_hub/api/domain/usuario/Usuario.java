package com.aluracursos.foro_hub.api.domain.usuario;

import com.aluracursos.foro_hub.api.domain.perfil.Perfil;

import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosActualizaUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosCambiaContraseñaUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String correoElectronico;

    private String contraseña;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    private Boolean activo;

    //@OneToMany(mappedBy = "autorRespuesta", cascade = CascadeType.ALL)
    //private List<Respuesta> respuestas;

    public Usuario(DatosRegistroUsuario datos) {
        this.id = getId();
        this.nombre = datos.nombre();
        this.correoElectronico = datos.correoElectronico();
        this.contraseña = datos.contraseña();
        this.perfil = getPerfil();
        this.activo = true;
    }



    public void actualizarInformacion(Long id,String nombre,Perfil perfil) {
        if (nombre != null)
            this.nombre = nombre;
        if (perfil != null)
            this.perfil = perfil;
    }

    public void actualizarContraseña(DatosCambiaContraseñaUsuario datos) {

        if (datos.nuevaContraseña() != null)
            this.contraseña = datos.nuevaContraseña();

    }
    public void inactivarUsuario(){
        this.activo = false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", perfil=" + perfil +
                ", activo=" + activo +
                '}';
    }
}
