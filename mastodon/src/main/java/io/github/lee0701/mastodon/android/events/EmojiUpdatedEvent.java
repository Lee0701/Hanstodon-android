package io.github.lee0701.mastodon.android.events;

public class EmojiUpdatedEvent{
	public String instanceDomain;

	public EmojiUpdatedEvent(String instanceDomain){
		this.instanceDomain=instanceDomain;
	}
}
