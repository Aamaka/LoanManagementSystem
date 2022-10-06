package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.LoginUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.RegisterUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.UserLoanRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userServices;

    @Test
   public void register() {
        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Alabah");
        request.setAddress("Shomolah");
        request.setGender(Gender.MALE);
        request.setNin("7689848769P1796");
        request.setAccountNumber("3856785910");
        request.setEmail("alabah@gmail.com");
        request.setBankName("union");
        request.setOccupation("Doctor");
        request.setDob("2005-06-09 00:00");
        request.setPassword("joy");
        request.setConfirmPassword("joy");
        Response response = userServices.register(request);
        assertNotNull(request);
        assertEquals("Your registration was successful, Welcome Alabah", response.getMessage());
    }

    @Test
    public void login() {
        LoginUserRequest request = new LoginUserRequest();
        request.setEmail("alabah@gmail.com");
        request.setPassword("joy");
        Response response = userServices.login(request);
        assertEquals("Welcome back Alabah",response.getMessage());
    }

    @Test
    @DisplayName("test that a user can apply for a loan")
    public void takeALoanTest(){
        UserLoanRequest request = UserLoanRequest.builder()
                .amount(BigInteger.valueOf(-9))
                .loanPlan("1 year")
                .loanPurpose("Marketing")
                .guarantorName("Ade")
                .guarantorPhoneNumber("6548979826")
                .email("alabah@gmail.com")
                .build();
//        try{
//            Response response = userServices.loan(request);
//            assertNotNull(response);
//            assertEquals("pay up your loan of ",response.getMessage());
//        }catch (InvalidDetailException ex){
//            System.out.println(ex.getMessage());
//        }
        assertThrows(InvalidDetailException.class, ()-> userServices.loan(request));

    }
}