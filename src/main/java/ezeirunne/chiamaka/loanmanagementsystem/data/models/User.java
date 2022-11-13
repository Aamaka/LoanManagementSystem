package ezeirunne.chiamaka.loanmanagementsystem.data.models;

import ezeirunne.chiamaka.loanmanagementsystem.enums.Authority;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String phoneNumber;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Authority> authority = new HashSet<>();

}
