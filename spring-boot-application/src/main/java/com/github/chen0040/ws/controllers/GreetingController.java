package com.github.chen0040.ws.controllers;

import com.github.chen0040.ws.models.AuditEvent;
import com.github.chen0040.ws.models.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController {

    @MessageMapping("/hello")
    @SendTo("/topics/event")
    public AuditEvent greeting(HelloMessage helloMessage) throws Exception {
        return new AuditEvent("greeting", "Hello," + helloMessage.getName() + "!");
    }
}