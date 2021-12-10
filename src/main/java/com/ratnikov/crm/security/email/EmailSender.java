package com.ratnikov.crm.security.email;

public interface EmailSender {
    void send(String to, String email);
}
