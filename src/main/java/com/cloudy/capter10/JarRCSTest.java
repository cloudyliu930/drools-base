package com.cloudy.capter10;

import com.cloudy.BaseTest;
import com.cloudy.capter13.EtcdUtil;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.internal.io.ResourceFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cloudy
 * @createTime 2018/12/27
 * @description
 */
public class JarRCSTest extends BaseTest {

    @Test
    public void batchGeneratorYgJar() throws IOException {
        final String RULESFILE_NAME = "rules.drl";
        // final String rules      = "package rule.capter10; import com.cloudy.capter10.Message; rule \"Hello World \" salience 10 when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";

        StringBuffer stringBuffer = new StringBuffer("package rule; import com.ygts.security.riskcontrol.ruleengine.server.fact.Variables; \n" +
                "import com.ygts.security.riskcontrol.ruleengine.server.fact.RunContext; \n" +
                "import java.util.List; \n");
        for (int i = 0; i < 1; i++) {      // 580 50kb   1180 100kb
//            stringBuffer.append("rule \"Hello World\" \n" +
//                        "salience 10 \n" +
//                    "when " +
//                        "vs:Variables() \n " +
//                        "context: RunContext(vs.get(\"username\", \"String\", \"用户名\") == \"zhangsan\" && vs.get(\"ageScore\", \"Double\", \"年龄分\") == \"18\") \n " +
//                    "then " +
//                        "vs.append( \"reasonCode\", \"blackage\", \"String\", \"原因编码码\" ); " +
//                        "vs.append( \"reasonCode\", \"blackage\", \"String\", \"原因编码码\" ); " +
//                        "vs.put( \"risCode\", \"90000\", \"String\", \"决策码\" ); end;");

            stringBuffer.append("rule \"Hello World\"\n" +
                    "salience 10\n" +
                    "dialect \"mvel\"\n" +
                    "when\n" +
                    " vs: Variables()\n" +
                    " context: RunContext(vs.get(\"username\", String, \"用户名\") == \"2\" && vs.get(\"ageScore\", Double, \"年龄分\") == 3 && vs.get(\"list\", List, \"标签\").size() == 2)\n" +
                    "then\n" +
                    " vs.append( \"reasonCode\", \"blackage\", \"String\", \"原因编码码\" );\n" +
                    " vs.append( \"reasonCode\", \"_blackage\", \"String\", \"原因编码码\" );\n" +
                    " vs.append(\"successCode\", \"100\", Integer, \"成功编码\");\n" +
                    " vs.append(\"successCode\", \"100\", Integer, \"成功编码\");\n" +
                    " vs.put( \"risCode\", \"90000\", \"String\", \"决策码\" );\n" +
                    "end;");
        }

        KieServices kieServices = KieServices.Factory.get();
        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com", "cloudy4", "1.0.0");
        // 创建初始化的kjar
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                new ResourceWrapper(ResourceFactory.newByteArrayResource(stringBuffer.toString().getBytes()), RULESFILE_NAME));
        writeFile(kJar.getBytes(), releaseId.getArtifactId() + releaseId.getVersion() + ".jar");
    }


    @Test
    public void testPut() throws Exception {
        File file = new File("d:\\kjar\\cloudy41.0.0.jar");
        byte[] bytes = getBytes(file);
        EtcdUtil.init();
        String jarName = "yg41.0.0.jar";
        EtcdUtil.putEtcdByteValueByKey(jarName, bytes);
    }

    @Test
    public void testDel() {
        String jarName = "yg41.0.0.jar";
        EtcdUtil.deleteEtcdValueByKey(jarName);
    }


    private static void writeFile(byte[] bs, String fileName) throws IOException {
        OutputStream out = new FileOutputStream("d:\\kjar\\" + fileName);
        InputStream is = new ByteArrayInputStream(bs);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }

    private byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

}
