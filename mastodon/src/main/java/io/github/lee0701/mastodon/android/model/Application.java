package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.RequiredField;
import org.parceler.Parcel;

@Parcel
public class Application extends BaseModel{
	@RequiredField
	public String name;
	public String website;
	public String vapidKey;
	public String clientId;
	public String clientSecret;

	@Override
	public String toString(){
		return "Application{"+
				"name='"+name+'\''+
				", website='"+website+'\''+
				", vapidKey='"+vapidKey+'\''+
				", clientId='"+clientId+'\''+
				", clientSecret='"+clientSecret+'\''+
				'}';
	}
}
