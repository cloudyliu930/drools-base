package com.cloudy.capter09;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.internal.event.rule.RuleEventListener;

/**
 * @author cloudy
 * @createTime 2018/11/29
 * @description
 */
public class RuleRuntimeEventListenerImpl implements RuleRuntimeEventListener {

    @Override
    public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("objectInserted");
        System.out.println(event.getObject().toString());
    }

    @Override
    public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("objectUpdated");
        System.out.println(event.getObject().toString());
        System.out.println(event.getOldObject().toString());
    }

    @Override
    public void objectDeleted(ObjectDeletedEvent event) {
        System.out.println("objectDeleted");
        System.out.println(event.getOldObject().toString());
    }
}
