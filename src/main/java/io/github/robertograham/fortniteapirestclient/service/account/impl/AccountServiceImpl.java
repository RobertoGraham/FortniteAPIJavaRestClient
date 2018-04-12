package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ObjectMappingResponseHandlerProducer;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class AccountServiceImpl implements AccountService {

    private final ObjectMappingResponseHandlerProducer objectMappingResponseHandlerProducer;

    public AccountServiceImpl(ObjectMappingResponseHandlerProducer objectMappingResponseHandlerProducer) {
        this.objectMappingResponseHandlerProducer = objectMappingResponseHandlerProducer;
    }

    @Override
    public Account getAccount(GetAccountRequest getAccountRequest) throws IOException {
        return Request.Get(Endpoint.lookup(getAccountRequest.getAccountName()))
                .addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(objectMappingResponseHandlerProducer.produceFor(Account.class));
    }
}
