package ir.omidashouri.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Lec01MvcKeyValueTest extends MvcBaseTest{

    @Test
    public void keyValueAccessTest(){
        RBucket<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        bucket.set("omidmvc");
        System.out.println(bucket.get());
        Assertions.assertEquals("omidmvc",bucket.get());
    }

    @Test
    public void keyValueExpiryTest(){
        RBucket<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        bucket.set("omidExpiry",10, TimeUnit.SECONDS);
//        ##ttl user:1:name
    }

    @Test
    public void keyValueExtendExpiryTest(){
        RBucket<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        bucket.set("omidExpiry",10, TimeUnit.SECONDS);
        String omidExpiry = bucket.get();

        System.out.println(omidExpiry);

        sleep(5000);
        bucket.expire(Duration.ofSeconds(60));

        Long ttl = bucket.remainTimeToLive();
        System.out.println(ttl);
    }
}
