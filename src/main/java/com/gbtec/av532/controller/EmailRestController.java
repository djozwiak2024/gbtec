package com.gbtec.av532.controller;

import com.gbtec.av532.exceptions.EmailNotADraftException;
import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * EmailRestController implements CRUD functionalities
 * It allows to create, read, update and delete Emails.
 * Furthermore, it allows to bulk create, read and delete Emails.
 */
@RestController
@RequestMapping("emails")
public class EmailRestController {

    //EmailService as layer between Controller and Repositories and to run logic
    private final EmailService emailService;

    //Inject Service
    public EmailRestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<EmailModel> getEmail(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(emailService.getEmailById(id));
        } catch (EmailNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmailModel>> getAllEmails() {
        return ResponseEntity.ok(emailService.getAllEmails());
    }

    @PostMapping("/create")
    public ResponseEntity<EmailModel> createEmail(@RequestBody EmailModel email) {
        return ResponseEntity.ok(emailService.createNewEmail(email));
    }

    @PostMapping("/createMultiple")
    public ResponseEntity<EmailModel> createMultipleEmail(@RequestBody List<EmailModel> emails) {
        try{
            emails.forEach(emailService::createNewEmail);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<EmailModel> udpateEmail(@RequestBody EmailModel email) {
        try {
            return ResponseEntity.ok(emailService.updateEmail(email));
        } catch (EmailNotADraftException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmailModel> deleteEmail(@PathVariable UUID id) {
        emailService.deleteEmail(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<EmailModel> deleteAllEmails() {
        emailService.deleteAllEmails();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/send/{id}")
    public ResponseEntity<EmailModel> sendEmail(@PathVariable UUID id) {
        emailService.sendEmail(id);
        return ResponseEntity.ok().build();
    }

}
