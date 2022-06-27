package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RAtomicLongReactive;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Lec03MvcNumberTest extends MvcBaseTest{

    @Test
    public void keyValueIncreaseTest(){
//        ##set user:1:visit 1
        RAtomicLong atomicLong = this.client.getAtomicLong("user:1:visit");
        IntStream.range(1, 30).forEach(i -> atomicLong.incrementAndGet());
        System.out.println(atomicLong.get());
    }
}
