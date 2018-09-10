package com.cloudy.capter03;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * Created by Administrator on 2018/9/9.
 */
public class SalienceTest extends BaseTest {

    @Test
    public void testRuleFlowGroup() {
        KieSession kieSession = super.getKieSession("salience");

        Person person = new Person();
        person.setAge(10);

        kieSession.insert(person);
        kieSession.fireAllRules();
        kieSession.dispose();
    }

}
