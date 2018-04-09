package io.github.robertograham.fortniteapirestclient.util.mapper;

public interface Mapper<FROM, TO> {

    TO mapFrom(FROM from);
}
