package com.aluracursos.foro_hub.api.domain.usuario;

import com.aluracursos.foro_hub.api.domain.topico.Respuesta;
import com.aluracursos.foro_hub.api.domain.topico.Topico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;


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

    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    private Boolean activo;

    @OneToMany(mappedBy = "autorTopico", cascade = CascadeType.ALL)
    private List<Topico> topicos;

    @OneToMany(mappedBy = "autorRespuesta", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Usuario(DatosRegistroUsuario datos) {
        this.nombre = datos.nombre();
        this.correoElectronico = datos.correoElectronico();
        this.contraseña = datos.contraseña();
        this.perfil = datos.perfil();
        this.activo = true;
       }

    public void actualizarInformacion(DatosActualizaUsuario datos) {
        if (datos.nombre() != null)
            this.nombre = datos.nombre();
        if (datos.perfil() != null)
            this.perfil = datos.perfil();
    }

    public void actualizarContraseña(DatosCambiaContraseñaUsuario datos) {

        if (datos.nuevaContraseña() != null)
            this.contraseña = datos.nuevaContraseña();

    }
    public void inactivarUsuario(){
        this.activo = false;
    }
}
