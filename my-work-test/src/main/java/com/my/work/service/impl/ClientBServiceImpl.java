package com.my.work.service.impl;

import com.my.work.service.ClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("clientB")
public class ClientBServiceImpl  implements ClientService {

    /**
     * 获取客户信息.
     *
     * @return 客户信息字符串
     */
    @Override
    public String getClientInfo() {
        return "客户B - (我是客户B的业务逻辑)";
    }


    /**
     * 获取待办列表.
     *
     * @return 待办项列表
     */
    @Override
    public List<String> getTodoList() {
        List<String> resultList = new ArrayList<>();
        resultList.add("B01");
        resultList.add("B02");
        resultList.add("B03");

        return resultList;
    }

}
