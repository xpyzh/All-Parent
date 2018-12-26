package transaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import transaction.po.User;
import transaction.service.UserService;

/**
 * Created by youzhihao on 2018/12/20.
 * 注解事务的入口:TransactionInterceptor.invoke()
 * api事务的入口:TransactionTemplate.execute()
 * 核心类:
 * DataSourceTransactionManager
 *  1.DataSourceTransactionObject:当前事务持有的数据库连接等
 *  2.DefaultTransactionStatus:事务的基本信息，已经前一个事务的信息等
 * TransactionSynchronizationManager:保存当前线程上下文中的事务同步器相关信息
 *  1.TransactionSynchronization:事务同步器
 *  2.resources:<datasource,connection>
 * SqlSessionTemplate:重点看其中的代理方法invoke，会在第一次获取sqlsession的时候绑定到TransactionSynchronizationManager的resource上面
 *
 *
 *
 */
@RestController
public class TestController {
    @Autowired
    private UserService userService;


    @RequestMapping("/mockSingleTx")
    public void mockSingleTx() {
        userService.mockSingleTx(new User("youzhihao"));
    }

    @RequestMapping("/mockRequiredTx")
    public void mockRequiredTx() {
        userService.mockRequiredTx(new User("youzhihao"));
    }

    @RequestMapping("/mockRequiresNewTx")
    public void mockRequiresNewTx() {
        userService.mockRequiresNewTx(new User("youzhihao"));
    }
}
