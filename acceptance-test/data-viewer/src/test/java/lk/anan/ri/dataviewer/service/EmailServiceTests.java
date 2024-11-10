package lk.anan.ri.dataviewer.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
@Disabled
@SpringBootTest
class EmailServiceTests {

    @MockBean
    private JavaMailSender emailSender;

    @Autowired
    private EmailService emailService;

    @Test
    void testSendSimpleMessage() {
        // Given
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        // When
        emailService.sendSimpleMessage(to, subject, text);

        // Then
        verify(emailSender).send(any(SimpleMailMessage.class));
    }
}