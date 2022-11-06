package ezeirunne.chiamaka.loanmanagementsystem.services;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Customer;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;

import java.util.List;

public interface CustomerService {
    Response register(RegisterUserRequest request);

    Response loan(UserLoanRequest request);
    Customer find (Request request);

    Loan findLoan(Request request);

    Response payment (PaymentRequest request);

    List<Payment> findPayment(Request request);
}
