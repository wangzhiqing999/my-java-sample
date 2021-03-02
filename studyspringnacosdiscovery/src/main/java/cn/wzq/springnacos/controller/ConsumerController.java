package cn.wzq.springnacos.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @NacosInjected
    private NamingService namingService;

    private RestTemplate restTemplate = new RestTemplate();

    
    @GetMapping("/demo")
    public String consumer() throws IllegalStateException, NacosException {
        
    	// 获得实例
        Instance instance = namingService.selectOneHealthyInstance("demo-application");

        if(instance == null) {
        	return "Service Fail...";
        }
        
        // 执行请求
        return restTemplate.getForObject("http://" + instance.toInetAddr() + "/provider/demo",
                String.class);
    }

}
