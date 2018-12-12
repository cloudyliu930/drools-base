package com.cloudy;

import org.kie.api.KieServices;
import org.kie.api.internal.utils.KieService;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * Created by Administrator on 2018/9/9.
 */
public class BaseTest {

    protected KieSession getKieSession(String sessionName) {
        System.setProperty("drools.dateformat", "yyyy-MM-dd");
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.newKieClasspathContainer();
        KieSession session = kieContainer.newKieSession(sessionName);
        return session;
    }

    protected StatelessKieSession getStatelessKieSession(String sessionName) {
        System.setProperty("drools.dateformat", "yyyy-MM-dd");
        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        StatelessKieSession statelessKieSession = container.newStatelessKieSession(sessionName);
        return statelessKieSession;
    }

    public void fireAllRules(KieSession kieSession) {
        int result = kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println("规则执行了" + result + "次");
    }
}
