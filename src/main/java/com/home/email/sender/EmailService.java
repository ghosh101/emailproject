package com.home.email.sender;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text) throws Exception;
}
