package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucket;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

public class Lec05MvcEventListenerTest extends MvcBaseTest{

    @Test
    public void expireEventTest(){

        RBucket<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        bucket.set("omid",10, TimeUnit.SECONDS);

        bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String name) {
                System.out.println("Expired : " + name);
            }
        });

        sleep(11000);
    }

   @Test
    public void deletedEventTest(){
        RBucket<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        bucket.set("omid");

        bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(String name) {
                System.out.println("Deleted : " + name);
            }
        });

        sleep(60000);
    }
//    ## del user:1:name
}
