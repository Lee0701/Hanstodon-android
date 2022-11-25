package io.github.lee0701.mastodon.android.model;

public class PaginatedResponse<T>{
	public T items;
	public String maxID;

	public PaginatedResponse(T items, String maxID){
		this.items=items;
		this.maxID=maxID;
	}
}
