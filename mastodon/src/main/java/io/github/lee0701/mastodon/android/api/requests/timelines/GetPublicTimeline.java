package io.github.lee0701.mastodon.android.api.requests.timelines;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;

import java.util.List;

public class GetPublicTimeline extends MastodonAPIRequest<List<Status>>{
	public GetPublicTimeline(boolean local, boolean remote, String maxID, int limit){
		super(HttpMethod.GET, "/timelines/public", new TypeToken<>(){});
		if(local)
			addQueryParameter("local", "true");
		if(remote)
			addQueryParameter("remote", "true");
		if(!TextUtils.isEmpty(maxID))
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
