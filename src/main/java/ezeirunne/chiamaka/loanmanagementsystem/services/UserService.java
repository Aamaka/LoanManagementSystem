package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.LoginUserRequest;
import ezeirunne.chiamaka.loanmanagementsystem.dtos.responses.Response;

public interface UserService {

    Response login(LoginUserRequest request);
    User getUserByUserName(String email);
}
