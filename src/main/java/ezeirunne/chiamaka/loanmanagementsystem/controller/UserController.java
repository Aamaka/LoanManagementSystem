package ezeirunne.chiamaka.loanmanagementsystem.controller;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Customer;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import ezeirunne.chiamaka.loanmanagementsystem.services.AdminService;
import ezeirunne.chiamaka.loanmanagementsystem.services.CustomerService;
import ezeirunne.chiamaka.loanmanagementsystem.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/loan/users/")
@Slf4j
public class UserController {



    private final CustomerService services;
    private final UserService userService;
    private final AdminService adminService;


    @PostMapping("register-a-customer/")
    public Response register(@RequestBody RegisterUserRequest request){
        try {
            return services.register(request);
        }
        catch (InvalidDetailException e){
            log.info("Invalid detail");
            return services.register(request);
        }
    }

    @PostMapping("register-an-admin/")
    public Response adminRegistration( @RequestBody AdminRegistrationRequest request){
        return adminService.adminRegistration(request);
    }

    @PostMapping("login/")
    public Response login(@RequestBody LoginUserRequest request){
        return userService.login(request);
    }

    @PostMapping("apply-for-loan/")
    public Response loan(@RequestBody UserLoanRequest request){
        log.info("GOT HERE CONTROLLER -> {}", request);
            return services.applyForLoan(request);
    }

    @GetMapping("find-customer/")
    public Customer find(@RequestBody Request request){
        return services.findUser(request);
    }

    @GetMapping("find-loan/")
    public Loan findLoan(@RequestBody Request request){
        return services.findLoan(request);
    }

    @PostMapping("make-payment/")
    public Response payment(@RequestBody PaymentRequest request){
        return services.makePayment(request);
    }

    @GetMapping("find-payment/")
    public List<Payment> findPayment(@RequestBody Request request){
        return services.findPayment(request);
    }
}
