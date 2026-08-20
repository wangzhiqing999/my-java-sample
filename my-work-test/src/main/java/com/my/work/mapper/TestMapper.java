package com.my.work.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * 测试使用 ibatis，做数据库操作的例子.
 */
@Mapper
public interface TestMapper {

    /**
     * 数据库连接自检：执行 {@code select 1}.
     *
     * @return 固定返回 1
     */
    @Select("select 1")
    int selectTest();

    /**
     * 获取数据库版本信息.
     *
     * @return PostgreSQL 版本字符串
     */
    @Select("select version()")
    String selectFunction();



    /**
     * 测试 无参数、无返回值.
     * 注意：没有返回值的情况下，使用 @Update
     */
    @Update("SELECT test_nop_nor()")
    void callTestNopNor();


    /**
     * 测试 有参数，无返回值.
     * 注意：没有返回值的情况下，使用 @Update
     * @param logText 日志文本内容
     */
    @Update("SELECT test_havep_nor(#{logText})")
    void callTestHavepNor(@Param("logText") String logText);


    /**
     * 调用 test_havep_haver 函数，插入日志并返回新记录的ID
     * @param logText 日志文本内容
     * @return 新插入记录的ID
     */
    @Select("select test_havep_haver(#{logText})")
    Long testHavepHaver(@Param("logText") String logText);

    /**
     * 调用 test_havep_haverj 函数，插入日志并返回JSON结果
     * @param logText 日志文本内容
     * @return JSON格式的操作结果，包含code、msg和id字段
     */
    @Select("select test_havep_haverj(#{logText})")
    Map<String, Object> testHavepHaverj(@Param("logText") String logText);


    /**
     * 调用 test_havepj_haverj 函数，接收JSON参数并返回JSON结果
     * @param logData JSON格式的参数，需包含log_text字段
     * @return JSON格式的操作结果，包含code、msg和id字段
     */
    @Select("select test_havepj_haverj(#{logData}::json)")
    Map<String, Object> testHavepjHaverj(@Param("logData") String logData);


    /**
     * 调用数据库函数 test_sum_array（数组求和）， 参数是一个数组.
     *
     * @param datas 待求和的整型数组
     * @return 数组元素之和
     */
    @Select("SELECT test_sum_array(#{datas})")
    int testSumArray(int[] datas);




    /**
     * 保存配置信息.
     *
     * @param p_code  配置编码
     * @param p_value 配置值（JSON 字符串）
     */
    @Update("SELECT fn_save_config(#{p_code}, #{p_value}::json)")
    void fn_save_config(@Param("p_code") String p_code, @Param("p_value") String p_value);


    /**
     * 获取配置信息.
     *
     * @param p_code 配置编码
     * @return 配置值（JSON 字符串）
     */
    @Select("select fn_get_config(#{p_code})")
    String fn_get_config(@Param("p_code") String p_code);

}
