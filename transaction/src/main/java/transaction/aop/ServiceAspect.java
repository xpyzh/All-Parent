package transaction.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Created by youzhihao on 2019/1/4.
 */
@Aspect
@Component
public class ServiceAspect {


    @Around("execution(public *  transaction.service..*.*(..))")
    public Object ajaxResultProcess(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("ServiceAspect");
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw throwable;
        }
    }
}
