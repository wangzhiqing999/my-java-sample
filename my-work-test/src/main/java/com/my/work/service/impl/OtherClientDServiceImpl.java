package com.my.work.service.impl;

import com.my.work.service.OtherClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Profile("otherClientD")
public class OtherClientDServiceImpl implements OtherClientService {


    /**
     * 获取客户信息.
     *
     * @return 客户信息字符串
     */
    @Override
    public String getClientInfo() {
        return "客户D - 我是新行业的处理";
    }


    /**
     * 获取待办列表.
     *
     * @param typeCode 业务类型编码
     * @return 待办项列表
     */
    @Override
    public List<String> getTodoList(String typeCode) {
        List<String> resultList = new ArrayList<>();

        resultList.add("D02");
        resultList.add("D04");
        resultList.add("D06");

        return resultList;
    }
}
