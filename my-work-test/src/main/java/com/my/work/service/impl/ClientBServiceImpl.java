package com.my.work.service.impl;

import com.my.work.service.ClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("clientB")
public class ClientBServiceImpl  implements ClientService {

    /**
     * 获取客户信息.
     * @return
     */
    public String getClientInfo() {
        return "客户B - (我是客户B的业务逻辑)";
    }

}
