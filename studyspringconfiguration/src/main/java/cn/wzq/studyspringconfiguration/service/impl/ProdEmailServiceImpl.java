package cn.wzq.studyspringconfiguration.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import cn.wzq.studyspringconfiguration.service.EmailService;



@Service
@Profile("prod") //生产环境.
public class ProdEmailServiceImpl implements EmailService {

	@Override
	public void send() {
		System.out.println("ProdEmailServiceImpl.send().生产环境执行邮件的发送.");
	}

}
