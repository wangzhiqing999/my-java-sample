package cn.wzq.studyspringnacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import cn.wzq.studyspringnacos.pojo.OrderProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class StudyspringnacosconfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyspringnacosconfigApplication.class, args);
	}
	
	
	
	
    @Component
    public class OrderPropertiesCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private OrderProperties orderProperties;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + orderProperties.getPayTimeoutSeconds());
            logger.info("createFrequencySeconds:" + orderProperties.getCreateFrequencySeconds());
        }

    }

    @Component
    public class ValueCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

//        @NacosValue(value = "${order.pay-timeout-seconds}")
        @Value(value = "${order.pay-timeout-seconds}")
        private Integer payTimeoutSeconds;

//        @NacosValue(value = "${order.create-frequency-seconds}")
        @Value(value = "${order.create-frequency-seconds}")
        private Integer createFrequencySeconds;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + payTimeoutSeconds);
            logger.info("createFrequencySeconds:" + createFrequencySeconds);
        }
    }
    
    

}
