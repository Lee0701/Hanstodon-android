package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.RequiredField;
import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Hashtag extends BaseModel{
	@RequiredField
	public String name;
	@RequiredField
	public String url;
	public List<History> history;

	@Override
	public String toString(){
		return "Hashtag{"+
				"name='"+name+'\''+
				", url='"+url+'\''+
				", history="+history+
				'}';
	}
}
