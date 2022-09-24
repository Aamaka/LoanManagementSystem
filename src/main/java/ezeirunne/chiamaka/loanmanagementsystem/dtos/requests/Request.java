package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FindUserRequest {
    private String  email;
}
