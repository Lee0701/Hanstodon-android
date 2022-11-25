package io.github.lee0701.mastodon.android.fragments.discover;

import android.os.Bundle;
import android.view.View;

import io.github.lee0701.mastodon.android.api.requests.trends.GetTrendingStatuses;
import io.github.lee0701.mastodon.android.fragments.StatusListFragment;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.ui.utils.DiscoverInfoBannerHelper;

import java.util.List;

import me.grishka.appkit.api.SimpleCallback;

public class DiscoverPostsFragment extends StatusListFragment{
	private DiscoverInfoBannerHelper bannerHelper=new DiscoverInfoBannerHelper(DiscoverInfoBannerHelper.BannerType.TRENDING_POSTS);

	@Override
	protected void doLoadData(int offset, int count){
		currentRequest=new GetTrendingStatuses(count)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<Status> result){
						onDataLoaded(result, false);
					}
				}).exec(accountID);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		bannerHelper.maybeAddBanner(contentWrap);
	}
}
