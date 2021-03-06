package br.com.youbook.youbook.models;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@DynamicUpdate
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Users implements UserDetails{
    
    @Column(length = 45, nullable = true)
    private String perfilImage;
    
    @Id
    @NotEmpty
    private String username;
    
    @NotEmpty
    private String nomeCompleto;
    
    @NotEmpty        
    private String email;
    
    @NotEmpty
    private String password;

    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Transient
    public String getPhotosImagePath() {
        if (getPerfilImage() == null || getUsername() == null) return null;
        return "/user-photos/" + getUsername() + getPerfilImage();
    }
        
}
