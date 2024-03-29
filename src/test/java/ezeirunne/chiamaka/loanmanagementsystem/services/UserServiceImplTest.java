package ezeirunne.chiamaka.loanmanagementsystem.services;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.LoginUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.PaymentRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.RegisterUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.UserLoanRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import ezeirunne.chiamaka.loanmanagementsystem.enums.PaymentType;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("test that a user can register")
   public void register() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Ada");
        request.setAddress("Shomolah");
        request.setGender(Gender.FEMALE);
        request.setNin("3454848769P1796");
        request.setPhoneNumber("095364675765");
        request.setAccountNumber("1626785910");
        request.setEmail("ada@gmail.com");
        request.setBankName("union");
        request.setDob("2005-06-09 00:00");
        request.setPassword("joy");
        request.setConfirmPassword("joy");
        Response response = customerService.register(request);
        assertNotNull(request);
        assertEquals("Your registration was successful, Welcome Ada", response.getMessage());
    }

    @Test
    @DisplayName("test that a user can log in")
    public void login() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("ada@gmail.com");
        request.setPassword("joy");
        Response response = userService.login(request);
        assertEquals("Welcome back Ada",response.getMessage());
    }

    @Test
    @DisplayName("test that a user can apply for a loan")
    public void takeALoanTest(){
        UserLoanRequest request = UserLoanRequest.builder()
                .amount(BigDecimal.valueOf(100000))
                .loanPlan("1 year")
                .loanPurpose("school fees")
                .guardianName("Ade")
                .guardianPhoneNumber("6548979826")
                .email("ada@gmail.com")
                .build();

            Response response = customerService.applyForLoan(request);
            assertNotNull(response);
            assertEquals("Ada Your request has been received we will get back to you",response.getMessage());

        assertThrows(InvalidDetailException.class, ()-> customerService.applyForLoan(request));
    }

    @Test
    @DisplayName("Test that a user can pay up their loan")
    public void paymentTest(){
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(BigDecimal.valueOf(50000))
                .email("ada@gmail.com")
                .paymentType(PaymentType.CARD)
                .build();
        Response payment = customerService.makePayment(paymentRequest);
        assertThat("Your new balance is 0.00").isEqualTo(payment.getMessage());

    }
}