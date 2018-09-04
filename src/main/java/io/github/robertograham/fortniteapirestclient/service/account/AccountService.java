package io.github.robertograham.fortniteapirestclient.service.account;


import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.Eula;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AccountService {

    CompletableFuture<Account> getAccount(GetAccountRequest getAccountRequest);

    CompletableFuture<List<Account>> getAccounts(GetAccountsRequest getAccountsRequest);

    CompletableFuture<Eula> getEula(GetEulaRequest getEulaRequest);

    CompletableFuture<Boolean> acceptEula(AcceptEulaRequest acceptEulaRequest);

    CompletableFuture<Boolean> grantAccess(GrantAccessRequest grantAccessRequest);
}
