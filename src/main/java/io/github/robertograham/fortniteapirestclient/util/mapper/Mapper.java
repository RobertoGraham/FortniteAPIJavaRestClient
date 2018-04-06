package io.github.robertograham.fortniteapirestclient.util.mapper;

public interface Mapper<F, T> {

    T map(F f);
}
