package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.ObjectValidationException;
import io.github.lee0701.mastodon.android.api.RequiredField;
import org.parceler.Parcel;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Parcel
public class Poll extends BaseModel{
	@RequiredField
	public String id;
	public Instant expiresAt;
	public boolean expired;
	public boolean multiple;
	public int votersCount;
	public boolean voted;
	@RequiredField
	public List<Integer> ownVotes;
	@RequiredField
	public List<Option> options;
	@RequiredField
	public List<Emoji> emojis;

	public transient ArrayList<Option> selectedOptions;

	@Override
	public void postprocess() throws ObjectValidationException{
		super.postprocess();
		for(Emoji e:emojis)
			e.postprocess();
	}

	@Override
	public String toString(){
		return "Poll{"+
				"id='"+id+'\''+
				", expiresAt="+expiresAt+
				", expired="+expired+
				", multiple="+multiple+
				", votersCount="+votersCount+
				", voted="+voted+
				", ownVotes="+ownVotes+
				", options="+options+
				", emojis="+emojis+
				'}';
	}

	@Parcel
	public static class Option{
		public String title;
		public Integer votesCount;

		@Override
		public String toString(){
			return "Option{"+
					"title='"+title+'\''+
					", votesCount="+votesCount+
					'}';
		}
	}
}
