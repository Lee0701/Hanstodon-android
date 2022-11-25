package io.github.lee0701.mastodon.android.fragments.account_list;

import io.github.lee0701.mastodon.android.api.requests.HeaderPaginationRequest;
import io.github.lee0701.mastodon.android.model.Account;
import io.github.lee0701.mastodon.android.model.HeaderPaginationList;

import java.util.stream.Collectors;

import me.grishka.appkit.api.SimpleCallback;

public abstract class PaginatedAccountListFragment extends BaseAccountListFragment{
	private String nextMaxID;

	public abstract HeaderPaginationRequest<Account> onCreateRequest(String maxID, int count);

	@Override
	protected void doLoadData(int offset, int count){
		currentRequest=onCreateRequest(offset==0 ? null : nextMaxID, count)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(HeaderPaginationList<Account> result){
						if(result.nextPageUri!=null)
							nextMaxID=result.nextPageUri.getQueryParameter("max_id");
						else
							nextMaxID=null;
						onDataLoaded(result.stream().map(AccountItem::new).collect(Collectors.toList()), nextMaxID!=null);
					}
				})
				.exec(accountID);
	}

	@Override
	public void onResume(){
		super.onResume();
		if(!loaded && !dataLoading)
			loadData();
	}
}
