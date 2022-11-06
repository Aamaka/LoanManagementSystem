package ezeirunne.chiamaka.loanmanagementsystem.data.models;

import ezeirunne.chiamaka.loanmanagementsystem.enums.PaymentType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @DateTimeFormat
    private LocalDate date = LocalDate.now();
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;
    @JoinColumn
    @ManyToOne
    private Loan loan;
}
