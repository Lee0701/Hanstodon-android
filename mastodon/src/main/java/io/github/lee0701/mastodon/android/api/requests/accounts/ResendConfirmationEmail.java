package io.github.lee0701.mastodon.android.api.requests.accounts;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;

public class ResendConfirmationEmail extends MastodonAPIRequest<Object>{
	public ResendConfirmationEmail(String email){
		super(HttpMethod.POST, "/emails/confirmations", Object.class);
//		setRequestBody(new Body(email));
		setRequestBody(new Object());
	}

	private static class Body{
		public String email;

		public Body(String email){
			this.email=email;
		}
	}
}
