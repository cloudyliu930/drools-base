package com.cloudy.capter01;

import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2018/9/5.
 */
public class DroolsTest extends BaseTest {

    @Test
    public void testHello() throws Exception {
        KieSession session = super.getKieSession("hello");
        Person person = new Person();
        person.setAge(100);
        FactHandle handle = session.insert(person);
        int count = session.fireAllRules();

        System.out.println("name:" + person.getName() + ",age:" + person.getAge() + ", count:" + count);
        session.dispose();
    }

    @Test
    public void testFactHandle() {
        KieSession session = super.getKieSession("hello");

        Person person = new Person();
        person.setAge(100);

        FactHandle handle = session.insert(person);
        System.out.println(handle.toExternalForm());
        int count = session.fireAllRules();
        System.out.println("name:" + person.getName() + ",age:" + person.getAge() + ", count:" + count);

        person.setAge(120);
        session.update(handle, person);
        int count1 = session.fireAllRules();
        System.out.println("name:" + person.getName() + ",age:" + person.getAge() + ", count:" + count1);
        session.dispose();

    }
}
