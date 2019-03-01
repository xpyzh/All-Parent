package transaction.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

import transaction.po.User;

/**
 * Created by youzhihao on 2018/12/21.
 * 调试mybatis和spring事务相结合的代码
 */
@Repository
public class UserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    //数据库路径
    @Value("${username}")
    private String userName = "root";
    @Value("${password}")
    private String password = "root";

    @PostConstruct
    public void init() {
        password = "init";
    }

    public int insert(User user) {
        return sqlSessionTemplate.insert("transaction.dao.UserDao.insert", user);
    }

}
