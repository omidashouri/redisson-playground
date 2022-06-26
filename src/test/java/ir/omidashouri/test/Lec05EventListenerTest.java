package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.DeletedObjectListener;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBucketReactive;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Lec05EventListenerTest extends BaseTest{

    @Test
    public void expireEventTest(){
        RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("omid",10, TimeUnit.SECONDS);
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();

        Mono<Void> event = bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String name) {
                System.out.println("Expired : " + name);
            }
        }).then();

        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();

        sleep(11000);
    }

    @Test
    public void deletedEventTest(){
        RBucketReactive<String> bucket = this.client.getBucket("user:1:name", StringCodec.INSTANCE);
        Mono<Void> set = bucket.set("omid");
        Mono<Void> get = bucket.get().doOnNext(System.out::println).then();

        Mono<Void> event = bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(String name) {
                System.out.println("Deleted : " + name);
            }
        }).then();

        StepVerifier.create(set.concatWith(get).concatWith(event)).verifyComplete();
        sleep(60000);
    }
//    ## del user:1:name
}
