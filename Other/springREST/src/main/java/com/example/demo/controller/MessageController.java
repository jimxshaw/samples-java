package com.example.demo.controller;

import com.example.demo.model.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/message")
    public Message send() {
        return new Message("This is the first message!");
    }

    @PostMapping("/message")
    public Message echo(@RequestBody Message message) {
        return message;
    }
}
