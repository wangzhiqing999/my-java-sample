package cn.wzq.studylombok;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App 
{
    public static void main( String[] args )
    {

        // 测试 Log.
        log.debug("Test Debug");
        log.info("Test Info");
        log.warn("Test Warn");
        log.error("Test Error");
        

        // 测试 @Data 是否生效.
        User user = new User();
        user.setUserName("ZhangSan");
        System.out.println(user.getUserName());



        // 测试 @Builder 、 @Builder.Default 是否生效.
        User user2 = User.builder()
                .id(123L)
                .name("张三")
                .userName("zhang3")
                .age(25)
                .email("zhangsan@test.com")
                .build();
        // 测试 @ToString  是否生效.
        System.out.println(user2);

    }
}