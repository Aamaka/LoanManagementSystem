package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import lombok.*;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoanRequest {
    private String email;

    private BigInteger amount;

    private String loanPurpose;

    private String loanPlan;

    private String guarantorName;
    private String guarantorPhoneNumber;


}
