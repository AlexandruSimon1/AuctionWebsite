package com.auctionwebsite.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

//Using an @Context parameter with a type that provides its own @ BeforeMapping methods to handle cycles in Notificator structures
public class NotificatorMappingContext {
    private final Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        return targetType.cast(knownInstances.get(source));
    }

    @BeforeMapping
    public void storedMappedInstance(Object source, @MappingTarget Object target) {
        knownInstances.put(source, target);
    }
}
