package br.com.rafelms.rest_with_spring.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @Column
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER) // Quando carrega o User, ele traz as permissões dele
    @JoinTable(name = "user_permission", // cria a tabela associativa
            joinColumns = {@JoinColumn(name = "id_user")}, //puxa o fk da tabela user
            inverseJoinColumns = {@JoinColumn(name = "id_permission")} // puxa o fk da tabela permission
    )
    private List<Permission> permissions;

    public List<String> getRoles(){
        List<String> roles = new ArrayList<>();
        for (Permission permission : permissions){
            roles.add(permission.getDescription()); // cria um array contando as permissoes
        }                                           // que o user tem
        return roles;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
