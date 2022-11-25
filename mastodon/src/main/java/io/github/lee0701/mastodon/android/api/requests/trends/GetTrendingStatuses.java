package io.github.lee0701.mastodon.android.api.requests.trends;

import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;

import java.util.List;

public class GetTrendingStatuses extends MastodonAPIRequest<List<Status>>{
	public GetTrendingStatuses(int limit){
		super(HttpMethod.GET, "/trends/statuses", new TypeToken<>(){});
		if(limit>0)
			addQueryParameter("limit", ""+limit);
	}
}
