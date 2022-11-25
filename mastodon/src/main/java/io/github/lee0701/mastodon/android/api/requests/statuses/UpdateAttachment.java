package io.github.lee0701.mastodon.android.api.requests.statuses;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Attachment;

public class UpdateAttachment extends MastodonAPIRequest<Attachment>{
	public UpdateAttachment(String id, String description){
		super(HttpMethod.PUT, "/media/"+id, Attachment.class);
		setRequestBody(new Body(description));
	}

	private static class Body{
		public String description;

		public Body(String description){
			this.description=description;
		}
	}
}
