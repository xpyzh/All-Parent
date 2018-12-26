package transaction.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import transaction.dao.UserDao;
import transaction.po.User;

/**
 * Created by youzhihao on 2018/12/20.
 */
@Component
public class UserManager {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void mockRequiredTx(User user) {
        userDao.insert(user);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void mockRequiresNewTx(User user) {
        userDao.insert(user);
    }
}
