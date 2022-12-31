package ezeirunne.chiamaka.loanmanagementsystem.services.Notification;

import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.EmailNotificationRequest;

public interface EmailNotificationService {

    String sendHtmlMail(EmailNotificationRequest emailNotificationRequest);
}
