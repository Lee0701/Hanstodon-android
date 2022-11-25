package io.github.lee0701.mastodon.android.api.requests.notifications;

import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.ApiUtils;
import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Notification;

import java.util.EnumSet;
import java.util.List;

public class GetNotifications extends MastodonAPIRequest<List<Notification>>{
	public GetNotifications(String maxID, int limit, EnumSet<Notification.Type> includeTypes){
		super(HttpMethod.GET, "/notifications", new TypeToken<>(){});
		if(maxID!=null)
			addQueryParameter("max_id", maxID);
		if(limit>0)
			addQueryParameter("limit", ""+limit);
		if(includeTypes!=null){
			for(String type:ApiUtils.enumSetToStrings(includeTypes, Notification.Type.class)){
				addQueryParameter("types[]", type);
			}
			for(String type:ApiUtils.enumSetToStrings(EnumSet.complementOf(includeTypes), Notification.Type.class)){
				addQueryParameter("exclude_types[]", type);
			}
		}
		removeUnsupportedItems=true;
	}
}
