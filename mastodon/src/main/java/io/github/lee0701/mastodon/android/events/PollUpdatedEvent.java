package io.github.lee0701.mastodon.android.events;

import io.github.lee0701.mastodon.android.model.Poll;

public class PollUpdatedEvent{
	public String accountID;
	public Poll poll;

	public PollUpdatedEvent(String accountID, Poll poll){
		this.accountID=accountID;
		this.poll=poll;
	}
}
