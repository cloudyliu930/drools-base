package com.cloudy.capter05;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/9/10.
 */
public class DateEffectiveTest extends BaseTest {

    @Test
    public void testDateEffective() {
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        KieSession kieSession = super.getKieSession("date-effective");

        kieSession.fireAllRules();
        kieSession.dispose();
    }

    @Test
    public void testTimes() {
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        KieSession kieSession = super.getKieSession("date-effective");

        kieSession.fireUntilHalt();
        kieSession.dispose();
    }

}
