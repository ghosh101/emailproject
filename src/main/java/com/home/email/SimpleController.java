package com.home.email;

import com.home.email.reader.InboxReader;
import com.home.email.sender.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleController.class);

    @Autowired
    private EmailService emailService;

    @Autowired
    private InboxReader inboxReader;

    @GetMapping("/send")
    public String sendMail() {
        String to = "sahoo.sailaja@gmail.com";
        String subject = "Hi from Spring app";
        String text = "Hello Sailaha";

        try {
            emailService.sendSimpleMessage(to, subject, text);
        } catch (Exception e) {
            LOGGER.error("Exception while sending email", e);
            return "error sending email";
        }
        return "read mail successfully";
    }

    @GetMapping("/read")
    public String readMail() {
        try {
            inboxReader.read();
        } catch (Exception e) {
            LOGGER.error("Exception reading gmail", e);
            return "error reading gmail";
        }
        return "read mail successfully";
    }
}
