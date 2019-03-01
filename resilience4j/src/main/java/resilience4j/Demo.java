package resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

/**
 * Created by youzhihao on 2019/2/18.
 */
public class Demo {
    public static void main(String[] args)
    {
        // Given
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("testName");

// When I decorate my function and invoke the decorated function
        CheckedFunction0<String> checkedSupplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> {
            throw new RuntimeException("BAM!");
        });
        Try<String> result = Try.of(checkedSupplier)
                .recover(throwable -> "Hello Recovery");
        System.out.println(result.isSuccess());
        System.out.println(result.get());
    }

}
