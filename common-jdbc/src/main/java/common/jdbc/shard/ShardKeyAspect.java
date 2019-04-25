package common.jdbc.shard;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class ShardKeyAspect {

    @Pointcut("@annotation(common.jdbc.shard.ShardKey)")
    public void shardKeyPointcut() {
    }

    @Before("shardKeyPointcut()")
    public void doBefore(JoinPoint point) throws Throwable {

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        ShardKey shardKey = method.getAnnotation(ShardKey.class);
        if (shardKey == null) {
            return;
        }

        ShardKeyStore.set(shardKey.value());
    }

    @After("shardKeyPointcut()")
    public void doAfter(JoinPoint point) {
        ShardKeyStore.clear();
    }
}
