package io.github.robertograham.fortniteapirestclient.service.account;


import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountsRequest;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;

import java.io.IOException;
import java.util.List;

public interface AccountService {

    Account getAccount(GetAccountRequest getAccountRequest) throws IOException;

    List<Account> getAccounts(GetAccountsRequest getAccountsRequest) throws IOException;
}
