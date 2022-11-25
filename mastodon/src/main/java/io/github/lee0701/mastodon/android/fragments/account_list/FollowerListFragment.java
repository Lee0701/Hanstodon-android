package io.github.lee0701.mastodon.android.fragments.account_list;

import android.os.Bundle;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.api.requests.HeaderPaginationRequest;
import io.github.lee0701.mastodon.android.api.requests.accounts.GetAccountFollowers;
import io.github.lee0701.mastodon.android.model.Account;

public class FollowerListFragment extends AccountRelatedAccountListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setSubtitle(getResources().getQuantityString(R.plurals.x_followers, account.followersCount, account.followersCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetAccountFollowers(account.id, maxID, count);
	}
}
