package com.toba.tobaflow.test.dozer;

import lombok.extern.slf4j.Slf4j;
import org.dozer.DozerBeanMapper;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class DozerTest {
    public static void main(String[] args) {
        DozerBeanMapper mapper = new DozerBeanMapper();

        // 도메인 객체 To 도메인 객체 변환
        TestSource source = new TestSource("test", "address-test", 10);
        TestDest dest = mapper.map(source, TestDest.class);
        log.info("============================================================================================");
        log.info("dest: "+dest);
        log.info("============================================================================================");


        // Map To 도메인 객체 변환
        Map<String, Object> testMap = new HashMap<String, Object>();
        testMap.put("name", "goodname-1");
        testMap.put("address", "address-1");
        testMap.put("age", 20);

        TestDest dest2 = mapper.map(testMap, TestDest.class);
        log.info("============================================================================================");
        log.info("dest2: " + dest2);
        log.info("============================================================================================");

    }
}
