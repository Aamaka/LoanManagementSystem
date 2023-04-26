package ezeirunne.chiamaka.loanmanagementsystem.data.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal amount;

    private LocalDate dueDate;
    private String duration;
    private String bvn;

    private String schoolName;
    private String schoolId;
    private String department;
    private String courseOfStudy;

    private BigDecimal balance ;

    @DateTimeFormat
    private LocalDate loanDate = LocalDate.now();

    private String loanPurpose;

    private String loanPlan;

    private String guardianName;
    private String guardianPhoneNumber;

    @JoinColumn
    @OneToOne
    private Customer customer;

}
