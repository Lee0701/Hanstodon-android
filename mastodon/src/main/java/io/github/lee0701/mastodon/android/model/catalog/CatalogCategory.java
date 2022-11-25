package io.github.lee0701.mastodon.android.model.catalog;

import io.github.lee0701.mastodon.android.api.AllFieldsAreRequired;
import io.github.lee0701.mastodon.android.model.BaseModel;

@AllFieldsAreRequired
public class CatalogCategory extends BaseModel{
	public String category;
	public int serversCount;

	@Override
	public String toString(){
		return "CatalogCategory{"+
				"category='"+category+'\''+
				", serversCount="+serversCount+
				'}';
	}
}
