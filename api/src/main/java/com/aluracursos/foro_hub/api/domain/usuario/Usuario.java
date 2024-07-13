package com.aluracursos.foro_hub.api.domain.usuario;

import com.aluracursos.foro_hub.api.domain.perfil.Perfil;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {

    @Getter
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

    public Usuario(DatosRegistroUsuario datos) {
        this.id = getId();
        this.nombre = datos.nombre();
        this.correoElectronico = datos.correoElectronico();
        this.contraseña = datos.contraseña();
        this.perfil = getPerfil();
        this.activo = true;
    }

    //Actualiza nombre usuario
    public void actualizarInformacion(Long id,String nombre) {
        if (nombre != null)
            this.nombre = nombre;
    }

    //Actualiza perfil usuario
    public void actualizarPerfil(Long id,Perfil perfil) {
        if (perfil != null)
            this.perfil = perfil;
    }

    //Actualiza contraseña usuario
    public void actualizarContraseña(String nuevaContraseña) {
        if (nuevaContraseña != null)
            this.contraseña = nuevaContraseña;
    }

    //Inactiva usuario (delete logico)
    public void inactivarUsuario(){
        this.activo = false;
    }

    //Metodos UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority("ROLE_"+getPerfil().getNombre()));
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return correoElectronico;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
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
