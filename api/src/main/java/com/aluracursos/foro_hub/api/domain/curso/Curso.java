package com.aluracursos.foro_hub.api.domain.curso;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "cursos")
@Entity(name = "Curso")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;

    private Boolean activo;

    //@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    //private List<Topico> topicos;

    public Curso(DatosRegistroCurso curso){
        //this.cursoId = curso.id();
        this.nombre = curso.nombre();
        this.categoria = curso.categoria();
        this.activo = true;
    }

    public void actualizarInformacion(DatosActualizaCurso datos) {
        if (datos.nombre() != null)
            this.nombre = datos.nombre();
        if (datos.categoria() != null)
            this.categoria = datos.categoria();
    }

    public void inactivarCurso(){
        this.activo = false;
    }
    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
