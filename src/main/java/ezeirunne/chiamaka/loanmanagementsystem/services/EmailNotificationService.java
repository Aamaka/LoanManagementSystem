package ezeirunne.chiamaka.loanmanagementsystem.services;

import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.EmailNotificationRequest;

public interface EmailNotificationService {

    String sendHtmlMail(EmailNotificationRequest emailNotification);

}
