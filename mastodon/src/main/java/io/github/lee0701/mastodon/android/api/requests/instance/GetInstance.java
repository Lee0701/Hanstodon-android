package io.github.lee0701.mastodon.android.api.requests.instance;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Instance;

public class GetInstance extends MastodonAPIRequest<Instance>{
	public GetInstance(){
		super(HttpMethod.GET, "/instance", Instance.class);
	}
}
