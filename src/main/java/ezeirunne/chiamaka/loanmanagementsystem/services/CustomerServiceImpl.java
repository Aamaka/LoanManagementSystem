package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.*;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.LoanRepository;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.PaymentRepository;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.CustomerRepository;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Authority;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidSyntaxException;
import ezeirunne.chiamaka.loanmanagementsystem.services.Notification.EmailNotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ezeirunne.chiamaka.loanmanagementsystem.validation.ValidateEmail.validateEmail;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository userRepository;
    private final LoanRepository loanRepository;
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenService verificationTokenService;

    private final EmailNotificationService emailNotificationService;
    @Override
    public Response register(RegisterUserRequest request) {
        if(userRepository.existsByEmail(request.getEmail())) throw new InvalidDetailException("User already exist");
        if(validateEmail(request.getEmail())){
            if (request.getConfirmPassword().equals(request.getPassword())) {
                Customer customer = onBoardCustomer(request);
                return response(request, customer);
            }
            throw new InvalidDetailException("Invalid details");
        }
       throw new InvalidSyntaxException("Email syntax is invalid");
    }

    private Response response(RegisterUserRequest request, Customer customer) {
        User saveUser = userRepository.save(customer);
        VerificationToken token = verificationTokenService.createToken(request.getEmail());
        emailNotificationService.sendHtmlMail(buildEmailNotificationRequest(token, saveUser.getName()));
        Response response = new Response();
        response.setMessage("Your registration was successful, Welcome " + saveUser.getName());
        return response;
    }

    private EmailNotificationRequest buildEmailNotificationRequest(VerificationToken token, String name) {
        String message = getMessageTemplate();
        String mail = null;
        if(message != null){
            var verificationUrl="http://localhost:8080/api/loan/users/verify/" + token.getToken();
            mail = String.format(message, name, verificationUrl);
        }

        return EmailNotificationRequest.builder()
                .userEmail(token.getUserEmail())
                .mailContent(mail)
                .build();

    }

    private String getMessageTemplate() {
        try(BufferedReader bufferedReader =
                new BufferedReader(new FileReader("C:\\Users\\ADMIN\\Desktop\\LoanManagementSystem\\src\\main\\resources\\welcome.txt"))){
            return  bufferedReader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return null;
    }

    private Customer onBoardCustomer(RegisterUserRequest request) {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Customer customer = modelMapper.map(request, Customer.class);
        customer.setDob(LocalDate.parse(request.getDob(), dateTimeFormat));
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.getAuthority().add(Authority.BORROWER);
        return customer;
    }

    @Override
    public Response applyForLoan(UserLoanRequest request) {
        Optional<Customer> user = userRepository.findCustomerByEmail(request.getEmail());

        if (user.isPresent()) {
            Optional<Loan> loan = loanRepository.findByUserId(user.get().getId());
            if (loan.isPresent()) {
                if (loan.get().getBalance().intValue() > 0) {
                    throw new InvalidDetailException("Pay up your loan of " + loan.get().getBalance());
                }
                return getALoan(request, user);

            }
            return getALoan(request, user);

        }
        throw new InvalidDetailException("User does not exist");
    }

    private Response getALoan(UserLoanRequest request, Optional<Customer> user) {
        Response response = new Response();
        if(request.getAmount().intValue() > 0){
            Loan loan = Loan.builder()
                    .amount(request.getAmount())
                    .loanPurpose(request.getLoanPurpose())
                    .loanPlan(request.getLoanPlan())
                    .guarantorName(request.getGuarantorName())
                    .loanDate(LocalDate.now())
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
    public Customer findUser(Request request) {
        Optional<Customer> user = userRepository.findCustomerByEmail(request.getEmail());
        if (user.isPresent()) {
            return user.get();
        }
        throw new InvalidDetailException("User does not exist");
    }

    @Override
    public Loan findLoan(Request request) {
        Optional<Customer> user = userRepository.findCustomerByEmail(request.getEmail());
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
    public Response makePayment(PaymentRequest request) {
        Optional<Customer> user = userRepository.findCustomerByEmail(request.getEmail());
        if(user.isPresent()){
            Response response = new Response();
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
                                    .date(LocalDate.now())
                                    .build();
                            loan.get().setBalance(loan.get().getBalance().subtract(request.getAmount()));
                            Loan saved = loanRepository.save(loan.get());
                            paymentRepository.save(payment);
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
            throw new InvalidDetailException("User does not exist");
    }

    @Override
    public List<Payment> findPayment(Request request) {
        Loan loan = findLoan(request);
        List<Payment> payment = paymentRepository.findPaymentByLoan_Id(loan.getId());
        if(!payment.isEmpty()){
            return payment;
        }
        throw new InvalidDetailException("User has not made any payment");
    }


}