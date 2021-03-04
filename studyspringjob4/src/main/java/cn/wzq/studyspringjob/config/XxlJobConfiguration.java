package cn.wzq.studyspringjob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XxlJobConfiguration {

	
    private Logger logger = LoggerFactory.getLogger(getClass());	
	
	
    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;
    @Value("${xxl.job.executor.appname}")
    private String appName;
    @Value("${xxl.job.executor.ip}")
    private String ip;
    @Value("${xxl.job.executor.port}")
    private int port;
    @Value("${xxl.job.accessToken}")
    private String accessToken;
    @Value("${xxl.job.executor.logpath}")
    private String logPath;
    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
    	
    	
    	logger.info("adminAddresses = {}", adminAddresses);
    	logger.info("appName = {}", appName);
    	logger.info("ip = {}", ip);
    	logger.info("port = {}", port);
    	logger.info("accessToken = {}", accessToken);
    	logger.info("logPath = {}", logPath);
    	logger.info("logRetentionDays = {}", logRetentionDays);
    	
    	
    	
        // 创建 XxlJobSpringExecutor 执行器
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(adminAddresses);
        xxlJobSpringExecutor.setAppName(appName);
        xxlJobSpringExecutor.setIp(ip);
        xxlJobSpringExecutor.setPort(port);
        xxlJobSpringExecutor.setAccessToken(accessToken);
        xxlJobSpringExecutor.setLogPath(logPath);
        xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        // 返回
        return xxlJobSpringExecutor;
    }

}
