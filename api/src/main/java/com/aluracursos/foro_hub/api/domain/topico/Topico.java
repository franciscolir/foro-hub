package com.aluracursos.foro_hub.api.domain.topico;

import com.aluracursos.foro_hub.api.domain.curso.Curso;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosActualizaTopico;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosRegistraTopico;
import com.aluracursos.foro_hub.api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Estado status;

    //se debe accesae a perfil
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private Boolean activo;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL)
    private List<Respuesta> respuestas;

    public Topico(DatosRegistraTopico datos) {
        this.id = getId();
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.status = Estado.CREADO;
        this.usuario = getUsuario();
        this.curso = getCurso();
    }
    //MEtodo para inactivar Topico
    public void inactivarUsuario(){
        this.activo = false;
    }
    // Método para obtener el número total de respuestas
    public int getTotalRespuestas() {
        return respuestas != null ? respuestas.size() : 0;
    }
    public void actualizarInformacion(DatosActualizaTopico datos) {
        if (datos.id() != null)
            this.id = datos.id();
        if (datos.titulo() != null)
            this.titulo = datos.titulo();
        if (datos.mensaje() != null)
            this.mensaje = datos.mensaje();
        this.status = Estado.ACTUALIZADO;

    }



    @Override
    public String toString() {
        return "Topico{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", status=" + status +
                ", usuario=" + usuario +
                ", curso=" + curso +
                ", respuestas=" + respuestas +
                '}';
    }
}
