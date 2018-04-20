package io.github.robertograham.fortniteapirestclient.service.account;


import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AccountService {

    CompletableFuture<Account> getAccount(GetAccountRequest getAccountRequest);

    CompletableFuture<List<Account>> getAccounts(GetAccountsRequest getAccountsRequest);
}
