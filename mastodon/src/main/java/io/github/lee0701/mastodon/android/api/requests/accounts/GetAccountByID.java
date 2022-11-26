package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Account;

public class GetAccountByID extends MastodonAPIRequest<Account>{
	public GetAccountByID(String id){
		super(HttpMethod.GET, "/accounts/"+id, Account.class);
	}
}
