package ezeirunne.chiamaka.loanmanagementsystem.controller;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Loan;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Payment;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Customer;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.*;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import ezeirunne.chiamaka.loanmanagementsystem.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/loan/user/")
@Slf4j
public class UserController {



    private final CustomerService services;


    @PostMapping("register/")
    public Response register(@RequestBody RegisterUserRequest request){
        try {
            return services.register(request);
        }
        catch (InvalidDetailException e){
            log.info("Invalid detail");
            return services.register(request);
        }
    }
//
//    @PostMapping("login/")
//    public Response login(@RequestBody LoginUserRequest request){
//        return services.login(request);
//    }

    @PostMapping("loan/")
    public Response loan(@RequestBody UserLoanRequest request){
        try {
            return services.loan(request);
        }
        catch (InvalidDetailException e){
            log.info("Invalid detail");
            return services.loan(request);
        }
    }

    @GetMapping("find/")
    public Customer find(@RequestBody Request request){
        return services.find(request);
    }

    @GetMapping("findLoan/")
    public Loan findLoan(@RequestBody Request request){
        return services.findLoan(request);
    }

    @PostMapping("payment/")
    public Response payment(@RequestBody PaymentRequest request){
        return services.payment(request);
    }

    @GetMapping("findPayment/")
    public List<Payment> findPayment(@RequestBody Request request){
        return services.findPayment(request);
    }
}
