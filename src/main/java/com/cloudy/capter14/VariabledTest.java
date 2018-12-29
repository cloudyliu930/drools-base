package com.cloudy.capter14;

import com.cloudy.BaseTest;
import com.ygts.security.riskcontrol.ruleengine.server.fact.RunContext;
import com.ygts.security.riskcontrol.ruleengine.server.fact.Variables;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import java.util.*;

/**
 * @author cloudy
 * @createTime 2018/12/26
 * @description
 */
public class VariabledTest extends BaseTest {

    @Test
    public void testVariabled() {
        KieSession kieSession = super.getKieSession("variable_test");

        List<String> list = new ArrayList<String>();
        list.add("a");
        list.add("b");

        Variables variables = new Variables();
        variables.put("username", "2", String.class, "用户名");
        variables.put("ageScore", "3", Double.class, "年龄分");
        variables.put("list", list, ArrayList.class, "年龄分");
        kieSession.insert(variables);

        RunContext runContext = new RunContext();
        kieSession.insert(runContext);
        kieSession.fireAllRules();

        System.out.println(variables);
    }
}
