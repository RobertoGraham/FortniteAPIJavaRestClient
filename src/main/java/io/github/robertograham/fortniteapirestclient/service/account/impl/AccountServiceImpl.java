package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import io.github.robertograham.fortniteapirestclient.util.ObjectMapperResponseHandler;
import org.apache.http.HttpHeaders;
import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class AccountServiceImpl implements AccountService {

    @Override
    public Account getAccount(GetAccountRequest getAccountRequest) throws IOException {
        return Request.Get(Endpoint.lookup(getAccountRequest.getAccountName()))
                .addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(ObjectMapperResponseHandler.thatProduces(Account.class));
    }
}
