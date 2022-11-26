package io.github.lee0701.mastodon.android.api.requests.notifications;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.PushSubscription;

public class UpdatePushSettings extends MastodonAPIRequest<PushSubscription>{
	public UpdatePushSettings(PushSubscription.Alerts alerts, PushSubscription.Policy policy){
		super(HttpMethod.PUT, "/push/subscription", PushSubscription.class);
		setRequestBody(new Request(alerts, policy));
	}

	private static class Request{
		public Data data=new Data();

		public Request(PushSubscription.Alerts alerts, PushSubscription.Policy policy){
			this.data.alerts=alerts;
			this.data.policy=policy;
		}

		private static class Data{
			public PushSubscription.Alerts alerts;
			public PushSubscription.Policy policy;
		}
	}
}
