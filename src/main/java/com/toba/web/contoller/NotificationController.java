package com.toba.web.contoller;

import com.toba.app.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@RestController
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * @title 로그인 한 유저 sse 연결
     */
    @GetMapping(value = "/subscribe/{id}", produces = "text/event-stream")
    public SseEmitter subscribe(@PathVariable Long id,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.subscribe(id, lastEventId);
    }

    @GetMapping(value = "/test", produces = "text/event-stream")
    public void subscribe(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("good start4ed...");
        response.setContentType("text/event-stream"); // Header에 Content Type을 Event Stream으로 설정
        response.setCharacterEncoding("UTF-8"); // Header에 encoding을 UTF-8로 설정
        PrintWriter writer = response.getWriter();

        String uuid = UUID.randomUUID().toString();
        for(int i = 1; i <= 1; i++) {

            String jsonData = "data: { \"message\" : \"number : "+ uuid + "\" }\n";
            System.out.println("good start4ed...data: "+jsonData);

            writer.write("data: { \"message\" : \"number : "+ uuid + "\" }\n\n");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
        }
        writer.close();

    }

}