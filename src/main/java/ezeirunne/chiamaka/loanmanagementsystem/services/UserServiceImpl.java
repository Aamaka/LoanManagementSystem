package ezeirunne.chiamaka.loanmanagementsystem.services;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.LoanRepository;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.PaymentRepository;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.UserRepository;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserServices {

    private final UserRepository userRepository;

    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Response register(RegisterUserRequest request) {
        System.out.println("=====>Calling this service");
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        User user = User.builder()
                .name(request.getName())
                .address(request.getAddress())
                .gender(request.getGender())
                .nin(request.getNin())
                .dob(LocalDate.parse(request.getDob(), dateTimeFormat))
                .accountNumber(request.getAccountNumber())
                .phoneNumber(request.getPhoneNumber())
                .bankName(request.getBankName())
                .email(request.getEmail())
                .occupation(request.getOccupation())
                .password(request.getPassword())
                .confirmPassword(request.getConfirmPassword())
                .build();
        if (request.getConfirmPassword().equals(request.getPassword())) {
            User saveUser = userRepository.save(user);
            Response response = new Response();
            response.setMessage("Your registration was successful, Welcome " + saveUser.getName());
            return response;
        }
        throw new InvalidDetailException("Invalid details");
    }

    @Override
    public Response login(LoginUserRequest request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if (user.isPresent()) {
            if (request.getPassword().equals(user.get().getPassword())) {
                Response response = new Response();
                response.setMessage("Welcome back " + user.get().getName());
                return response;
            }
            throw new InvalidDetailException("Invalid details");
        }
        throw new InvalidDetailException("User does not exist");
    }

    @Override
    public Response loan(UserLoanRequest request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());

        if (user.isPresent()) {
            Optional<Loan> loan = loanRepository.findByUserId(user.get().getId());
            if (loan.isPresent()) {
                if (loan.get().getBalance().intValue() > 0) {
                    throw new InvalidDetailException("Pay up your loan of " + loan.get().getBalance());               }
                return getALoan(request, user);

            }

            return getALoan(request, user);

        }
        throw new InvalidDetailException("User does not exist");
    }

    private Response getALoan(UserLoanRequest request, Optional<User> user) {
        Response response = new Response();
        if(request.getAmount().intValue() > 0){
            Loan loan = Loan.builder()
                    .amount(request.getAmount())
                    .loanPurpose(request.getLoanPurpose())
                    .loanPlan(request.getLoanPlan())
                    .guarantorName(request.getGuarantorName())
                    .guarantorPhoneNumber(request.getGuarantorPhoneNumber())
                    .balance(request.getAmount())
                    .user(user.get())
                    .build();
            Loan saved = loanRepository.save(loan);

            response.setMessage(saved.getUser().getName() + " Your request has been received we will get back to you");
        }else {
            response.setMessage("amount must be greater than zero(0)");
        }
        return response;

    }

    @Override
    public User find(Request request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if (user.isPresent()) {
            return user.get();
        }
        throw new InvalidDetailException("User does not exist");
    }

    @Override
    public Loan findLoan(Request request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        System.out.println("User");
        if (user.isPresent()) {
            Optional<Loan> loan = loanRepository.findByUserId(user.get().getId());
            if(loan.isPresent()){
                if(loan.get().getBalance().intValue() > 0){
                    return loan.get();
                }
                throw new InvalidDetailException("You debt has been cleared");
            }
            throw new InvalidDetailException("User does not have a loan");
        }
        throw new InvalidDetailException("User does not exist");
    }

    @Override
    public Response payment(PaymentRequest request) {
        Optional<User> user = userRepository.findUserByEmail(request.getEmail());
        if(user.isPresent()){
            Response response = new Response();
            if(user.get().getPassword().equals(request.getPassword())){

                Optional<Loan> loan = loanRepository.findByUserId(user.get().getId());
                if(loan.isPresent()){
                    if(loan.get().getBalance().intValue() > 0){
                        if(request.getAmount().intValue() > 0){
                            if(request.getAmount().intValue() > loan.get().getBalance().intValue()){
                                response.setMessage("Amount is greater than balance");
                            }else {
                                Payment payment = Payment.builder()
                                        .paymentType(request.getPaymentType())
                                        .amount(request.getAmount())
                                        .loan(loan.get())
                                        .build();
                                loan.get().setBalance(loan.get().getBalance().subtract(request.getAmount()));
                                Loan saved = loanRepository.save(loan.get());
                                Payment savePayment = paymentRepository.save(payment);
                                response.setMessage("Your new balance is "+saved.getBalance());
                            }

                        }else {
                            response.setMessage("amount must be greater than zero ");
                        }
                    }else {
                        response.setMessage("Your debt has been cleared");
                    }
                    return response;
                }
                throw new InvalidDetailException("User does not have a loan");
            }
            throw new InvalidDetailException("Invalid detail");
        }
        throw new InvalidDetailException("User does not exist");
    }

    @Override
    public List<Payment> findPayment(Request request) {
        Loan loan = findLoan(request);
        List<Payment> payment = paymentRepository.findPaymentByLoan_Id(loan.getId());
        if(!payment.isEmpty()){
            return payment;
        }
        throw new InvalidDetailException("User as not made a payment");
    }


}