package com.cloudy.capter07;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author cloudy
 * @createTime 20/11/29
 * @description
 */
public class DroolsService implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++++++");
        System.out.println(execution.getVariable("reason"));
        System.out.println("++++++++++++++++++++++++++++++++++++++");
    }

}
