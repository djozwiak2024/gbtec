package com.gbtec.av532.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "EMAIL")
public class EmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;
    private String emailFrom;
    @ElementCollection
    private List<String> emailTo;

    @ElementCollection
    private List<String> emailCC;

    @ElementCollection
    private List<String> emailBCC;

    private String emailSubject;
    private String emailBody;
    private EmailState state;

    private LocalDateTime lastChangeDateTime;

    public EmailModel(){
    }

    public EmailModel(String emailFrom, List<String> emailTo, List<String> emailCC, List<String> emailBCC, String emailSubject, String emailBody) {
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.emailCC = emailCC;
        this.emailBCC = emailBCC;
        this.emailSubject = emailSubject;
        this.emailBody = emailBody;
    }

    public UUID getEmailId() {
        return emailId;
    }

    public void setEmailId(UUID emailId) {
        this.emailId = emailId;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public List<String> getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(List<String> emailTo) {
        this.emailTo = emailTo;
    }

    public String getEmailSubject() {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject) {
        this.emailSubject = emailSubject;
    }

    public String getEmailBody() {
        return emailBody;
    }

    public void setEmailBody(String body) {
        this.emailBody = body;
    }

    public EmailState getState() {
        return state;
    }

    public void setState(EmailState state) {
        this.state = state;
    }

    public List<String> getEmailCC() {
        return emailCC;
    }

    public void setEmailCC(List<String> emailCC) {
        this.emailCC = emailCC;
    }

    public List<String> getEmailBCC() {
        return emailBCC;
    }

    public void setEmailBCC(List<String> emailBCC) {
        this.emailBCC = emailBCC;
    }

    public LocalDateTime getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    public void setLastChangeDateTime(LocalDateTime lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }
}