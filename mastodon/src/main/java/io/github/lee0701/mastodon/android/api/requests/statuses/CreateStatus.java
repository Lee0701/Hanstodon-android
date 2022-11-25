package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.model.StatusPrivacy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CreateStatus extends MastodonAPIRequest<Status>{
	public CreateStatus(CreateStatus.Request req, String uuid){
		super(HttpMethod.POST, "/statuses", Status.class);
		setRequestBody(req);
		addHeader("Idempotency-Key", uuid);
	}

	public static class Request{
		public String status;
		public List<String> mediaIds;
		public Poll poll;
		public String inReplyToId;
		public boolean sensitive;
		public String spoilerText;
		public StatusPrivacy visibility;
		public Instant scheduledAt;
		public String language;

		public static class Poll{
			public ArrayList<String> options=new ArrayList<>();
			public int expiresIn;
			public boolean multiple;
			public boolean hideTotals;
		}
	}
}
