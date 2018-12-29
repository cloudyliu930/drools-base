package com.cloudy.capter06;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * @author cloudy
 * @createTime 2018/11/28
 * @description
 */
public class FunctionTest extends BaseTest {

    public static String Hello(String name) {
        return "Hello" + name;
    }

    @Test
    public void testFunction() {
        KieSession kieSession = super.getKieSession("agendaGroup");
        kieSession.getAgenda().getAgendaGroup("fun").setFocus();

        int result = kieSession.fireAllRules();

        System.out.println("命中规则" + result + "条");

        kieSession.dispose();
    }

}
