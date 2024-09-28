package com.gbtec.av532.tasks;

import com.gbtec.av532.services.EmailService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SpamTask extends QuartzJobBean {

    private final EmailService emailService;

    public SpamTask(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        emailService.markAsSpam();
    }
}
