package com.cloudy.capter06;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.definition.type.FactType;
import org.kie.api.runtime.KieSession;

/**
 * @author cloudy
 * @createTime 2018/11/28
 * @description
 */
public class DeclareTest extends BaseTest {

    @Test
    public void testDeclare() throws IllegalAccessException, InstantiationException {
        KieSession kieSession = super.getKieSession("declare");
        kieSession.getAgenda().getAgendaGroup("dec").setFocus();


        FactType factType =  kieSession.getKieBase().getFactType("rules.capter06", "Address");
        Object object = factType.newInstance();

        factType.set(object, "name", "zhangsan");

        kieSession.insert(object);
        int result = kieSession.fireAllRules();
        System.out.println("命中规则" + result + "条");
    }

}
