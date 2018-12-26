package com.bless.controller;

import com.bless.Entity.Message;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangxi on 2018/12/14.
 */

@Api(value = "WebSocketController",description = "WebSocketController")
@Slf4j
@RestController
@CrossOrigin
//@RequestMapping("/websocket")
public class WebSocketController {

    @MessageMapping("/receive")
    @SendTo("/topic/greetings")
    public Message message(Message message){
        return message;
    }
}
