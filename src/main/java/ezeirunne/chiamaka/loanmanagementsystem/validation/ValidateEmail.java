package ezeirunne.chiamaka.loanmanagementsystem.validation;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public class ValidateEmail {
    public static boolean validateEmail(String email){
        String regex = "[\\w-]{1,20}@\\w{2,20}\\.\\w{2,3}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }
}
