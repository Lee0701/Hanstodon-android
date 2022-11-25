package io.github.lee0701.mastodon.android.api.requests.trends;

import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Card;

import java.util.List;

public class GetTrendingLinks extends MastodonAPIRequest<List<Card>>{
	public GetTrendingLinks(){
		super(HttpMethod.GET, "/trends/links", new TypeToken<>(){});
	}
}
