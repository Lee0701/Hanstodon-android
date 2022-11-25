package io.github.lee0701.mastodon.android.api.requests.notifications;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.PushSubscription;

public class RegisterForPushNotifications extends MastodonAPIRequest<PushSubscription>{
	public RegisterForPushNotifications(String deviceToken, String encryptionKey, String authKey, PushSubscription.Alerts alerts, PushSubscription.Policy policy, String accountID){
		super(HttpMethod.POST, "/push/subscription", PushSubscription.class);
		Request r=new Request();
		r.subscription.endpoint="https://app.joinmastodon.org/relay-to/fcm/"+deviceToken+"/"+accountID;
		r.data.alerts=alerts;
		r.data.policy=policy;
		r.subscription.keys.p256dh=encryptionKey;
		r.subscription.keys.auth=authKey;
		setRequestBody(r);
	}

	private static class Request{
		public Subscription subscription=new Subscription();
		public Data data=new Data();

		private static class Keys{
			public String p256dh;
			public String auth;
		}

		private static class Subscription{
			public String endpoint;
			public Keys keys=new Keys();
		}

		private static class Data{
			public PushSubscription.Alerts alerts;
			public PushSubscription.Policy policy;
		}
	}
}
