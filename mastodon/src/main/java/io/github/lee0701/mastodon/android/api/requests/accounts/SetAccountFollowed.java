package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Relationship;

public class SetAccountFollowed extends MastodonAPIRequest<Relationship>{
	public SetAccountFollowed(String id, boolean followed, boolean showReblogs){
		super(HttpMethod.POST, "/accounts/"+id+"/"+(followed ? "follow" : "unfollow"), Relationship.class);
		if(followed)
			setRequestBody(new Request(showReblogs, null));
		else
			setRequestBody(new Object());
	}

	private static class Request{
		public Boolean reblogs, notify;

		public Request(Boolean reblogs, Boolean notify){
			this.reblogs=reblogs;
			this.notify=notify;
		}
	}
}
