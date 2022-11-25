package io.github.lee0701.mastodon.android.api.requests.oauth;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.model.Application;

public class CreateOAuthApp extends MastodonAPIRequest<Application>{
	public CreateOAuthApp(){
		super(HttpMethod.POST, "/apps", Application.class);
		setRequestBody(new Request());
	}

	private static class Request{
		public String clientName="Hanstodon for Android";
		public String redirectUris=AccountSessionManager.REDIRECT_URI;
		public String scopes=AccountSessionManager.SCOPE;
		public String website="https://app.joinmastodon.org/android";
	}
}
