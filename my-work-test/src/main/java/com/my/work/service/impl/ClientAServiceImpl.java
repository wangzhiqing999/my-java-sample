package com.my.work.service.impl;

import com.my.work.service.ClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("clientA")
public class ClientAServiceImpl implements ClientService {

    /**
     * 获取客户信息.
     *
     * @return 客户信息字符串
     */
    @Override
    public String getClientInfo() {
        return "客户A";
    }



    /**
     * 获取待办列表.
     *
     * @return 待办项列表
     */
    @Override
    public List<String> getTodoList() {
        List<String> resultList = new ArrayList<>();
        resultList.add("A01");
        resultList.add("A02");
        resultList.add("A03");

        return resultList;
    }

}
