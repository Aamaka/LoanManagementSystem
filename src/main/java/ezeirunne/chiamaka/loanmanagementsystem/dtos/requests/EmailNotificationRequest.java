package ezeirunne.chiamaka.loanmanagementsystem.dtos.requests;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmailNotificationRequest {
    private String userEmail;
    private String mailContent;

}
