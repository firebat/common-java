package common.monitor.configuration;

import com.google.common.base.Strings;
import common.monitor.Monitor;
import common.monitor.annotation.Counted;
import common.monitor.annotation.Timed;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class MonitorAspect {

    @Around("@annotation(counted)")
    public Object counter(ProceedingJoinPoint joinPoint, Counted counted) throws Throwable {

        final String name = Strings.isNullOrEmpty(counted.value())
                ? joinPoint.getSignature().toShortString()
                : counted.value();

        Monitor.recordValue(name, 1);

        return joinPoint.proceed();
    }

    @Around("@annotation(timed)")
    public Object timer(ProceedingJoinPoint joinPoint, Timed timed) throws Throwable {

        final String name = Strings.isNullOrEmpty(timed.value())
                ? joinPoint.getSignature().toShortString()
                : timed.value();

        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        Monitor.recordTime(name, duration);
        return proceed;
    }
}
