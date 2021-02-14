package cn.wzq.springsample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("cn.wzq.springsample.mapper")//使用MapperScan批量扫描所有的Mapper接口；
public class SpringsampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsampleApplication.class, args);
	}

}
