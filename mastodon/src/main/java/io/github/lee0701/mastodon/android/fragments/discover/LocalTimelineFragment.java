package io.github.lee0701.mastodon.android.fragments.discover;

import android.os.Bundle;
import android.view.View;

import io.github.lee0701.mastodon.android.api.requests.timelines.GetPublicTimeline;
import io.github.lee0701.mastodon.android.fragments.StatusListFragment;
import io.github.lee0701.mastodon.android.model.Filter;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.ui.utils.DiscoverInfoBannerHelper;
import io.github.lee0701.mastodon.android.utils.StatusFilterPredicate;

import java.util.List;
import java.util.stream.Collectors;

import me.grishka.appkit.api.SimpleCallback;

public class LocalTimelineFragment extends StatusListFragment{
	private DiscoverInfoBannerHelper bannerHelper=new DiscoverInfoBannerHelper(DiscoverInfoBannerHelper.BannerType.LOCAL_TIMELINE);
	private String maxID;

	@Override
	protected void doLoadData(int offset, int count){
		currentRequest=new GetPublicTimeline(true, false, refreshing ? null : maxID, count)
				.setCallback(new SimpleCallback<>(this){
					@Override
					public void onSuccess(List<Status> result){
						if(!result.isEmpty())
							maxID=result.get(result.size()-1).id;
						onDataLoaded(result.stream().filter(new StatusFilterPredicate(accountID, Filter.FilterContext.PUBLIC)).collect(Collectors.toList()), !result.isEmpty());
					}
				})
				.exec(accountID);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		bannerHelper.maybeAddBanner(contentWrap);
	}
}
