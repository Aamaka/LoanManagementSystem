package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLoanRequest {
    private String email;

    private BigDecimal amount;
    private String schoolName;
    private String schoolId;
    private String department;
    private String courseOfStudy;
    private String bvn;

    private String loanPurpose;

    private String loanPlan;

    private String guardianName;
    private String guardianPhoneNumber;


}
