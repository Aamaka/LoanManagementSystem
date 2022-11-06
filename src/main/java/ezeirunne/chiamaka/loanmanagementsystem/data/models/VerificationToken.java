package ezeirunne.chiamaka.loanmanagementsystem.data.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private String userEmail;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime expiresAt = createdAt.plusMinutes(5);


}
