package com.cloudy.capter08;

import com.cloudy.BaseTest;
import com.cloudy.capter01.Person;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

/**
 * @author liuchuangui
 * @createTime 2018/11/29
 * @description
 */
public class DSLRTest extends BaseTest {

    @Test
    public void tsetkeyword() throws Exception {
        Resource dsl = ResourceFactory.newClassPathResource("rules/capter08/PersonDSL.dsl");
        Resource dslr = ResourceFactory.newClassPathResource("rules/capter08/PersonDslr.dslr");
        KieHelper helper = new KieHelper();
        helper.addResource(dsl, ResourceType.DSL);
        helper.addResource(dslr, ResourceType.DSLR);
        KieSession session = helper.build().newKieSession();
        Person p = new Person();
        p.setAge(30);
        p.setName("stilton");
        session.insert(p);
        int i = session.fireAllRules();
        System.out.println(p.getPost() + p.getAge() + p.getName());
        session.dispose();
    }
}
