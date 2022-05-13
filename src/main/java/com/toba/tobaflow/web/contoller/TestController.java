package com.toba.tobaflow.web.contoller;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;

import com.toba.tobaflow.web.service.vo.TestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TaskExecutor taskExecutor;

    Collection<TestPojo> list = null;

    @PostConstruct
    public void init() {
        list = new ArrayList<TestPojo>();
        list.add(new TestPojo("1","test1"));
        list.add(new TestPojo("2","test2"));
        list.add(new TestPojo("3","test3"));
        list.add(new TestPojo("4","test4"));
        list.add(new TestPojo("5","test5"));
        for (int ii = 6; ii < 100; ii++) {
            list.add(new TestPojo(Integer.toString(ii), "test"+ii));
        }
    }

    @GetMapping("/emitter1")
    public ResponseBodyEmitter emitter1() {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        taskExecutor.execute(()->{
            try {
                for(TestPojo tmpData : list) {
                    emitter.send(tmpData);
                }
                emitter.complete();
            }catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    @GetMapping("/emitter2")
    public ResponseEntity<ResponseBodyEmitter> emitter2() {
        final ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        taskExecutor.execute(()->{
            try {
                for(TestPojo tmpData : list) {
                    emitter.send(tmpData);
                }
                emitter.complete();
            }catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
                .header("Custom-Header", "Custom-Value")
                .body(emitter);
    }

    @GetMapping("/sseEmitter")
    public SseEmitter sseEmitter() {
        final SseEmitter emitter = new SseEmitter(5000L);

        System.out.println("send tmpData started =====");
        taskExecutor.execute(()->{
            try {
                for(TestPojo tmpData : list) {
                    Thread.sleep(20);
                    System.out.println("sended: "+tmpData);
                    emitter.send(tmpData);
                }
                emitter.complete();
            }catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    @GetMapping("/sseEmitter2")
    public SseEmitter sseEmitter2() {
        final SseEmitter emitter = new SseEmitter(5000L);

        taskExecutor.execute(()->{
            try {
                for(TestPojo tmpData : list) {
                    Thread.sleep(1000);
                    emitter.send(emitter.event().id(String.valueOf(tmpData.hashCode())).data(tmpData));
                }
                emitter.complete();
            }catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}