package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import ezeirunne.chiamaka.loanmanagementsystem.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String email;
    private String password;
    private PaymentType paymentType;
    private BigInteger amount;
}
