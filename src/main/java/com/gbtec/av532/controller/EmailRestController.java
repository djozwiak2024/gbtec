package com.gbtec.av532.controller;

import com.gbtec.av532.exceptions.EmailNotADraftException;
import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.services.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("emails")
public class EmailRestController {

    private final EmailService emailService;

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
