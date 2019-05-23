package common.jdbc.configuration;


import common.jdbc.annotation.ShardKey;
import common.jdbc.shard.ShardKeyStore;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class ShardKeyAspect {

    @Before("@annotation(shardKey)")
    public void doBefore(JoinPoint point, ShardKey shardKey) throws Throwable {
        ShardKeyStore.set(shardKey.value());
    }

    @After("@annotation(shardKey)")
    public void doAfter(JoinPoint point, ShardKey shardKey) {
        ShardKeyStore.clear();
    }
}
