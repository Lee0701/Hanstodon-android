package io.github.lee0701.mastodon.android.events;

import io.github.lee0701.mastodon.android.model.Status;

public class StatusCountersUpdatedEvent{
	public String id;
	public int favorites, reblogs, replies;
	public boolean favorited, reblogged;

	public StatusCountersUpdatedEvent(Status s){
		id=s.id;
		favorites=s.favouritesCount;
		reblogs=s.reblogsCount;
		replies=s.repliesCount;
		favorited=s.favourited;
		reblogged=s.reblogged;
	}
}
