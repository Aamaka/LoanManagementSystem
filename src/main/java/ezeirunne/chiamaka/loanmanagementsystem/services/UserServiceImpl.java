package ezeirunne.chiamaka.loanmanagementsystem.services;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Admin;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Customer;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.AdminRepository;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.CustomerRepository;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.LoginUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    @Override
    public Response login(LoginUserRequest request) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(request.getEmail());
        if(admin.isPresent() && admin.get().getPassword().equals(request.getPassword()))
            return response(admin.get());

        Optional<Customer> customer = customerRepository.findCustomerByEmail(request.getEmail());
        if(customer.isPresent() && customer.get().getPassword().equals(request.getPassword()))
            return response(customer.get());

        return Response.builder()
                .message("Invalid details")
                .build();
    }

    private Response response(User user) {
        return Response.builder()
                .message("Welcome back "+ user.getName() )
                .build();
    }

    @Override
    public User getUserByUserName(String email) {
        Optional<Admin> admin = adminRepository.findAdminByEmail(email);
        if(admin.isPresent()) return admin.get();

        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        if(customer.isPresent()) return customer.get();

        throw new UsernameNotFoundException("Username not found");
    }
}
