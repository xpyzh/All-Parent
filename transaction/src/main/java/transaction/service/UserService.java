package transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import transaction.dao.UserDao;
import transaction.manager.UserManager;
import transaction.po.User;

/**
 * Created by youzhihao on 2018/12/20.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserManager userManager;

    //模拟单独的事务
    @Transactional()
    public void mockSingleTx(User user) {
        userDao.insert(user);
    }

    @Transactional
    public void mockRequiredTx(User user) {
        userDao.insert(user);
        userManager.mockRequiredTx(user);
    }

    @Transactional
    public void mockRequiresNewTx(User user) {
        userDao.insert(user);
        userManager.mockRequiresNewTx(user);
    }
}
