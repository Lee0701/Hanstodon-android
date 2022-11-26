package io.github.lee0701.mastodon.android.fragments.account_list;

import android.os.Bundle;

import io.github.lee0701.mastodon.android.model.Account;
import org.parceler.Parcels;

public abstract class AccountRelatedAccountListFragment extends PaginatedAccountListFragment{
	protected Account account;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		account=Parcels.unwrap(getArguments().getParcelable("targetAccount"));
		setTitle("@"+account.acct);
	}
}
