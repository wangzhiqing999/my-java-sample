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

    @Select("select 1")
    int selectTest();

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
     * @param logText
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
     * 保存配置信息.
     * @param p_code
     * @param p_value
     */
    @Update("SELECT fn_save_config(#{p_code}, #{p_value}::json)")
    void fn_save_config(@Param("p_code") String p_code, @Param("p_value") String p_value);


    /**
     * 获取配置信息.
     * @param p_code
     * @return
     */
    @Select("select fn_get_config(#{p_code})")
    String fn_get_config(@Param("p_code") String p_code);

}
