package io.github.lee0701.mastodon.android.events;

import io.github.lee0701.mastodon.android.model.Status;

public class StatusCreatedEvent{
	public Status status;

	public StatusCreatedEvent(Status status){
		this.status=status;
	}
}
