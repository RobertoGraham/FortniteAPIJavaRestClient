package io.github.robertograham.fortniteapirestclient.util.mapper.impl;

import io.github.robertograham.fortniteapirestclient.util.mapper.Mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class StringLocalDateTimeMapper implements Mapper<String, LocalDateTime> {

    @Override
    public LocalDateTime mapFrom(String timestamp) {
        return LocalDateTime.ofInstant(Instant.parse(timestamp), ZoneOffset.UTC);
    }
}
