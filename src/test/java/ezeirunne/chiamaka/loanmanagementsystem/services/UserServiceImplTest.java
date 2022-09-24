//package ezeirunne.chiamaka.loanmanagementsystem.services;
//
//import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.RegisterUserRequest;
//import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
//import ezeirunne.chiamaka.loanmanagementsystem.enums.Gender;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class UserServiceImplTest {
//
//    @Autowired
//    private UserServices userServices;
//
//    @Test
//   public void register() {
//        RegisterUserRequest request = new RegisterUserRequest();
//        request.setName("Alabi");
//        request.setAddress("Shomolu");
//        request.setGender(Gender.MALE);
//        request.setNin("768984875769P996");
//        request.setAccountNumber("34567859");
//        request.setEmail("alabi@gmail.com");
//        request.setBankName("union");
//        request.setOccupation("Doctor");
//        request.setDob("2005-06-09 00:00");
//        request.setGender(Gender.MALE);
//        request.setPassword("joy");
//        request.setPassword("joy");
//        Response response = userServices.register(request);
//        assertNotNull(request);
//        assertEquals("Your registration was successful, Welcome Alabi", response.getMessage());
//    }
//
//    @Test
//    void login() {
//    }
//}