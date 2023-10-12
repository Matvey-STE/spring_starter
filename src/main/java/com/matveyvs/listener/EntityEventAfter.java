package com.matveyvs.listener;

import com.matveyvs.entity.AccessType;
import org.springframework.context.ApplicationEvent;

public class EntityEventAfter extends ApplicationEvent {
    private final AccessType accessType;

    public EntityEventAfter(Object source, AccessType accessType) {
        super(source);
        this.accessType = accessType;
    }
    public AccessType getAccessType(){
        return accessType;
    }
}
