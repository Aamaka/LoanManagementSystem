package ezeirunne.chiamaka.loanmanagementsystem.data.repositories;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

}
