package com.observer.pattern;


import org.jsmart.zerocode.core.domain.JsonTestCase;
import org.jsmart.zerocode.core.domain.TargetEnv;
import org.jsmart.zerocode.core.runner.ZeroCodeUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

@TargetEnv("screening_service_host.properties")
@RunWith(ZeroCodeUnitRunner.class)
public class DBServiceImplTest {

//    @Autowired
//    private RedisService redisService;

    @Test
    @JsonTestCase("load_tests/get/get_screening_details_by_custid.json")
    public void getLockerTest() throws Exception {
//        long leftLimit = 0L;
//        long rightLimit = 899L;
//        Long lockerId = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
//        log.info("printing lockerId {} " ,lockerId);
//        redisService.getFromRedis(lockerId);
//    }
    }

}
