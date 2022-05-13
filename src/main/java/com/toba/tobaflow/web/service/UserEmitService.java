package com.toba.tobaflow.web.service;

import com.toba.tobaflow.web.service.vo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class UserEmitService {

    private static final int REPEAT = 13;
    private final Map<ResponseBodyEmitter, AtomicInteger> emitterCountMap = new HashMap<>();

    public void add(ResponseBodyEmitter emitter) {
        emitterCountMap.put(emitter, new AtomicInteger(0));
    }

    @Scheduled(fixedRate = 100L)
    public void emit() {

        //System.out.println("emit scheudled.. run..");
        List<ResponseBodyEmitter> toBeRemoved = new ArrayList<>(emitterCountMap.size());

        for (Map.Entry<ResponseBodyEmitter, AtomicInteger> entry : emitterCountMap.entrySet()) {

            Integer count = entry.getValue().incrementAndGet();
//            User user = new RestTemplate().getForObject("https://jsonplaceholder.typicode.com/users/{id}", User.class, count);

            User user = new User("test", "address");
            ResponseBodyEmitter emitter = entry.getKey();
            try {
                System.out.println("emit send..");
                emitter.send(user);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                toBeRemoved.add(emitter);
            }

            if (count >= REPEAT) {
                toBeRemoved.add(emitter);
            }
        }

        for (ResponseBodyEmitter emitter : toBeRemoved) {
            emitterCountMap.remove(emitter);
        }
    }
}