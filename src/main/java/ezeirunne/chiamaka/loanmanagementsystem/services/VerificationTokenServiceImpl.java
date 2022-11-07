package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.VerificationToken;
import ezeirunne.chiamaka.loanmanagementsystem.data.repositories.VerificationTokenRepository;
import ezeirunne.chiamaka.loanmanagementsystem.util.LoanUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    @Override
    public VerificationToken createToken(String email) {
        VerificationToken verificationToken = VerificationToken.builder()
                .token(LoanUtil.generateToken())
                .userEmail(email)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .build();
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public boolean isValidVerificationToken(String token) {
        return false;
    }
}
