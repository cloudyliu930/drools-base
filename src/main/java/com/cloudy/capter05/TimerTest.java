package com.cloudy.capter05;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/11/28.
 */
public class TimerTest extends BaseTest {

    @Test
    public void testTimer() {
        KieSession kieSession = super.getKieSession("timer");

        // kieSession.fireUntilHalt();
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
