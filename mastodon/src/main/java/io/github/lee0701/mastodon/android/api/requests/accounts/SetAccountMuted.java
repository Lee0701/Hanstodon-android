package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Relationship;

public class SetAccountMuted extends MastodonAPIRequest<Relationship>{
	public SetAccountMuted(String id, boolean muted){
		super(HttpMethod.POST, "/accounts/"+id+"/"+(muted ? "mute" : "unmute"), Relationship.class);
		setRequestBody(new Object());
	}
}
