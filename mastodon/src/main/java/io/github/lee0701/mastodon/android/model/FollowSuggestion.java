package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.ObjectValidationException;
import io.github.lee0701.mastodon.android.api.RequiredField;

public class FollowSuggestion extends BaseModel{
	@RequiredField
	public Account account;
//	public String source;

	@Override
	public void postprocess() throws ObjectValidationException{
		super.postprocess();
		account.postprocess();
	}
}
