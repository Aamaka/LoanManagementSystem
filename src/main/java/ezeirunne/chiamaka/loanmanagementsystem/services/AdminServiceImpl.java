package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.Admin;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.AdminRepository;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.AdminRegistrationRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Authority;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidSyntaxException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static ezeirunne.chiamaka.loanmanagementsystem.validation.ValidateEmail.validateEmail;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;

    @Override
    public Response adminRegistration(AdminRegistrationRequest request) {
        if(adminRepository.existsByEmail(request.getEmail())) throw new InvalidDetailException("Admin already exist");
        if(validateEmail(request.getEmail())){
            if(request.getPassword().equals(request.getConfirmPassword())) {
                Admin admin = modelMapper.map(request, Admin.class);
                admin.getAuthority().add(Authority.LENDER);
                admin.setPassword(passwordEncoder.encode(request.getPassword()));

                Admin saved = adminRepository.save(admin);
                Response response = new Response();
                response.setMessage(String.format("%s, your registration was successful", saved.getName()));
                return response;
            }
            throw new BadCredentialsException("Password mismatch");
        }
        else {
            throw new InvalidSyntaxException("Email syntax is invalid");
        }

    }
}
