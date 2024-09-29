package com.gbtec.av532.services;

import com.gbtec.av532.exceptions.EmailNotADraftException;
import com.gbtec.av532.exceptions.EmailNotFoundException;
import com.gbtec.av532.model.EmailModel;
import com.gbtec.av532.model.EmailState;
import com.gbtec.av532.repositories.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
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
        email.setState(EmailState.DRAFT);
        email.setLastChangeDateTime(LocalDateTime.now());
        EmailModel saved = emailRepository.save(email);
        log.info("New email created: {}", saved.getEmailId());
        return saved;
    }

    /**
     *
     * @param email
     * @return the updated emailModel
     * @throws EmailNotADraftException if Email is not in Draft State
     */
    public EmailModel updateEmail(EmailModel email) throws EmailNotADraftException {
        if(EmailState.DRAFT.equals(email.getState())) {
            email.setLastChangeDateTime(LocalDateTime.now());
            EmailModel updated = emailRepository.save(email);
            log.info("Email updated successfully: {}", updated.getEmailId());
            return updated;
        } else {
            log.error("Email {} not a draft", email.getEmailId());
            throw new EmailNotADraftException("Email is not in Draft State");
        }
    }

    /**
     * Searches all Emails on it's attributes to, from, cc and bcc
     * whether it contains a malicious sender / recipient and marks it as spam
     * @param spamMail
     */
    public void markAsSpam(String spamMail){
        Iterable<EmailModel> potentialSpamMails = emailRepository.findAllByEmailFromContainingIgnoreCaseOrEmailToContainingIgnoreCaseOrEmailBCCContainingIgnoreCaseOrEmailCCContainingIgnoreCase(spamMail, spamMail, spamMail, spamMail);
        potentialSpamMails.forEach(email -> {
            email.setLastChangeDateTime(LocalDateTime.now());
            email.setState(EmailState.SPAM);
            emailRepository.save(email);
            log.info("Email successfully marked as spam: {}", email.getEmailId());
        });
    }

    /**
     * Usually would delete via emailRepository.deleteById()
     * but due to requirements we flag the mails as "deleted".
     * @param id
     */
    public void deleteEmail(UUID id) {
        Optional<EmailModel> byId = emailRepository.findById(id);
        byId.ifPresent(email -> {
            email.setState(EmailState.DELETED);
            email.setLastChangeDateTime(LocalDateTime.now());
            emailRepository.save(email);
            log.info("Email deleted: {}", email.getEmailId());
        });
    }

    /**
     * Searches for all EMails and wraps deleteEmail(UUID)
     */
    public void deleteAllEmails() {
        emailRepository.findAll().forEach(email -> deleteEmail(email.getEmailId()));
    }

    /**
     * Sends EMails which have the State DRAFT
     * @param id
     */
    public void sendEmail(UUID id){
        emailRepository.findById(id).ifPresent(email -> {
            if(EmailState.DRAFT.equals(email.getState())){
                email.setState(EmailState.SENT);
                email.setLastChangeDateTime(LocalDateTime.now());
                emailRepository.save(email);
                log.info("Email sent successfully: {}", email.getEmailId());
            }else{
                log.error("Email {} not sent, because it's in a different state: {}", email.getEmailId(), email.getState());
            }
        });
    }
}
