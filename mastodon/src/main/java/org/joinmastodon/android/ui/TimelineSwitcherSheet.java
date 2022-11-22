package org.joinmastodon.android.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.joinmastodon.android.R;
import org.joinmastodon.android.api.requests.oauth.RevokeOauthToken;
import org.joinmastodon.android.api.session.AccountSession;
import org.joinmastodon.android.api.session.AccountSessionManager;
import org.joinmastodon.android.fragments.HomeTimelineFragment;
import org.joinmastodon.android.fragments.PublicTimelineFragment;
import org.joinmastodon.android.ui.utils.UiUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.grishka.appkit.Nav;
import me.grishka.appkit.api.Callback;
import me.grishka.appkit.api.ErrorResponse;
import me.grishka.appkit.utils.BindableViewHolder;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.BottomSheet;
import me.grishka.appkit.views.UsableRecyclerView;

public class TimelineSwitcherSheet extends BottomSheet{
	private final Activity activity;
	private final String accountID;
	private UsableRecyclerView list;
	private List<WrappedTimelineType> timelineTypes;

	public TimelineSwitcherSheet(@NonNull Activity activity, String accountID){
		super(activity);
		this.activity=activity;
		this.accountID = accountID;

		timelineTypes = Arrays.stream(WrappedTimelineType.values()).collect(Collectors.toList());

		list=new UsableRecyclerView(activity);
		list.setClipToPadding(false);
		list.setLayoutManager(new LinearLayoutManager(activity));

		MergeRecyclerAdapter adapter=new MergeRecyclerAdapter();
		View handle=new View(activity);
		handle.setBackgroundResource(R.drawable.bg_bottom_sheet_handle);
		adapter.addAdapter(new SingleViewRecyclerAdapter(handle));
		adapter.addAdapter(new TimelinesAdapter());

		list.setAdapter(adapter);
		DividerItemDecoration divider=new DividerItemDecoration(activity, R.attr.colorPollVoted, .5f, 72, 16, DividerItemDecoration.NOT_FIRST);
		divider.setDrawBelowLastItem(true);
		list.addItemDecoration(divider);

		FrameLayout content=new FrameLayout(activity);
		content.setBackgroundResource(R.drawable.bg_bottom_sheet);
		content.addView(list);
		setContentView(content);
		setNavigationBarBackground(new ColorDrawable(UiUtils.getThemeColor(activity, R.attr.colorWindowBackground)), !UiUtils.isDarkTheme());
	}

	private void confirmLogOut(String accountID){
		new M3AlertDialogBuilder(activity)
				.setTitle(R.string.log_out)
				.setMessage(R.string.confirm_log_out)
				.setPositiveButton(R.string.log_out, (dialog, which) -> logOut(accountID))
				.setNegativeButton(R.string.cancel, null)
				.show();
	}

	private void logOut(String accountID){
		AccountSession session=AccountSessionManager.getInstance().getAccount(accountID);
		new RevokeOauthToken(session.app.clientId, session.app.clientSecret, session.token.accessToken)
				.setCallback(new Callback<>(){
					@Override
					public void onSuccess(Object result){
						onLoggedOut(accountID);
					}

					@Override
					public void onError(ErrorResponse error){
						onLoggedOut(accountID);
					}
				})
				.wrapProgress(activity, R.string.loading, false)
				.exec(accountID);
	}

	private void onLoggedOut(String accountID){
		AccountSessionManager.getInstance().removeAccount(accountID);
		dismiss();
	}

	@Override
	protected void onWindowInsetsUpdated(WindowInsets insets){
		if(Build.VERSION.SDK_INT>=29){
			int tappableBottom=insets.getTappableElementInsets().bottom;
			int insetBottom=insets.getSystemWindowInsetBottom();
			if(tappableBottom==0 && insetBottom>0){
				list.setPadding(0, 0, 0, V.dp(48)-insetBottom);
			}else{
				list.setPadding(0, 0, 0, V.dp(24));
			}
		}else{
			list.setPadding(0, 0, 0, V.dp(24));
		}
	}

	private class TimelinesAdapter extends RecyclerView.Adapter<TimelineTypeViewHolder> {

		@NonNull
		@Override
		public TimelineTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
			return new TimelineTypeViewHolder();
		}

		@Override
		public int getItemCount(){
			return timelineTypes.size();
		}

		@Override
		public void onBindViewHolder(TimelineTypeViewHolder holder, int position){
			holder.bind(timelineTypes.get(position));
		}
	}

	private class TimelineTypeViewHolder extends BindableViewHolder<WrappedTimelineType> implements UsableRecyclerView.Clickable{
		private final TextView name;
		private final ImageView avatar;

		public TimelineTypeViewHolder(){
			super(activity, R.layout.item_timeline_switcher, list);
			name=findViewById(R.id.name);
			avatar=findViewById(R.id.avatar);

//			avatar.setOutlineProvider(OutlineProviders.roundedRect(12));
//			avatar.setClipToOutline(true);

		}

		@SuppressLint("SetTextI18n")
		@Override
		public void onBind(WrappedTimelineType item){
			name.setText(item.name);
		}

		@Override
		public void onClick(){
			Class<? extends Fragment> type;
			if(this.item == WrappedTimelineType.PUBLIC) type = PublicTimelineFragment.class;
			else type = HomeTimelineFragment.class;
			Bundle extras = new Bundle();
			extras.putString("account", accountID);
			extras.putBoolean("_can_go_back", false);
			Nav.goClearingStack(activity, type, extras);
			dismiss();
		}
	}

	private enum WrappedTimelineType {
		HOME("Home"), PUBLIC("Public");
		private String name;
		WrappedTimelineType(String name) {
			this.name = name;
		}
	}
}
