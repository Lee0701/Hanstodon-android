package io.github.lee0701.mastodon.android.fragments.account_list;

import android.os.Bundle;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.api.requests.HeaderPaginationRequest;
import io.github.lee0701.mastodon.android.api.requests.accounts.GetAccountFollowing;
import io.github.lee0701.mastodon.android.model.Account;

public class FollowingListFragment extends AccountRelatedAccountListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setSubtitle(getResources().getQuantityString(R.plurals.x_following, account.followingCount, account.followingCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetAccountFollowing(account.id, maxID, count);
	}
}
