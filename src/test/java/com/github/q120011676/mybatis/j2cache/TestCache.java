package com.github.q120011676.mybatis.j2cache;

import com.github.q120011676.mybatis.j2cache.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by say on 3/22/16.
 */
public class TestCache {
    public static void main(String[] args) throws IOException, InterruptedException {
        //mybatis的配置文件
        String resource = "/conf.xml";
        //使用类加载器加载mybatis的配置文件（它也加载关联的映射文件）
        InputStream is = TestCache.class.getResourceAsStream(resource);
        /// /构建sqlSession的工厂
        SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(is);
        //使用MyBatis提供的Resources类加载mybatis的配置文件（它也加载关联的映射文件）
        //Reader reader = Resources.getResourceAsReader(resource);
        //构建sqlSession的工厂
        //SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //创建能执行映射文件中sql的sqlSession
        SqlSession session = sessionFactory.openSession();
        /**
         * 映射sql的标识字符串，
         * me.gacl.mapping.userMapper是userMapper.xml文件中mapper标签的namespace属性的值，
         * getUser是select标签的id属性值，通过select标签的id属性值就可以找到要执行的SQL
         */
        User u = new User();
        u.setName("aa11pppppppp010iipppp");
      int n =  session.insert("add",u);
        System.out.println(n);
        String statement = "getUser";//映射sql的标识字符串
        //执行查询返回一个唯一user对象的sql
        User user = session.selectOne(statement, 1);
        System.out.println(user);
        user.setName("bbb");
        session.update("upUser", user);
        user = session.selectOne(statement, 1);
        System.out.println(user);
        session.commit();
    }
}
