package io.github.lee0701.mastodon.android.fragments;

import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.api.requests.timelines.GetPublicTimeline;
import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.model.CacheablePaginatedResponse;
import io.github.lee0701.mastodon.android.model.Status;

import java.util.List;

import me.grishka.appkit.api.Callback;

public class PublicTimelineFragment extends TimelineFragment {

	@Override
	public MastodonAPIRequest<List<Status>> getGetTimelineRequest(String maxID, String minID, int limit, String sinceID) {
		return new GetPublicTimeline(false, false, maxID, sinceID, limit);
	}

	@Override
	public void getTimeline(String maxID, int count, boolean forceReload, Callback<CacheablePaginatedResponse<List<Status>>> callback) {
		AccountSessionManager.getInstance()
						.getAccount(accountID).getCacheController()
						.getPublicTimeline(maxID, count, forceReload, callback);
	}

	@Override
	public void putTimeline(List<Status> posts, boolean clear) {
		AccountSessionManager.getInstance().getAccount(accountID).getCacheController().putPublicTimeline(posts, false);
	}

}
