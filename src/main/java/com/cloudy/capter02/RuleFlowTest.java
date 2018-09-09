package com.cloudy.capter02;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/9/9.
 */
public class RuleFlowTest extends BaseTest {

    @Test
    public void testRuleFlowGroup() {
        KieSession kieSession = super.getKieSession("ruleFlowGroup");

        kieSession.getAgenda().getAgendaGroup("rule-flow-group-01").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("rule-flow-group-02").setFocus();
        kieSession.fireAllRules();

        kieSession.getAgenda().getAgendaGroup("rule-flow-group-01").setFocus();
        kieSession.fireAllRules();


        kieSession.dispose();

    }

    @Test
    public void testLockOnActive() {
        KieSession kieSession = super.getKieSession("ruleFlowGroup");
    }

}
