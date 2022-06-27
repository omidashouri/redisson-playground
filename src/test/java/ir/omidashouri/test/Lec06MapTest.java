package ir.omidashouri.test;

import ir.omidashouri.test.dto.Student;
import org.junit.jupiter.api.Test;
import org.redisson.api.RMapReactive;
import org.redisson.client.codec.StringCodec;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

public class Lec06MapTest extends BaseTest{

    @Test
    public void mapTest1(){

        RMapReactive<String, String> map = this.client.getMap("user:1", StringCodec.INSTANCE);
        Mono<String> name = map.put("name", "omid");
        Mono<String> age = map.put("age", "41");
        Mono<String> city = map.put("city", "tehran");
        StepVerifier.create(name.concatWith(age).concatWith(city).then()).verifyComplete();
    }
//    ##hgetall user:1
//    ##hget user:1 age


    @Test
    public void mapTest2(){

        RMapReactive<String, String> map = this.client.getMap("user:2", StringCodec.INSTANCE);
        final Map<String, String> javaMap = Map.of("name", "omid2",
                "age", "42",
                "city", "tehran2");
       ;
        StepVerifier.create(map.putAll(javaMap).then()).verifyComplete();
    }
//    ##hgetall user:2
//    ##hget user:2 age


    @Test
    public void mapTest3(){

        final TypedJsonJacksonCodec codec = new TypedJsonJacksonCodec(Integer.class, Student.class);

        RMapReactive<Integer, Student> map = this.client.getMap("users", codec);
        Student student1 = new Student("omid1", 41, "Tehran1", List.of(1, 2, 3));
        Student student2 = new Student("omid2", 42, "Tehran1", List.of(12, 22, 32));
        final Mono<Student> mono1 = map.put(1, student1);
        final Mono<Student> mono2 = map.put(2, student2);
        StepVerifier.create(mono1.concatWith(mono2).then()).verifyComplete();
    }
//    ##hgetall users
//    ##hget users 1
}
