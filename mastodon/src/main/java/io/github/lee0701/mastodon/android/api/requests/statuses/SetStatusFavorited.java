package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;

public class SetStatusFavorited extends MastodonAPIRequest<Status>{
	public SetStatusFavorited(String id, boolean favorited){
		super(HttpMethod.POST, "/statuses/"+id+"/"+(favorited ? "favourite" : "unfavourite"), Status.class);
		setRequestBody(new Object());
	}
}
