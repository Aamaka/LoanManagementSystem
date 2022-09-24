package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoanRequest {
    private String email;

    private BigInteger amount;

    private String loanPurpose;

    private String loanPlan;

    private String guarantorName;
    private String guarantorPhoneNumber;


}
