package io.github.lee0701.mastodon.android.api.requests.instance;

import com.google.gson.reflect.TypeToken;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.model.Emoji;

import java.util.List;

public class GetCustomEmojis extends MastodonAPIRequest<List<Emoji>>{
	public GetCustomEmojis(){
		super(HttpMethod.GET, "/custom_emojis", new TypeToken<>(){});
	}
}
