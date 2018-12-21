package com.cloudy.capter10;

import com.cloudy.BaseTest;
import org.apache.poi.ss.formula.functions.T;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.drools.workbench.models.commons.backend.rule.RuleModelDRLPersistenceImpl;
import org.drools.workbench.models.datamodel.rule.RuleModel;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.internal.utils.ServiceDiscoveryImpl;
import org.kie.api.internal.utils.ServiceRegistry;
import org.kie.api.internal.utils.ServiceRegistryImpl;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.awt.*;
import java.io.*;
import java.util.*;

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
        KieModule kieModule1 = kr.addKieModule(ks.getResources().newFileSystemResource(new File("d:\\test\\cloudy21.0.9-SNAPSHOT.jar")));

        KieContainer kContainer = ks.newKieContainer(kieModule1.getReleaseId());

        KieSession kSession = kContainer.newKieSession();

        Message message = new Message();
        message.setStatus("0");
        kSession.insert(message);

        int result = kSession.fireAllRules();
        System.out.println("功触发规则" + result);
    }
    @Test
    public void testBatchJar() throws InterruptedException {
        System.setProperty("kie.repository.project.cache.size", "20000");
        System.setProperty("kie.repository.project.versions.cache.size", "100");

        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            kr.addKieModule(ks.getResources().newFileSystemResource(new File("d:\\test5\\cloudy" + i + "1.0.0.jar")));
        }

        ReleaseId releaseId = ks.newReleaseId("com", "cloudy0", "1.0.0");
        ReleaseId releaseId1 = new ReleaseIdImpl("com", "cloudy1", "1.0.0");
        KieModule kieModule1 = kr.getKieModule(releaseId);
        KieModule kieModule2 = kr.getKieModule(releaseId1);

        long endTime = System.currentTimeMillis();

        String result1 = "磁盘加载到内存耗时(毫秒)：" + (endTime - startTime);

        KieContainer kContainer = ks.newKieContainer(kieModule1.getReleaseId());
        String result2 = "创建容器时长耗时(毫秒)：" + (System.currentTimeMillis() - endTime);

        long endTime1 = System.currentTimeMillis();
        KieBase kieBase = kContainer.getKieBase("KBase");
        String result3 = "创建kbase时长耗时(毫秒)：" + (System.currentTimeMillis() - endTime1);

        Thread.sleep(3000);
        long start1Time = System.currentTimeMillis();
        int count = 100;
        for (int i = 0; i < count; i++) {
            KieSession kSession1 = kieBase.newKieSession();
            Message message = new Message();
            message.setStatus("0");
            kSession1.insert(message);

            int result = kSession1.fireAllRules();
            System.out.println("功触发规则" + result);
            kSession1.dispose();
        }
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(count + "规则执行时长耗时(毫秒)：" + (System.currentTimeMillis() - start1Time));
    }

    @Test
    public void generatorJar() throws IOException {
        final String RULESFILE_NAME = "rules.drl";
        // final String rules      = "package rule.capter10; import com.cloudy.capter10.Message; rule \"Hello World \" salience 10 when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";
        StringBuffer stringBuffer = new StringBuffer("package rule.capter10; import com.cloudy.capter10.Message; ");
        for (int i = 0; i < 10; i++) {
            stringBuffer.append("rule \"Hello World " + i + " \" salience 10 when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end;");
        }

        KieServices kieServices = KieServices.Factory.get();
        /**
         * 指定kjar包
         */
        final ReleaseId releaseId = kieServices.newReleaseId("com", "cloudy2", "1.0.9-SNAPSHOT");
        // 创建初始化的kjar
        InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                new ResourceWrapper(ResourceFactory.newByteArrayResource(stringBuffer.toString().getBytes()), RULESFILE_NAME));
        writeFile(kJar.getBytes(), releaseId.getArtifactId() + releaseId.getVersion() + ".jar");
    }



    @Test
    public void batchGeneratorJar() throws IOException {
        final String RULESFILE_NAME = "rules.drl";
        // final String rules      = "package rule.capter10; import com.cloudy.capter10.Message; rule \"Hello World \" salience 10 when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end";

        StringBuffer stringBuffer = new StringBuffer("package rule.capter10; import com.cloudy.capter10.Message; ");
        for (int i = 0; i < 1180; i++) {      // 580 50kb   1180 100kb
            stringBuffer.append("rule \"Hello World " + i + " \" salience 10 when message:Message (status == \"0\") then System.out.println(\"hello, Drools!\"); end;");
        }

        KieServices kieServices = KieServices.Factory.get();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            /**
             * 指定kjar包
             */
            final ReleaseId releaseId = kieServices.newReleaseId("com", "cloudy" + i, "1.0.0");
            // 创建初始化的kjar
            InternalKieModule kJar = DroolsUtils.createKieJar(kieServices, releaseId,
                    new ResourceWrapper(ResourceFactory.newByteArrayResource(stringBuffer.toString().getBytes()), RULESFILE_NAME));
            //writeFile(kJar.getBytes(), releaseId.getArtifactId() + releaseId.getVersion() + ".jar");
        }
        long endTime = System.currentTimeMillis();

        System.out.println(endTime - startTime);
    }

    private static void writeFile(byte[] bs, String fileName) throws IOException {
        OutputStream out = new FileOutputStream("d:\\test100\\" + fileName);
        InputStream is = new ByteArrayInputStream(bs);
        byte[] buff = new byte[1024];
        int len = 0;
        while((len=is.read(buff))!=-1){
            out.write(buff, 0, len);
        }
        is.close();
        out.close();
    }














    @Test
    public void standardTestBatchJar() throws InterruptedException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Integer loadMemoryTime = 0;
        Integer createContainerTime = 0;
        Integer createKBaseTime = 0;
        Integer executionTime = 0;
        Integer jarCount = 3000;     // jar包数量
        Integer executionCount = 3000;  // 执行次数

        String path = "test50";    // jar包大小  5kb 10个规则、 10kb 80个规则、  50kb 580个规则、  100kb  1180个规则

        int count = 1;
        for (int i = 0; i < count; i++) {
            Map<String, Integer> result = standardTestJar(jarCount, executionCount, path);
            loadMemoryTime += result.get("loadMemoryTime");
            createContainerTime += result.get("createContainerTime");
            createKBaseTime += result.get("createKBaseTime");
            executionTime += result.get("executionTime");
        }

        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("D:\\1.properties");
        properties.load(in);

        FileOutputStream oFile = new FileOutputStream("D:\\1.properties", false);//true表示追加打开

        Properties newProperties = new Properties();
//        newProperties.put(path + "loadMemoryTime", Objects.isNull(properties.getProperty(path + "loadMemoryTime")) ? "(" +  loadMemoryTime : properties.getProperty(path + "loadMemoryTime") + " + " + loadMemoryTime);
//        newProperties.put(path + "createContainerTime", Objects.isNull(properties.getProperty(path + "createContainerTime")) ? "(" +  createContainerTime : properties.getProperty(path + "createContainerTime") + " + " + createContainerTime);
//        newProperties.put(path + "createKBaseTime", Objects.isNull(properties.getProperty(path + "createKBaseTime")) ? "(" +  createKBaseTime : properties.getProperty(path + "createKBaseTime") + " + " + createKBaseTime);
//        newProperties.put(path + "executionTime", Objects.isNull(properties.getProperty(path + "executionTime")) ? "(" +  executionTime : properties.getProperty(path + "executionTime") + " + " + executionTime);
//        newProperties.put(path + "count", Objects.isNull(properties.getProperty(path + "count")) ? "(" +  1 : properties.getProperty(path + "count") + " + " + 1);

        Integer countProp = Objects.isNull(properties.getProperty(path + "count")) ? 1 : Integer.parseInt(properties.getProperty(path + "count")) + 1;

        newProperties.put(path + "loadMemoryTime", (Objects.isNull(properties.getProperty(path + "loadMemoryTime")) ? (loadMemoryTime) : Integer.parseInt(properties.getProperty(path + "loadMemoryTime")) + loadMemoryTime) + "");
        newProperties.put(path + "createContainerTime", (Objects.isNull(properties.getProperty(path + "createContainerTime")) ? (createContainerTime) : Integer.parseInt(properties.getProperty(path + "createContainerTime")) + createContainerTime)+ "");
        newProperties.put(path + "createKBaseTime", (Objects.isNull(properties.getProperty(path + "createKBaseTime")) ? (createKBaseTime) : Integer.parseInt(properties.getProperty(path + "createKBaseTime")) + createKBaseTime)+ "");
        newProperties.put(path + "executionTime", (Objects.isNull(properties.getProperty(path + "executionTime")) ? (executionTime) : Integer.parseInt(properties.getProperty(path + "executionTime")) + executionTime) + "");
        newProperties.put(path + "count", countProp + "");

        newProperties.store(oFile, "D:\\1.properties");

        System.out.println("磁盘加载到内存耗时(毫秒)：" + (loadMemoryTime/count));
        System.out.println("创建容器时长耗时(毫秒)：" +  (createContainerTime/count));
        System.out.println("创建kbase时长耗时(毫秒)：" + (createKBaseTime/count));
        System.out.println("100规则执行时长耗时(毫秒)：" + (executionTime/count));
    }

    @Test
    public void testAvg() throws IOException {
        Properties properties = new Properties();
        FileInputStream in = new FileInputStream("D:\\1.properties");
        properties.load(in);

        Integer loadMemoryTime = 0;
        Integer createContainerTime = 0;
        Integer createKBaseTime = 0;
        Integer executionTime = 0;
        String path = "test50";

        loadMemoryTime = Integer.parseInt(properties.getProperty(path + "loadMemoryTime")) / Integer.parseInt(properties.getProperty(path + "count"));
        createContainerTime = Integer.parseInt(properties.getProperty(path + "createContainerTime")) / Integer.parseInt(properties.getProperty(path + "count"));
        createKBaseTime = Integer.parseInt(properties.getProperty(path + "createKBaseTime")) / Integer.parseInt(properties.getProperty(path + "count"));
        executionTime = Integer.parseInt(properties.getProperty(path + "executionTime")) / Integer.parseInt(properties.getProperty(path + "count"));

        System.out.println("count：" + properties.getProperty(path + "count"));
        System.out.println("loadMemoryTime：" + loadMemoryTime);
        System.out.println("createContainerTime：" + createContainerTime);
        System.out.println("createKBaseTime：" + createKBaseTime);
        System.out.println("executionTime：" + executionTime);
    }

    private static Map<String, Integer> standardTestJar(int jarCount, int executionCount, String path) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.setProperty("kie.repository.project.cache.size", "20000");
        System.setProperty("kie.repository.project.versions.cache.size", "100");

        KieServices ks = KieServices.Factory.get();

        KieRepository kr = ks.getRepository();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < jarCount; i++) {
            kr.addKieModule(ks.getResources().newFileSystemResource(new File("d:\\" + path + "\\cloudy" + i + "1.0.0.jar")));
        }

        ReleaseId releaseId = ks.newReleaseId("com", "cloudy1", "1.0.0");
        KieModule kieModule1 = kr.getKieModule(releaseId);

        long endTime = System.currentTimeMillis();

        long loadMemoryTime = endTime - startTime;
        String result1 = "磁盘加载到内存耗时(毫秒)：" + loadMemoryTime;


        KieContainer kContainer = ks.newKieContainer(kieModule1.getReleaseId());
        long createContainerTime = System.currentTimeMillis() - endTime;
        String result2 = "创建容器时长耗时(毫秒)：" + createContainerTime;

        long endTime1 = System.currentTimeMillis();
        KieBase kieBase = kContainer.getKieBase("KBase");
        long createKBaseTime = System.currentTimeMillis() - endTime1;
        String result3 = "创建kbase时长耗时(毫秒)：" + createKBaseTime;


        // 打印虚拟机级内存情况查询
        printMemory();

        long start1Time = System.currentTimeMillis();
        for (int i = 0; i < executionCount; i++) {
            KieSession kSession1 = kieBase.newKieSession();
            Message message = new Message();
            message.setStatus("0");
            kSession1.insert(message);

            int result = kSession1.fireAllRules();
            System.out.println("功触发规则" + result);
            kSession1.dispose();
        }

        long executionTime = System.currentTimeMillis() - start1Time;

        ks = null;

        // 打印虚拟机级内存情况查询
        printMemory();

        return new HashMap<String, Integer>() {
            {
                put("loadMemoryTime", (int) loadMemoryTime);
                put("createContainerTime", (int)createContainerTime);
                put("createKBaseTime", (int) createKBaseTime);
                put("executionTime", (int) executionTime);
            }
        };
    }

    private static void printMemory() {
        // 虚拟机级内存情况查询
        long vmFree = 0;
        long vmUse = 0;
        long vmTotal = 0;
        long vmMax = 0;
        int byteToMb = 1024 * 1024;
        Runtime rt = Runtime.getRuntime();
        vmTotal = rt.totalMemory() / byteToMb;
        vmFree = rt.freeMemory() / byteToMb;
        vmMax = rt.maxMemory() / byteToMb;
        vmUse = vmTotal - vmFree;
        System.out.println("JVM内存已用的空间为：" + vmUse + " MB");
        System.out.println("JVM内存的空闲空间为：" + vmFree + " MB");
        System.out.println("JVM总内存空间为：" + vmTotal + " MB");
        System.out.println("JVM最大内存空间为：" + vmMax + " MB");
    }

}
