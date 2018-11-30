package com.cloudy.capter10;

import com.cloudy.BaseTest;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.io.*;

/**
 * @author liuchuangui
 * @createTime 2018/11/30
 * @description 生成jar包，读取jar包
 */
public class JarTest extends BaseTest {

    @Test
    public void testJar() {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        KieModule kModule = kr.addKieModule(ks.getResources().newFileSystemResource(new File("d:\\cloudy.jar")));

        KieContainer kContainer = ks.newKieContainer(kModule.getReleaseId());

        KieSession kSession = kContainer.newKieSession();

        Message message = new Message();
        message.setStatus("0");
        kSession.insert(message);

        int result = kSession.fireAllRules();
        System.out.println("功触发规则" + result);
    }

    @Test
    public void generatorJar() throws IOException {
        final String RULESFILE_NAME = "rules.drl";
        final String rules      = "package rule.capter10; import com.cloudy.capter10.Message; rule \"Hello World \" when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";

        KieServices kieServices = KieServices.Factory.get();
        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com", "cloudy", "1.0.0");
        // 创建初始化的kjar
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                new ResourceWrapper(ResourceFactory.newByteArrayResource(rules.getBytes()), RULESFILE_NAME));
        writeFile(kJar.getBytes(), releaseId.getArtifactId() + ".jar");
    }

    private static void writeFile(byte[] bs, String fileName) throws IOException {
        OutputStream out = new FileOutputStream("d:\\" + fileName);
        InputStream is = new ByteArrayInputStream(bs);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }

}
