package com.gbtec.av532.model;

import java.util.List;

public record EmailDto(String emailFrom, List<String> emailTo, List<String> emailCC, List<String> emailBCC, String emailSubject, String emailBody) {
}
