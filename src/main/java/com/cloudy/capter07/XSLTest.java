package com.cloudy.capter07;

import com.cloudy.BaseTest;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.template.ObjectDataCompiler;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author cloudy
 * @createTime 2018/11/29
 * @description
 */
public class XSLTest extends BaseTest {

    @Test
    public void testXLS() throws FileNotFoundException {
        File file = new File("D:\\code\\demo\\drools-base\\src\\main\\resources\\rules\\capter07\\hello.xls");
        InputStream is = new FileInputStream(file);
        SpreadsheetCompiler converter = new SpreadsheetCompiler();
        String drl = converter.compile(is, InputType.XLS);
        System.out.println(drl);
    }

    @Test
    public void testXSL1() {
        Resource dis = ResourceFactory.newClassPathResource("rules/capter07/hello.xls",
                XSLTest.class);
        KieHelper helper = new KieHelper();
        helper.addResource(dis,ResourceType.DTABLE);
        KieSession ksession = helper.build().newKieSession();
        
        int i = ksession.fireAllRules();
        System.out.println( " " + i + "次");
        ksession.dispose();
    }
}
