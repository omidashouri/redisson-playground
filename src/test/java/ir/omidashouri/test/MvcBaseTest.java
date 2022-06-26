package ir.omidashouri.test;

import ir.omidashouri.test.config.RedissonConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class MvcBaseTest {

    private final RedissonConfig redissonConfig = new RedissonConfig();
    protected RedissonClient client;

    @BeforeAll
    public void setClient(){
        this.client = redissonConfig.getClient();
    }

    @AfterAll
    public void shutdown(){
       this.client.shutdown();
    }

    protected void sleep(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
