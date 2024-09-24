package com.gbtec.av532.services;

import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.repositories.EmailRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmailService {

    private final EmailRepository emailRepository;

    public EmailService(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public EmailModel getEmailById(UUID id) throws EmailNotFoundException {
        return emailRepository.findById(id).orElseThrow(() -> new EmailNotFoundException(String.format("Email with id = %s not found", id)));
    }

    public List<EmailModel> getAllEmails() {
        return emailRepository.findAll();
    }

    public EmailModel createNewEmail(EmailModel email) {
        return emailRepository.save(email);
    }

    public EmailModel updateEmail(EmailModel email) {
        return emailRepository.save(email);
    }

    public void deleteEmail(UUID id) {
        emailRepository.deleteById(id);
    }

    public void deleteAllEmails() {
        emailRepository.deleteAll();
    }
}
