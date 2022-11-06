package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminRegistrationRequest {

    private String name;
    private String address;

    private Gender gender;

    private String phoneNumber;
    private String email;

    private String password;
    private String confirmPassword;

}
