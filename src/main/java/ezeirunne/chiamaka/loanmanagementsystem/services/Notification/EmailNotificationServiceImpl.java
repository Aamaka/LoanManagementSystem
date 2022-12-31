package ezeirunne.chiamaka.loanmanagementsystem.services.Notification;

import ezeirunne.chiamaka.loanmanagementsystem.dtos.requests.EmailNotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
@AllArgsConstructor
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public String sendHtmlMail(EmailNotificationRequest emailNotificationRequest) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("no-reply@email.loanManagement.com.ng.ltd");
            helper.setTo(emailNotificationRequest.getUserEmail());
            helper.setText(emailNotificationRequest.getMailContent(), true);
            javaMailSender.send(message);
            return String.format("Email was sent successfully to %s", emailNotificationRequest.getUserEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return String.format("email not sent to %s", emailNotificationRequest.getUserEmail());
    }
}
