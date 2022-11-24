package org.joinmastodon.android.fragments;

import org.joinmastodon.android.api.MastodonAPIRequest;
import org.joinmastodon.android.api.requests.timelines.GetHomeTimeline;
import org.joinmastodon.android.api.session.AccountSessionManager;
import org.joinmastodon.android.model.CacheablePaginatedResponse;
import org.joinmastodon.android.model.Status;

import java.util.List;

import me.grishka.appkit.api.Callback;

public class HomeTimelineFragment extends TimelineFragment {
	@Override
	public MastodonAPIRequest<List<Status>> getGetTimelineRequest(String maxID, String minID, int limit, String sinceID) {
		return new GetHomeTimeline(maxID, minID, limit, sinceID);
	}

	@Override
	public void getTimeline(String maxID, int count, boolean forceReload, Callback<CacheablePaginatedResponse<List<Status>>> callback) {
		AccountSessionManager.getInstance()
				.getAccount(accountID).getCacheController()
				.getHomeTimeline(maxID, count, forceReload, callback);
	}

	@Override
	public void putTimeline(List<Status> posts, boolean clear) {
		AccountSessionManager.getInstance().getAccount(accountID).getCacheController().putHomeTimeline(posts, clear);
	}

}
