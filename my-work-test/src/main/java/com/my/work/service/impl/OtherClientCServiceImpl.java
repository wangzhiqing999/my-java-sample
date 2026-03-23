package com.my.work.service.impl;

import com.my.work.service.OtherClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Profile("otherClientC")
public class OtherClientCServiceImpl implements OtherClientService {


    /**
     * 获取客户信息.
     *
     * @return
     */
    @Override
    public String getClientInfo() {
        return "客户C - 我是新行业的处理";
    }


    @Override
    public List<String> getTodoList(String typeCode) {
        List<String> resultList = new ArrayList<>();

        resultList.add("C01");
        resultList.add("C03");
        resultList.add("C05");

        return resultList;
    }

}
