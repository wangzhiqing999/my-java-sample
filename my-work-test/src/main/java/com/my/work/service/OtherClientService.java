package com.my.work.service;


import java.util.List;

/**
 * 额外的测试服务.
 *
 * 假设我这套系统， 一开始， 是为 甲行业做的。
 * 定义了  甲行业的接口： ClientService
 * 为 甲行业的两家公司， 分别写了实现： ClientAServiceImpl， ClientBServiceImpl
 *
 *
 * 现在，业务拓展了， 准备为  相似的 乙行业写。
 * 定义了  乙行业的接口： OtherClientService
 * 为 乙行业的两家公司， 分别写了实现： OtherClientCServiceImpl， OtherClientDServiceImpl
 *
 */
public interface OtherClientService {


    /**
     * 获取客户信息.
     * @return
     */
    String getClientInfo();




    List<String> getTodoList(String typeCode);

}
