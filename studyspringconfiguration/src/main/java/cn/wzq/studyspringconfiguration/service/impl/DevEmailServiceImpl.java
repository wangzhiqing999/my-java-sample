package cn.wzq.studyspringconfiguration.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import cn.wzq.studyspringconfiguration.service.EmailService;


@Service
@Profile("dev") //开发环境的时候.
public class DevEmailServiceImpl implements EmailService {

	@Override
	public void send() {
		System.out.println("DevEmailServiceImpl.send().开发环境不执行邮件的发送.");
	}

}
