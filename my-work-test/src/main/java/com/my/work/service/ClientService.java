package com.my.work.service;


/**
 * 这个接口，是假定某个项目，是一个通用的项目， 但是，某些具体的业务， 不同的客户，有不同的实现。
 * 下面的方法，用于定义， 不同的客户，有不同的实现的情况.
 */
public interface ClientService {

    /**
     * 获取客户信息.
     * @return
     */
    String getClientInfo();
}
