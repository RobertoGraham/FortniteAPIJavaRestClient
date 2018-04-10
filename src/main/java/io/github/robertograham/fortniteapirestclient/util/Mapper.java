package io.github.robertograham.fortniteapirestclient.util;

public interface Mapper<FROM, TO> {

    TO mapFrom(FROM from);
}
