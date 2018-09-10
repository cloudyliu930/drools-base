package com.cloudy.capter04;

import com.cloudy.BaseTest;
import com.cloudy.capter03.Person;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/9/9.
 */
public class AgendaGroupTest extends BaseTest {

    @Test
    public void testRuleFlowGroup() {
        KieSession kieSession = super.getKieSession("agenda-group");
        kieSession.getAgenda().getAgendaGroup("abcd").setFocus();

        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
