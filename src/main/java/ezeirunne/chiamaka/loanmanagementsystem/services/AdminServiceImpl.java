package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.Admin;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.AdminRepository;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.AdminRegistrationRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;
import ezeirunne.chiamaka.loanmanagementsystem.enums.Authority;
import ezeirunne.chiamaka.loanmanagementsystem.exceptions.InvalidDetailException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;

    @Override
    public Response adminRegistration(AdminRegistrationRequest request) {
        if(adminRepository.existsByEmail(request.getEmail())) throw new InvalidDetailException("User already exist");
        Admin admin = modelMapper.map(request, Admin.class);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.getAuthority().add(Authority.LENDER);
        if(request.getPassword().equals(request.getConfirmPassword())) {
            Admin saved = adminRepository.save(admin);
            Response response = new Response();
            response.setMessage(String.format("%s, your registration was successful", saved.getName()));
        }
        throw new BadCredentialsException("Password mismatch");
    }
}
