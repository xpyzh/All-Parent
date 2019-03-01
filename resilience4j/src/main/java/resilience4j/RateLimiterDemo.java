package resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 限流控制的demo
 * Created by youzhihao on 2019/2/18.
 *
 */
public class RateLimiterDemo {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    public static void main(String[] args) throws Throwable {
        // For example you want to restrict the calling rate of some method to be not higher than 10 req/ms.
        RateLimiterConfig config = new RateLimiterConfig.Builder()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(1)
                .timeoutDuration(Duration.ofMillis(25))
                .build();
        // Create registry
        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(config);

        // Use registry
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("backend");
        CheckedRunnable restrictedCall = RateLimiter
                .decorateCheckedRunnable(rateLimiter, () -> {
                    System.out.println(dateFormat.format(new Date()) + " 执行业务");
                });
        //动态改变限流值
        Executors.newScheduledThreadPool(1).schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("change qos to 100");
                rateLimiter.changeLimitForPeriod(10);
            }
        }, 10, TimeUnit.SECONDS);
        while (true) {
            Try.run(restrictedCall).onFailure(throwable -> {
                return;
            });
        }
    }
}
