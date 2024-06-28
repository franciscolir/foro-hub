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

    @ManyToOne
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfiles;

    //private Boolean activo;

    @OneToMany(mappedBy = "autorTopico", cascade = CascadeType.ALL)
    private List<Topico> topicos;

    @OneToMany(mappedBy = "autorRespuesta", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Usuario(DatosRegistroUsuario datos) {
        this.nombre = datos.nombre();
        this.correoElectronico = datos.correoElectronico();
        this.contraseña = datos.contraseña();
        this.perfiles = new Perfil(datos.perfiles());
        //this.activo = true;
       }
}
