package ezeirunne.chiamaka.loanmanagementsystem.data.models;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String address;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String nin;

    private LocalDate dob;

    @Column(unique = true)
    private String accountNumber;

    @Column(unique = true)
    private String phoneNumber;
    private String bankName;

    @Email
    @Column(unique = true)
    private String email;

    private String password;

    private String occupation;
}
