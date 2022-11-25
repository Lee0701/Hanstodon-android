package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;

public class SetStatusReblogged extends MastodonAPIRequest<Status>{
	public SetStatusReblogged(String id, boolean reblogged){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(reblogged ? "reblog" : "unreblog"), Status.class);
		setRequestBody(new Object());
	}
}
