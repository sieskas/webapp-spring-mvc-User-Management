package com.example.usermanagementapp.web.config;

import com.example.usermanagementapp.model.ExpirationEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ExpirationEnumConverter implements Converter<String, ExpirationEnum> {

    @Override
    public ExpirationEnum convert(String source) {
        return ExpirationEnum.fromId(source);
    }
}