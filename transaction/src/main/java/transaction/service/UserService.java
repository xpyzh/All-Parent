package transaction.service;

import transaction.po.User;

/**
 * Created by youzhihao on 2019/1/15.
 */
public interface UserService {

    void mockSingleTx(User user);

    void mockRequiredTx(User user);

    void mockRequiresNewTx(User user);
}
