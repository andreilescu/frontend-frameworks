package com.al.frontendframeworks.frontendframeworks_backend.facade;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;

@Service
public class AbstractFacade {

    public <S, T> MapperFacade getMapper(Class<S> source, Class<T> target) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(source, target);
        return mapperFactory.getMapperFacade();
    }
}
