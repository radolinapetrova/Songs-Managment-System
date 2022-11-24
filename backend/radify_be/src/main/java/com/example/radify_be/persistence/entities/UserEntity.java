package com.example.radify_be.persistence.entities;


import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Access(AccessType.PROPERTY)
    private Integer id;

    @Column(nullable = false, name ="first_name")
    private String fName;

    @Column(nullable = false, name ="last_name")
    private String lName;

    @Column(nullable = false, unique = true, length = 20)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
    List<PlaylistEntity> playlists;

    public User convert(){
        return User.builder().id(this.id).role(this.role).fName(this.fName).lName(this.lName)
                .account(Account.builder().username(this.username).password(this.password).email(this.email).build()).build();
    }

}
