package cn.wzq.studyspringjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class Studyspringasyncjob1Application {

	public static void main(String[] args) {
		SpringApplication.run(Studyspringasyncjob1Application.class, args);
	}

}
