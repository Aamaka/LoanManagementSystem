package ezeirunne.chiamaka.loanmanagementsystem.util;

import lombok.Getter;

import java.security.SecureRandom;

@Getter
public class LoanUtil {

    public static String generateToken(){
        SecureRandom random = new SecureRandom();
        return String.valueOf(10000+random.nextInt(99999));
    }
}
