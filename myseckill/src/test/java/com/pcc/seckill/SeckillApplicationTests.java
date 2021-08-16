package com.pcc.seckill;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class SeckillApplicationTests {

    @Test
    void contextLoads() {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime);
    }

}
