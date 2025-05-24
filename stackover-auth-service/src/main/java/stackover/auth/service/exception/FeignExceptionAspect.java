package stackover.auth.service.exception;

import feign.FeignException;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeignExceptionAspect {

    @AfterThrowing(
            pointcut = "execution(* stackover.auth.service.feign.ProfileServiceClient.*(..))",
            throwing = "ex"
    )
    public void handleFeignExceptions(Exception ex) {
        if (ex instanceof FeignException.NotFound) {
            throw new EntityNotFoundException(ex.getMessage());
        } else {
            throw new FeignRequestException(ex.getMessage());
        }
    }
}
