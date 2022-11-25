package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.AllFieldsAreRequired;
import io.github.lee0701.mastodon.android.api.ObjectValidationException;

import java.util.List;

@AllFieldsAreRequired
public class StatusContext extends BaseModel{
	public List<Status> ancestors;
	public List<Status> descendants;

	@Override
	public void postprocess() throws ObjectValidationException{
		super.postprocess();
		for(Status s:ancestors)
			s.postprocess();
		for(Status s:descendants)
			s.postprocess();
	}
}
