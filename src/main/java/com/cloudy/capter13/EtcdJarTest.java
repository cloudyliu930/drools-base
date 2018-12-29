package com.cloudy.capter13;

import com.cloudy.BaseTest;
import com.cloudy.capter10.DroolsUtils;
import com.cloudy.capter10.Message;
import com.cloudy.capter10.ResourceWrapper;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kproject.ReleaseIdImpl;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author cloudy
 * @createTime 2018/11/30
 * @description 生成jar包，读取jar包
 */
public class EtcdJarTest extends BaseTest {

    @Test
    public void standardTestBatchJar() throws Exception {
        Integer loadMemoryTime = 0;
        Integer createContainerTime = 0;
        Integer createKBaseTime = 0;
        Integer executionTime = 0;
        Integer jarCount = 30;     // jar包数量
        Integer executionCount = 3000;  // 执行次数

        String path = "test50";    // jar包大小  5kb 10个规则、 10kb 80个规则、  50kb 580个规则、  100kb  1180个规则

        EtcdUtil.getEtclClient();


        System.out.println(System.currentTimeMillis());
        int count = 1;
        for (int i = 0; i < count; i++) {
            String jarName = i + "-100cloudy"  + i+ "1.0.0.jar";
            byte[] bytes = EtcdUtil.getEtcdByteValueByKey(jarName);
            Map<String, Integer> result = standardTestJar(jarCount, executionCount, bytes);
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

    private static Map<String, Integer> standardTestJar(int jarCount, int executionCount, byte[] bytes) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        System.setProperty("kie.repository.project.cache.size", "20000");
        System.setProperty("kie.repository.project.versions.cache.size", "100");

        KieServices ks = KieServices.Factory.get();

        KieRepository kr = ks.getRepository();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < jarCount; i++) {
            kr.addKieModule(ks.getResources().newByteArrayResource(bytes));
        }

        System.out.println("初始化加载：" + System.currentTimeMillis());
        ReleaseId releaseId = ks.newReleaseId("com", "cloudy0", "1.0.0");
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
