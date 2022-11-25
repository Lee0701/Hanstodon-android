package io.github.lee0701.mastodon.android.api.requests.search;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.SearchResults;

public class GetSearchResults extends MastodonAPIRequest<SearchResults>{
	public GetSearchResults(String query, Type type, boolean resolve){
		super(HttpMethod.GET, "/search", SearchResults.class);
		addQueryParameter("q", query);
		if(type!=null)
			addQueryParameter("type", type.name().toLowerCase());
		if(resolve)
			addQueryParameter("resolve", "true");
	}

	@Override
	protected String getPathPrefix(){
		return "/api/v2";
	}

	public enum Type{
		ACCOUNTS,
		HASHTAGS,
		STATUSES
	}
}
