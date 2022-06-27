package ir.omidashouri.test;

import ir.omidashouri.test.dto.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RBucketReactive;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

public class Lec02MvcKeyValueObjectTest extends MvcBaseTest{

    @Test
    public void keyValueObjectTest(){
        Student student = new Student("omid", 40, "tehran", Arrays.asList(1,2,3));
        RBucket<Student> bucket = this.client.getBucket("student:1", new TypedJsonJacksonCodec(Student.class));
        bucket.set(student);
        System.out.println(bucket.get());
        Assertions.assertEquals(student,bucket.get());
    }
}
