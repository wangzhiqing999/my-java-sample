package com.my.work.task;


import com.my.work.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务.
 */
@Component
@Slf4j
public class TestScheduledTask {

    @Autowired
    private TestService testService;


    /**
     * 定时执行任务.
     * 定时处理的时间， 写在 cron 上面。
     *
     * 例如 0 0 8 * * ?  表示 每天 8 点执行.
     *
     * 这里为了测试，简单写 0 * * * * ?
     * 也就是 每分钟执行一次， 以便于测试的时候， 观察。
     */
    @Scheduled(cron = "0 * * * * ?")
    public void callDailyTask() {
        String result = testService.dailyTask();
        log.info("定时任务调用结果：" + result);
    }

}
