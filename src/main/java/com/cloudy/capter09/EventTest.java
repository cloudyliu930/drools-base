package com.cloudy.capter09;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

/**
 * @author cloudy
 * @createTime 2018/11/29
 * @description
 */
public class EventTest extends BaseTest {

    @Test
    public void testEvent() {
        KieSession kieSession = super.getKieSession("event");
        kieSession.addEventListener(new RuleRuntimeEventListenerImpl());
        super.fireAllRules(kieSession);
    }

}
