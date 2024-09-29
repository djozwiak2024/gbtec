package com.gbtec.av532.tasks;

import com.gbtec.av532.services.EmailService;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpamTask {

    private final EmailService emailService;
    //for future use as property
    private final String spamBot = "carl@gbtec.com";

    public SpamTask(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * Starts every day at 10:00 and marks spamBot-Mails as SPAM
     * @throws JobExecutionException
     */
    @Scheduled(cron = "0 0 10 * * *")
    public void execute() throws JobExecutionException {
        emailService.markAsSpam(spamBot);
    }
}
