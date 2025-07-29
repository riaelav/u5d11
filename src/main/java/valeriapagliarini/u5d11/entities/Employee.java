package valeriapagliarini.u5d11.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import valeriapagliarini.u5d11.enums.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties({"password", "authorities", "enabled", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
public class Employee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;


    private String password;

    @Column(name = "profile_img")
    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Employee(String username, String firstName, String lastName, String email, String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;


    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


    //metodo dovuto all'implementazione di UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((this.role.name())));
    }
}