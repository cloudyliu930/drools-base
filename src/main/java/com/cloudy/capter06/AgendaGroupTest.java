package com.cloudy.capter06;

import com.cloudy.BaseTest;
import com.cloudy.capter01.Person;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.AgendaFilter;
import org.kie.api.runtime.rule.Match;

/**
 * @author cloudy
 * @createTime 2018/11/28
 * @description
 */
public class AgendaGroupTest extends BaseTest {

    @Test
    public void testAgenda() {
        KieSession kieSession = super.getKieSession("agendaGroup");
        kieSession.getAgenda().getAgendaGroup("a").setFocus();

        Person person = new Person();
        kieSession.insert(person);

        AgendaFilterTest agendaFilter = new AgendaFilterTest();

        int result = kieSession.fireAllRules(agendaFilter);
        kieSession.dispose();
        System.out.println("触发了" + result + "个规则");
    }


    class AgendaFilterTest implements AgendaFilter {

        @Override
        public boolean accept(Match match) {

            return true;
        }
    }

}
