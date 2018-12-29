package com.cloudy.capter05;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.conf.EqualityBehaviorOption;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.conf.ClockTypeOption;

/**
 * @author cloudy
 * @createTime 2018/11/27
 * @description KieFileSystem
 */
public class KieFileSystemTest {

    @Test
    public void testCreateModule() {
        KieServices kieServices = KieServices.Factory.get();
        KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
        KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("KBase1")
                .setDefault(Boolean.TRUE)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.STREAM);

        KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel("kSession1")
                .setDefault(Boolean.TRUE)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));

        KieFileSystem fileSystem = kieServices.newKieFileSystem();
        fileSystem.writeKModuleXML(kieModuleModel.toXML());


        KieBaseModel kieBaseModel1 = kieModuleModel.newKieBaseModel("KBase2")
                .setDefault(Boolean.TRUE)
                .setEqualsBehavior(EqualityBehaviorOption.EQUALITY)
                .setEventProcessingMode(EventProcessingOption.STREAM);

        KieSessionModel kieSessionModel1 = kieBaseModel1.newKieSessionModel("kSession2")
                .setDefault(Boolean.TRUE)
                .setType(KieSessionModel.KieSessionType.STATEFUL)
                .setClockType(ClockTypeOption.get("realtime"));

        KieFileSystem fileSystem1 = kieServices.newKieFileSystem();
        fileSystem.writeKModuleXML(kieModuleModel.toXML());
    }

}
