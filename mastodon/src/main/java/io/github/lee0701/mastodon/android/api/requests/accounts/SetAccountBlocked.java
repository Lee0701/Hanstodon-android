package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Relationship;

public class SetAccountBlocked extends MastodonAPIRequest<Relationship>{
	public SetAccountBlocked(String id, boolean blocked){
		super(HttpMethod.POST, "/accounts/"+id+"/"+(blocked ? "block" : "unblock"), Relationship.class);
		setRequestBody(new Object());
	}
}
