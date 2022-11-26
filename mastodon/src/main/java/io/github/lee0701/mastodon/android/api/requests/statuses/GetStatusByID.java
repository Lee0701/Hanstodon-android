package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;

public class GetStatusByID extends MastodonAPIRequest<Status>{
	public GetStatusByID(String id){
		super(HttpMethod.GET, "/statuses/"+id, Status.class);
	}
}
