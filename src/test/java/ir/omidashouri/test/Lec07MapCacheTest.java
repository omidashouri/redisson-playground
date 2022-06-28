package ir.omidashouri.test;

import ir.omidashouri.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec07MapCacheTest extends BaseTest{

    @Test
    public void mapCacheTest(){
        TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);
        RMapCacheReactive<Integer, Student> mapCache = this.client.getMapCache("user:cache", codec);

        Student student1 = new Student("omid1", 41, "Tehran1", List.of(1, 2, 3));
        Student student2 = new Student("omid2", 42, "Tehran1", List.of(10, 20, 30));

        final Mono<Student> st1 = mapCache.put(1, student1, 5, TimeUnit.SECONDS);
        final Mono<Student> st2 = mapCache.put(2, student2, 10, TimeUnit.SECONDS);

        StepVerifier.create(st1.then(st2).then()).verifyComplete();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();

        sleep(3000);

        mapCache.get(1).doOnNext(System.out::println).subscribe();
        mapCache.get(2).doOnNext(System.out::println).subscribe();
    }
}
