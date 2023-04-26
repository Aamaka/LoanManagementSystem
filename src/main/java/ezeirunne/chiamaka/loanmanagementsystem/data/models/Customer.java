package ezeirunne.chiamaka.loanmanagementsystem.data.models;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
public class Customer extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String nin;

    private LocalDate dob;

    @Column(unique = true)
    private String accountNumber;

    private String bankName;

}
