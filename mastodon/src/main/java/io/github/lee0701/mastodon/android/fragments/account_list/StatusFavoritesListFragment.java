package io.github.lee0701.mastodon.android.fragments.account_list;

import android.os.Bundle;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.api.requests.HeaderPaginationRequest;
import io.github.lee0701.mastodon.android.api.requests.statuses.GetStatusFavorites;
import io.github.lee0701.mastodon.android.model.Account;

public class StatusFavoritesListFragment extends StatusRelatedAccountListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(getResources().getQuantityString(R.plurals.x_favorites, status.favouritesCount, status.favouritesCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetStatusFavorites(status.id, maxID, count);
	}
}
