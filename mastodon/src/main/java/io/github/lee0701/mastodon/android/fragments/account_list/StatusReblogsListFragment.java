package io.github.lee0701.mastodon.android.fragments.account_list;

import android.os.Bundle;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.api.requests.HeaderPaginationRequest;
import io.github.lee0701.mastodon.android.api.requests.statuses.GetStatusReblogs;
import io.github.lee0701.mastodon.android.model.Account;

public class StatusReblogsListFragment extends StatusRelatedAccountListFragment{
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setTitle(getResources().getQuantityString(R.plurals.x_reblogs, status.reblogsCount, status.reblogsCount));
	}

	@Override
	public HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count){
		return new GetStatusReblogs(status.id, maxID, count);
	}
}
