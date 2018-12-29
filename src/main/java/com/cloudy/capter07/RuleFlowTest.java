package com.cloudy.capter07;

import com.cloudy.BaseTest;
import org.activiti.engine.*;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.utils.KieHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cloudy
 * @createTime 2018/11/29
 * @description 规则流
 */
public class RuleFlowTest extends BaseTest {

    @Test
    public void ruleFlowTest() {
//        Resource bpmn = ResourceFactory.newClassPathResource("bpmn/capter07/rule_flow.bpmn", RuleFlowTest.class);
//        Resource drl = ResourceFactory.newClassPathResource("rules/capter07/rule_flow.drl",RuleFlowTest.class);
//        KieHelper helper = new KieHelper();
//        helper.addResource(bpmn, ResourceType.BPMN2);
//        helper.addResource(drl,ResourceType.DRL);
//        KieSession ksession = helper.build().newKieSession();
//        Map<String, Object> map=new HashMap<String, Object>();
//        map.put("name", "张三");
//        ksession.startProcess("com.sample.process",map);
//        int i = ksession.fireAllRules();
//        System.out.println( " " + i + "次");
//        ksession.dispose();

        // 引擎配置
//        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // 获取流程引擎对象
//        ProcessEngine processEngine = pec.buildProcessEngine();

        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();

        // 得到流程存储服务组件
        RepositoryService repositoryService = engine.getRepositoryService();

        // 得到运行时服务组件
        RuntimeService runtimeService = engine.getRuntimeService();

        // 得到任务服务组件
        TaskService taskService = engine.getTaskService();

        //部署流程文件

        repositoryService.createDeployment()
                .addClasspathResource("rules/capter07/rule_flow.drl")
                .addClasspathResource("bpmn/capter07/rule_flow.bpmn").deploy();

        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("leave", new Leave("白展堂", 12));

        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process1");

        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        taskService.complete(task.getId(), vars);
    }

}


