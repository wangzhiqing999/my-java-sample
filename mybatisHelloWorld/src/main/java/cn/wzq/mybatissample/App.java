package cn.wzq.mybatissample;


import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;



import cn.wzq.mybatissample.model.*;;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Hello World!" );
        
        
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        
        
        System.out.println("### Test getUserById...");
        User user = (User) sqlSession.selectOne("cn.wzq.mybatissample.getUserById", 3);
        System.out.println(user);
        
        
        
        System.out.println("### Test addUser...");
        User user2 = new User();
        user2.setUsername("赵六");
        user2.setAddress("北京");
        int insert = sqlSession.insert("cn.wzq.mybatissample.addUser", user2);
        System.out.println(insert);
        sqlSession.commit();
        
        
        
        System.out.println("### Test getUserByName...");
        User user3 = (User) sqlSession.selectOne("cn.wzq.mybatissample.getUserByName", "赵六");
        System.out.println(user3);
        
        
        
        System.out.println("### Test updateUser...");
        user3.setUsername("赵六六");
        int update = sqlSession.update("cn.wzq.mybatissample.updateUser", user3);
        System.out.println(update);
        sqlSession.commit();
        
        
        System.out.println("### Test getUserById...");
        user = (User) sqlSession.selectOne("cn.wzq.mybatissample.getUserById", user3.getId());
        System.out.println(user);
        
        
        System.out.println("### Test deleteUserById...");
        int delete = sqlSession.delete("cn.wzq.mybatissample.deleteUserById", user3.getId());
        System.out.println(delete);
        sqlSession.commit();
        
        
        System.out.println("### Test getAllUser...");
        
        List<Object> list = sqlSession.selectList("cn.wzq.mybatissample.getAllUser");
        System.out.println(list);
        
        
        sqlSession.close();
    }
}
