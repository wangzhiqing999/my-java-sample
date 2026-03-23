package com.my.work.service.impl;

import com.my.work.service.ClientService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 假设我这套系统， 一开始， 是为 甲行业做的。
 * 定义了  甲行业的接口： ClientService
 * 为 甲行业的两家公司， 分别写了实现： ClientAServiceImpl， ClientBServiceImpl
 *
 *
 * 现在，业务拓展了， 准备为  相似的 乙行业写。
 * 定义了  乙行业的接口： OtherClientService
 * 为 乙行业的两家公司， 分别写了实现： OtherClientCServiceImpl， OtherClientDServiceImpl
 *
 *
 * 这个类是  甲行业的  空白实现.
 * 也就是，当项目是为 乙行业 打包的时候，  不使用 甲行业的 业务逻辑，
 */
@Service
@Profile({"clientEmpty"})
public class ClientEmptyServiceImpl  implements ClientService {

    /**
     * 获取客户信息.
     *
     * @return
     */
    @Override
    public String getClientInfo() {
        return "甲行业的 空白实现";
    }

    @Override
    public List<String> getTodoList() {
        return new ArrayList<>();
    }
}
