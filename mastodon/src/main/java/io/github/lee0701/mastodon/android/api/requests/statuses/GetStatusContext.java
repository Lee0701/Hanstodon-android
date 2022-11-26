package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.StatusContext;

public class GetStatusContext extends MastodonAPIRequest<StatusContext>{
	public GetStatusContext(String id){
		super(HttpMethod.GET, "/statuses/"+id+"/context", StatusContext.class);
	}
}
