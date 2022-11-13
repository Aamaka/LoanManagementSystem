package ezeirunne.chiamaka.loanmanagementsystem.data.repositories;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findPaymentByLoan_Id(long id);
}
