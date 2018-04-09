package io.github.robertograham.fortniteapirestclient.service.account.impl;

import io.github.robertograham.fortniteapirestclient.service.account.AccountService;
import io.github.robertograham.fortniteapirestclient.service.account.mapper.AccountJsonAccountMapper;
import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.util.Endpoint;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.fluent.Request;
import org.apache.http.impl.client.BasicResponseHandler;

import java.io.IOException;

public class AccountServiceImpl implements AccountService {

    private ResponseHandler<String> responseHandler;
    private AccountJsonAccountMapper accountJsonAccountMapper;

    public AccountServiceImpl() {
        responseHandler = new BasicResponseHandler();
        accountJsonAccountMapper = new AccountJsonAccountMapper();
    }

    @Override
    public Account getAccount(GetAccountRequest getAccountRequest) throws IOException {
        return accountJsonAccountMapper.mapFrom(Request.Get(Endpoint.lookup(getAccountRequest.getAccountName()))
                .addHeader(HttpHeaders.AUTHORIZATION, getAccountRequest.getAuthHeaderValue())
                .execute()
                .handleResponse(responseHandler));
    }
}
