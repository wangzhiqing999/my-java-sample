package cn.wzq.studyspringjob.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class DemoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public Integer execute01() {
        logger.info("[execute01]");
        sleep(10);
        return 1;
    }

    public Integer execute02() {
        logger.info("[execute02]");
        sleep(5);
        return 2;
    }

    private static void sleep(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
 
}
