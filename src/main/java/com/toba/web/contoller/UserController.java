package com.toba.web.contoller;

import com.toba.app.service.UserEmitService;
import com.toba.app.utils.ScStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserEmitService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseBodyEmitter users() {
        System.out.println("users...rest api called.."+ ScStringUtils.getCurrentTime());
        SseEmitter emitter = new SseEmitter(5000L);
        service.add(emitter);
        return emitter;
    }
}