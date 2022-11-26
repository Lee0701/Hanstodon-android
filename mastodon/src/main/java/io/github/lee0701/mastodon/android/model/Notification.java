package io.github.lee0701.mastodon.android.model;

import com.google.gson.annotations.SerializedName;

import io.github.lee0701.mastodon.android.api.ObjectValidationException;
import io.github.lee0701.mastodon.android.api.RequiredField;
import org.parceler.Parcel;

import java.time.Instant;

@Parcel
public class Notification extends BaseModel implements DisplayItemsParent{
	@RequiredField
	public String id;
//	@RequiredField
	public Type type;
	@RequiredField
	public Instant createdAt;
	@RequiredField
	public Account account;

	public Status status;

	@Override
	public void postprocess() throws ObjectValidationException{
		super.postprocess();
		account.postprocess();
		if(status!=null)
			status.postprocess();
	}

	@Override
	public String getID(){
		return id;
	}

	public enum Type{
		@SerializedName("follow")
		FOLLOW,
		@SerializedName("follow_request")
		FOLLOW_REQUEST,
		@SerializedName("mention")
		MENTION,
		@SerializedName("reblog")
		REBLOG,
		@SerializedName("favourite")
		FAVORITE,
		@SerializedName("poll")
		POLL,
		@SerializedName("status")
		STATUS
	}
}
