package io.github.lee0701.mastodon.android.api.requests.notifications;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Notification;

public class GetNotificationByID extends MastodonAPIRequest<Notification>{
	public GetNotificationByID(String id){
		super(HttpMethod.GET, "/notifications/"+id, Notification.class);
	}
}
