package io.github.lee0701.mastodon.android.api.requests.accounts;

import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Filter;

import java.util.List;

public class GetWordFilters extends MastodonAPIRequest<List<Filter>>{
	public GetWordFilters(){
		super(HttpMethod.GET, "/filters", new TypeToken<>(){});
	}
}
