package cn.wzq.studylombok;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    public static void main( String[] args )
    {
        
        log.debug("Test Debug");
        log.info("Test Info");
        log.warn("Test Warn");
        log.error("Test Error");
        
        
        User user = new User();
        user.setUserName("ZhangSan");        
        
        System.out.println(user.getUserName());
    }
}