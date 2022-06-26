package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLongReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

public class Lec03NumberTest extends BaseTest{

    @Test
    public void keyValueIncreaseTest(){
        RAtomicLongReactive atomicLong = this.client.getAtomicLong("user:1:visit");
        Mono<Void> mono = Flux.range(1, 30).delayElements(Duration.ofSeconds(1)).flatMap(i -> atomicLong.incrementAndGet()).then();
        StepVerifier.create(mono).verifyComplete();
    }
}
