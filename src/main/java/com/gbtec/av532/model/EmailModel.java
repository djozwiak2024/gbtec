package com.gbtec.av532.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "EMAIL")
public class EmailModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String from;
    private String to;
    private String subject;
    private String body;
    private EmailState state;

    public EmailModel(){}

    public EmailModel(UUID id, String from, String to, String subject, String body, EmailState state) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.state = state;
    }
}