package com.cloudy;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

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

}
