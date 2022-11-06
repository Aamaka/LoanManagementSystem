package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import ezeirunne.chiamaka.loanmanagementsystem.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PaymentRequest {
    private String email;
    private String password;
    private PaymentType paymentType;
    private BigDecimal amount;
}
