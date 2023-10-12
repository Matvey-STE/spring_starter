package com.matveyvs.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class EventListener {
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'CREATE'")
    public void createEntity(EntityEventAfter entityEvent){
        log.info("The object has been CREATED: " + entityEvent);
    }
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'READ'")
    public void readEntity(EntityEventAfter entityEvent){
        log.info("The object has been READ: " + entityEvent);
    }
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'UPDATE'")
    public void updateEntity(EntityEventAfter entityEvent){
        log.info("The object has been UPDATED: " + entityEvent);
    }

    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'DELETE'")
    public void deleteEntity(EntityEventAfter entityEvent){
        log.info("The object has been DELETED: " + entityEvent);
    }
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'CREATE'")
    public void createEntity(EntityEventBefore entityEvent){
        log.info("Attempt to CREATED: " + entityEvent);
    }
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'READ'")
    public void readEntity(EntityEventBefore entityEvent){
        log.info("Attempt to READ: " + entityEvent);
    }
    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'UPDATE'")
    public void updateEntity(EntityEventBefore entityEvent){
        log.info("Attempt to UPDATED: " + entityEvent);
    }

    @org.springframework.context.event.EventListener
            (condition = "#p0.accessType.name == 'DELETE'")
    public void deleteEntity(EntityEventBefore entityEvent){
        log.info("Attempt to DELETED: " + entityEvent);
    }
}
