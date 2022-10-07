package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Validated
public class RegisterUserRequest {
    private String name;
    private String address;
    private Gender gender;

    @Column(unique = true)
    private String nin;

    private String dob;

    @Column(unique = true)
    private String accountNumber;

    @Column(unique = true)
    private String phoneNumber;

    private String bankName;

    @Email
    @Column(unique = true)
    private String email;

    private String occupation;
    private String password;
    private String confirmPassword;
}
