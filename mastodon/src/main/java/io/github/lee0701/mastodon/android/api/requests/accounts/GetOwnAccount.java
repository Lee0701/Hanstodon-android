package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Account;

public class GetOwnAccount extends MastodonAPIRequest<Account>{
	public GetOwnAccount(){
		super(HttpMethod.GET, "/accounts/verify_credentials", Account.class);
	}
}
