package com.home.email.reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

@Service
public class InboxReaderImpl implements InboxReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(InboxReaderImpl.class);

    @Value("${spring.mail.username}")
    String username;

    @Value("${spring.mail.password}")
    String password;

    @Value("${imap.server:imap.gmail.com}")
    String server;

    @Value("${imap.port:993}")
    String port;

    Properties properties = new Properties();
    Folder emailFolder;
    //Store store;

    @PostConstruct
    void setup() {
        properties.put("mail.imap.host", server);
        properties.put("mail.imap.port", port);
        properties.put("mail.store.protocol", "imaps");
        Session emailSession = Session.getDefaultInstance(properties);

        try {
            Store store = emailSession.getStore("imaps");
            store.connect(server, username, password);
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            LOGGER.info("Inbox Type: {}", emailFolder.getType());
            LOGGER.info("Full Name: {}", emailFolder.getFullName());
        } catch (Exception e) {
            LOGGER.error("Exception connecting to gmail", e);
        }
    }

    @Override
    public void read() throws Exception {
        Message[] messages = emailFolder.getMessages();
        LOGGER.info("messages.length---" + messages.length);
        for (int i = 0; i < messages.length; i++) {
            Message message = messages[i];
            LOGGER.info("--------------------------------");
            LOGGER.info("Email Number " + (i + 1));
            LOGGER.info("From: " + message.getFrom()[0]);
            LOGGER.info("Received Date: " + message.getReceivedDate());
            LOGGER.info("Subject: " + message.getSubject());
        }
        emailFolder.close(false);
    }
}
