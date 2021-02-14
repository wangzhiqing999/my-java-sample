package cn.wzq.mybatissample;


import java.io.IOException;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;


import cn.wzq.mybatissample.mapper.UserMapper;


import cn.wzq.mybatissample.pojo.*;;



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
        
        
        System.out.println("### Test selectByPrimaryKey...");
        User user = mapper.selectByPrimaryKey(3);
        System.out.println(user);
        
        
        
        System.out.println("### Test insert...");
        User user2 = new User();
        user2.setUsername("赵六");
        user2.setAddress("北京");
        int insert = mapper.insert(user2);
        System.out.println(insert);
        
        
        
        
        System.out.println("### Test selectByExample...");
        
        
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo("赵六");
        
        List<User> userList = mapper.selectByExample(example);
        User user3 = userList.get(0);
        System.out.println(user3);
        
        
        
        System.out.println("### Test updateByPrimaryKey...");
        user3.setUsername("赵六六");
        int update = mapper.updateByPrimaryKey(user3);
        System.out.println(update);

        
        
        System.out.println("### Test selectByPrimaryKey...");
        user = mapper.selectByPrimaryKey(user3.getId());
        System.out.println(user);
        
        
        System.out.println("### Test deleteByPrimaryKey...");
        int delete = mapper.deleteByPrimaryKey(user3.getId());
        System.out.println(delete);

        
        
        System.out.println("### Test selectByExample...");
        
        example = new UserExample();

        List<User> list = mapper.selectByExample(example);
        System.out.println(list);
        
        
        sqlSession.close();
    }
}
