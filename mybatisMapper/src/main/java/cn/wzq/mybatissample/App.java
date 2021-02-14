package cn.wzq.mybatissample;


import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;


import cn.wzq.mybatissample.mapper.UserMapper;


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
        
        
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        
        
        System.out.println("### Test getUserById...");
        User user = mapper.getUserById(3);
        System.out.println(user);
        
        
        
        System.out.println("### Test addUser...");
        User user2 = new User();
        user2.setUsername("赵六");
        user2.setAddress("北京");
        int insert = mapper.addUser(user2);
        System.out.println(insert);
        
        
        
        
        System.out.println("### Test getUserByName...");
        User user3 = mapper.getUserByName("赵六");
        System.out.println(user3);
        
        
        
        System.out.println("### Test updateUser...");
        user3.setUsername("赵六六");
        int update = mapper.updateUser(user3);
        System.out.println(update);

        
        
        System.out.println("### Test getUserById...");
        user = mapper.getUserById(user3.getId());
        System.out.println(user);
        
        
        System.out.println("### Test deleteUserById...");
        int delete = mapper.deleteUserById(user3.getId());
        System.out.println(delete);

        
        
        System.out.println("### Test getAllUser...");
        
        List<User> list = mapper.getAllUser();
        System.out.println(list);
        
        
        sqlSession.close();
    }
}
