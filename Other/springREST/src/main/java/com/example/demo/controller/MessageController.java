package com.example.demo.controller;

import com.example.demo.model.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MessageController {

    @ResponseBody
    @GetMapping("/message")
    public Message send() {
        return new Message("This is the first message!");
    }
}
