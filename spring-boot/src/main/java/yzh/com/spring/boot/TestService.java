package yzh.com.spring.boot;

import com.unicom.auth.framework.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/9/30 9:27 上午 <br/>
 * @Author: youzhihao
 */
@Service
public class TestService {

    @Autowired
    private AuthService authService;

}
