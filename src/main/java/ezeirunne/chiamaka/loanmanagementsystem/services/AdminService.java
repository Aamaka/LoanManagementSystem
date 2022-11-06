package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.AdminRegistrationRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;

public interface AdminService {
    Response adminRegistration (AdminRegistrationRequest request);

}
