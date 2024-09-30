package com.gbtec.av532.services;

import com.gbtec.av532.exceptions.EmailNotADraftException;
import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.model.EmailState;
import com.gbtec.av532.repositories.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Rollback after each test
class EmailServiceIntegrationTest {

    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailRepository emailRepository;

    private EmailModel email;

    @BeforeEach
    void setUp() {
        email = new EmailModel();
        email.setState(EmailState.DRAFT);
        email.setLastChangeDateTime(LocalDateTime.now());
        email.setEmailFrom("carl@gbtec.com");
        emailRepository.save(email);
    }

    @Test
    void testGetEmailById_Success() throws EmailNotFoundException {
        EmailModel foundEmail = emailService.getEmailById(email.getEmailId());
        assertEquals(email.getEmailId(), foundEmail.getEmailId());
    }

    @Test
    void testGetEmailById_NotFound() {
        assertThrows(EmailNotFoundException.class, () -> emailService.getEmailById(UUID.randomUUID()));
    }

    @Test
    void testCreateNewEmail() {
        EmailModel newEmail = new EmailModel();
        newEmail.setEmailFrom("test@gmail.com");
        
        EmailModel createdEmail = emailService.createNewEmail(newEmail);

        assertEquals(newEmail.getEmailFrom(), createdEmail.getEmailFrom());
        assertEquals(EmailState.DRAFT, createdEmail.getState());
        assertNotNull(createdEmail.getLastChangeDateTime());
    }

    @Test
    void testUpdateEmail_Success() throws EmailNotADraftException {
        email.setEmailSubject("Updated Subject");
        EmailModel updatedEmail = emailService.updateEmail(email);

        assertEquals("Updated Subject", updatedEmail.getEmailSubject());
        assertNotNull(updatedEmail.getLastChangeDateTime());
    }

    @Test
    void testUpdateEmail_NotADraft() {
        email.setState(EmailState.SENT);

        assertThrows(EmailNotADraftException.class, () -> emailService.updateEmail(email));
    }

    @Test
    void testMarkAsSpam() {
        emailService.markAsSpam("carl@gbtec.com");
        
        EmailModel markedForSpam = emailRepository.findById(email.getEmailId()).orElseThrow();
        assertEquals(EmailState.SPAM, markedForSpam.getState());
    }

    @Test
    void testDeleteEmail() {
        emailService.deleteEmail(email.getEmailId());

        EmailModel deletedEmail = emailRepository.findById(email.getEmailId()).orElseThrow();
        assertEquals(EmailState.DELETED, deletedEmail.getState());
    }

    @Test
    void testDeleteEmail_NotFound() {
        emailService.deleteEmail(UUID.randomUUID()); // No exception should be thrown
    }

    @Test
    void testSendEmail_Success() {
        emailService.sendEmail(email.getEmailId());

        EmailModel sentEmail = emailRepository.findById(email.getEmailId()).orElseThrow();
        assertEquals(EmailState.SENT, sentEmail.getState());
    }

    @Test
    void testSendEmail_NotDraft() {
        email.setState(EmailState.SENT); // already sent

        emailService.sendEmail(email.getEmailId());

        EmailModel sentEmail = emailRepository.findById(email.getEmailId()).orElseThrow();
        assertEquals(EmailState.SENT, sentEmail.getState());
    }
}
