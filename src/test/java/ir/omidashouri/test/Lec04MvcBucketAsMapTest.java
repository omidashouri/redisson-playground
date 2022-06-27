package ir.omidashouri.test;

import org.junit.jupiter.api.Test;
import org.redisson.client.codec.StringCodec;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Map;

public class Lec04MvcBucketAsMapTest extends MvcBaseTest{

    @Test
    public void bucketAsMap(){
//        ##set user:1:name omid1
//        ##set user:2:name omid2
//        ##set user:3:name omid3
        Map<String, Object> stringObjectMap = this.client.getBuckets(StringCodec.INSTANCE)
                .get("user:1:name", "user:2:name", "user:3:name");
        stringObjectMap.entrySet().forEach(i->
                System.out.println(i.getKey() +" : "+i.getValue()));
    }
}
