package com.al.frontendframeworks.frontendframeworks_backend.controller;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Controller;

@Controller
public class AbstractController {

    public <S, T> MapperFacade getMapper(Class<S> source, Class<T> target) {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFactory.classMap(source, target);
        return mapperFactory.getMapperFacade();
    }

}
