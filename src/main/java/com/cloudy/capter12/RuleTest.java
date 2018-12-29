package com.cloudy.capter12;

import com.cloudy.capter01.Person;
import com.cloudy.BaseTest;
import org.junit.Test;
import org.kie.api.runtime.KieSession;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author cloudy
 * @createTime 2018/12/13
 * @description
 */
public class RuleTest extends BaseTest {

    @Test
    public void testRule1() {
        KieSession kieSession = super.getKieSession("rule1");

        Person person = new Person();
        person.setName("1");

        kieSession.insert(person);
        kieSession.fireAllRules();

    }

    @Test
    public void testBlockingQueue() {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
        queue.poll();
        for (int i = 0; i < 20; i++) {
            if (queue.size() == 10) {
                queue.poll();
            }
            queue.offer(String.valueOf(i));
        }

        while (queue.iterator().hasNext()) {
            System.out.println(queue.poll());
        }
    }

}
