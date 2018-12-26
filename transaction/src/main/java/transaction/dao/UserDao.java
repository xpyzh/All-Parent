package transaction.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import transaction.po.User;

/**
 * Created by youzhihao on 2018/12/21.
 * 调试mybatis和spring事务相结合的代码
 */
@Repository
public class UserDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public int insert(User user) {
        return sqlSessionTemplate.insert("transaction.dao.UserDao.insert", user);
    }

}
