package com.gbtec.av532.controller;

import com.gbtec.av532.exceptions.EmailNotADraftException;
import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class EmailRestControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailRestController emailRestController;

    private EmailModel emailModel;

    @BeforeEach
    void setUp() {
        openMocks(this);
        emailModel = new EmailModel();
        // Initialize emailModel as needed for your tests
    }

    /**
     * Test case #1
     * @throws EmailNotFoundException when no Mail Found
     */
    @Test
    void getEmail_ShouldReturnEmail_WhenEmailExists() throws EmailNotFoundException {
        UUID emailId = UUID.randomUUID();
        when(emailService.getEmailById(emailId)).thenReturn(emailModel);

        ResponseEntity<EmailModel> response = emailRestController.getEmail(emailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailModel, response.getBody());
    }

    /**
     * Test case #2
     * @throws EmailNotFoundException when no Mail Found
     */
    @Test
    void getEmail_ShouldReturnNotFound_WhenEmailDoesNotExist() throws EmailNotFoundException {
        UUID emailId = UUID.randomUUID();
        when(emailService.getEmailById(emailId)).thenThrow(new EmailNotFoundException(""));

        ResponseEntity<EmailModel> response = emailRestController.getEmail(emailId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    /**
     * Test case #3
     */
    @Test
    void getAllEmails_ShouldReturnAllEmails() {
        List<EmailModel> emails = Collections.singletonList(emailModel);
        when(emailService.getAllEmails()).thenReturn(emails);

        ResponseEntity<List<EmailModel>> response = emailRestController.getAllEmails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emails, response.getBody());
    }

    /**
     * Test case #4
     */
    @Test
    void createEmail_ShouldReturnCreatedEmail() {
        when(emailService.createNewEmail(emailModel)).thenReturn(emailModel);

        ResponseEntity<EmailModel> response = emailRestController.createEmail(emailModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailModel, response.getBody());
    }

    /**
     * Test case #5
     * @throws EmailNotADraftException when no Mail Found
     */
    @Test
    void updateEmail_ShouldReturnUpdatedEmail_WhenDraft() throws EmailNotADraftException {
        when(emailService.updateEmail(emailModel)).thenReturn(emailModel);

        ResponseEntity<EmailModel> response = emailRestController.udpateEmail(emailModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(emailModel, response.getBody());
    }

    /**
     * Test case #6
     * @throws EmailNotADraftException when no Mail Found
     */
    @Test
    void updateEmail_ShouldReturnBadRequest_WhenNotADraft() throws EmailNotADraftException {
        when(emailService.updateEmail(emailModel)).thenThrow(new EmailNotADraftException(""));

        ResponseEntity<EmailModel> response = emailRestController.udpateEmail(emailModel);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    /**
     * Test case #7
     */
    @Test
    void deleteEmail_ShouldReturnOk_WhenEmailDeleted() {
        UUID emailId = UUID.randomUUID();
        doNothing().when(emailService).deleteEmail(emailId);

        ResponseEntity<EmailModel> response = emailRestController.deleteEmail(emailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test case #8
     */
    @Test
    void deleteAllEmails_ShouldReturnOk_WhenAllEmailsDeleted() {
        doNothing().when(emailService).deleteAllEmails();

        ResponseEntity<EmailModel> response = emailRestController.deleteAllEmails();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Test case #9
     */
    @Test
    void sendEmail_ShouldReturnOk_WhenEmailSent() {
        UUID emailId = UUID.randomUUID();
        doNothing().when(emailService).sendEmail(emailId);

        ResponseEntity<EmailModel> response = emailRestController.sendEmail(emailId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
