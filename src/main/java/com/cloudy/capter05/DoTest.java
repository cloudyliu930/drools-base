package com.cloudy.capter05;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.builder.KieBuilder;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/11/28.
 */
public class DoTest extends BaseTest {

    @Test
    public void testDo() {
        KieSession kieSession = super.getKieSession("timer");

        kieSession.fireAllRules();

        kieSession.dispose();
    }
}
