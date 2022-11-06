package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.VerificationToken;

public interface VerificationTokenService {

    VerificationToken createToken(String email);
    boolean isValidVerificationToken(String token);
}
