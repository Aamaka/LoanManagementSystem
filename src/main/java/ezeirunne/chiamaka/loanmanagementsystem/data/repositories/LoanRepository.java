package ezeirunne.chiamaka.loanmanagementsystem.data.repositories;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {



    Optional<Loan> findByUserId(long id);

}
