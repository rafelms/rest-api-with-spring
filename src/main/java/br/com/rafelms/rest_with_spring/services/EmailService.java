package br.com.rafelms.rest_with_spring.services;

import br.com.rafelms.rest_with_spring.config.EmailConfig;
import br.com.rafelms.rest_with_spring.data.dto.request.EmailRequestDTO;
import br.com.rafelms.rest_with_spring.mail.EmailSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class EmailService {

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private EmailConfig emailConfigs;

    public void sendSimpleEmail(EmailRequestDTO emailRequest){
        emailSender.
                to(emailRequest.getTo())
                .withSubject(emailRequest.getSubject())
                .withMessage(emailRequest.getBody())
                .send(emailConfigs);
    }

    public void sendEmailWithAttachment(String emailRequestJson, MultipartFile attachment){
        File tempFile = null;
        try {
            EmailRequestDTO emailRequest = new ObjectMapper().readValue(emailRequestJson, EmailRequestDTO.class);
            tempFile = File.createTempFile("attachment", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);

            emailSender
                    .to(emailRequest.getTo())
                    .withSubject(emailRequest.getSubject())
                    .withMessage(emailRequest.getSubject())
                    .attach(tempFile.getAbsolutePath())
                            .send(emailConfigs);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing email request JSON" ,e);
        } catch (IOException e) {
            throw new RuntimeException("Error processing the attachment file",e);
        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }
}
