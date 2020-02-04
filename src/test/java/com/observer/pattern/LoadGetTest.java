package com.observer.pattern;


import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.runner.parallel.ZeroCodeLoadRunner;
import org.junit.runner.RunWith;

@LoadWith("load_generation.properties")
@TestMapping(testClass = DBServiceImplTest.class, testMethod = "getLockerTest")
@RunWith(ZeroCodeLoadRunner.class)
public class LoadGetTest {
}
