package org.joinmastodon.android.api.requests.timelines;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;

import org.joinmastodon.android.api.MastodonAPIRequest;
import org.joinmastodon.android.model.Status;

import java.util.List;

public class GetPublicTimeline extends MastodonAPIRequest<List<Status>>{
	public GetPublicTimeline(boolean local, boolean remote, String maxID, String sinceID, int limit){
		super(HttpMethod.GET, "/timelines/public", new TypeToken<>(){});
		if(local)
			addQueryParameter("local", "true");
		if(remote)
			addQueryParameter("remote", "true");
		if(!TextUtils.isEmpty(maxID))
			addQueryParameter("max_id", maxID);
		if(sinceID!=null)
			addQueryParameter("since_id", sinceID);
		if(limit>0)
			addQueryParameter("limit", limit+"");
	}
}
