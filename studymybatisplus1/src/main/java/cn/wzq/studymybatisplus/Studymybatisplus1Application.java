package cn.wzq.studymybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.wzq.studymybatisplus.mapper")
public class Studymybatisplus1Application {

	public static void main(String[] args) {
		SpringApplication.run(Studymybatisplus1Application.class, args);
	}

}
