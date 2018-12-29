package com.cloudy.capter15;

import com.alibaba.fastjson.JSON;
import com.cloudy.BaseTest;
import com.ygts.security.riskcontrol.ruleengine.server.fact.RunContext;
import com.ygts.security.riskcontrol.ruleengine.server.fact.Variables;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cloudy
 * @createTime 2018/12/29
 * @description
 */
public class RCSTest extends BaseTest {

    @Test
    public void testCondition1() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition1").setFocus();

        Variables variables = new Variables();
        variables.put("ExpOrderOpenTestAccount", 2, Integer.class, "ExpOrderOpenTestAccount");
        variables.put("UserName", "test101234", String.class, "UserName");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition2() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition2").setFocus();

        Variables variables = new Variables();
        variables.put("ExpOrderOrderSource", "2,3,3a,a", String.class, "ExpOrderOrderSource");
        variables.put("orderSource", "a", String.class, "orderSource");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition3() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition3").setFocus();

        Variables variables = new Variables();
        variables.put("sourceOrderId", null, String.class, "sourceOrderId");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition4() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition4").setFocus();

        Variables variables = new Variables();
        variables.put("username", "2", String.class, "用户名");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition5() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition5").setFocus();

        Variables variables = new Variables();
        variables.put("payments", null, List.class, "支付明细");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition6() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition6").setFocus();

        Variables variables = new Variables();
        variables.put("details", null, List.class, "订单明细");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

    @Test
    public void testCondition7() {
        KieSession kieSession = super.getKieSession("rcs_condition");
        kieSession.getAgenda().getAgendaGroup("rcs_condition7").setFocus();

        Variables variables = new Variables();
        variables.put("price", 100, Double.class, "实付价格");
        variables.put("totalPrice", 100, Double.class, "总金额");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }

}
