package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.api.*;
import org.redisson.client.codec.LongCodec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Lec09ListQueueStackTest extends BaseTest{

    @Test
    public void listTest(){
        final RListReactive<Long> list = this.client.getList("number-input", LongCodec.INSTANCE);

//        Mono<Void> listAdd = Flux.range(1, 10).map(Long::valueOf).flatMap(list::add).then();
//        StepVerifier.create(listAdd).verifyComplete();

        List<Long> longList = LongStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        StepVerifier.create(list.addAll(longList).then()).verifyComplete();

        StepVerifier.create(list.size()).expectNext(10).verifyComplete();

    }
//    #lrange number-input 0 -1



//    first run listTest
    @Test
    public void queueTest(){
        RQueueReactive<Long> queue = this.client.getQueue("number-input", LongCodec.INSTANCE);

//        remove from beginning
        Mono<Void> queuePoll = queue.poll().repeat(3).doOnNext(System.out::println).then();

        StepVerifier.create(queuePoll).verifyComplete();
        StepVerifier.create(queue.size()).expectNext(6).verifyComplete();

    }


    //    first run listTest then queueTest
    @Test
    public void stackTest(){ //use Deque instead Stack (double ended queue)
        RDequeReactive<Long> deque = this.client.getDeque("number-input", LongCodec.INSTANCE);

        final Mono<Void> stackPoll = deque.pollLast().repeat(3).doOnNext(System.out::println).then();

        StepVerifier.create(stackPoll).verifyComplete();
        StepVerifier.create(deque.size()).expectNext(2).verifyComplete();

    }

}
