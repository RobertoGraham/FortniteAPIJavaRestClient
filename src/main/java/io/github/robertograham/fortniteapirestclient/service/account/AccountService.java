package io.github.robertograham.fortniteapirestclient.service.account;


import io.github.robertograham.fortniteapirestclient.service.account.model.Account;
import io.github.robertograham.fortniteapirestclient.service.account.model.request.GetAccountRequest;

import java.io.IOException;

public interface AccountService {

    Account getAccount(GetAccountRequest getAccountRequest) throws IOException;
}
