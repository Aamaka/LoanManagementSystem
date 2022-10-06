package ezeirunne.chiamaka.loanmanagementsystem.services;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;

import java.util.List;

public interface UserService {
    Response register(RegisterUserRequest request);
    Response login(LoginUserRequest request);

    Response loan(UserLoanRequest request);
    User find (Request request);

    Loan findLoan(Request request);

    Response payment (PaymentRequest request);

    List<Payment> findPayment(Request request);
}