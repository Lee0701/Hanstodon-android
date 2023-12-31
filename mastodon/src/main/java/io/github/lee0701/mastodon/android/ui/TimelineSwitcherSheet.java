package io.github.lee0701.mastodon.android.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.grishka.appkit.utils.BindableViewHolder;
import me.grishka.appkit.utils.MergeRecyclerAdapter;
import me.grishka.appkit.utils.SingleViewRecyclerAdapter;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.BottomSheet;
import me.grishka.appkit.views.UsableRecyclerView;

public class TimelineSwitcherSheet extends BottomSheet{
	private final Activity activity;
	private UsableRecyclerView list;
	private List<TimelineType> timelineTypes;
	private TimelineSwitcherListener listener;

	public TimelineSwitcherSheet(@NonNull Activity activity, TimelineSwitcherListener listener){
		super(activity);
		this.activity=activity;
		this.listener = listener;

		timelineTypes = Arrays.stream(TimelineType.values()).collect(Collectors.toList());

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

	private class TimelineTypeViewHolder extends BindableViewHolder<TimelineType> implements UsableRecyclerView.Clickable{
		private final TextView name;
		private final ImageView icon;

		public TimelineTypeViewHolder(){
			super(activity, R.layout.item_timeline_switcher, list);
			name=findViewById(R.id.name);
			icon =findViewById(R.id.avatar);
		}

		@SuppressLint("SetTextI18n")
		@Override
		public void onBind(TimelineType item){
			name.setText(item.name);
			icon.setImageDrawable(activity.getResources().getDrawable(item.icon, activity.getTheme()));
		}

		@Override
		public void onClick(){
			TimelineType type;
			if(this.item == TimelineType.PUBLIC) type = TimelineType.PUBLIC;
			else if(this.item == TimelineType.LOCAL) type = TimelineType.LOCAL;
			else type = TimelineType.HOME;
			listener.onSwitch(type);
			dismiss();
		}
	}

	public enum TimelineType {
		HOME(R.string.home_timeline, R.drawable.ic_fluent_home_28_filled),
		LOCAL(R.string.local_timeline, R.drawable.ic_baseline_groups_24),
		PUBLIC(R.string.public_timeline, R.drawable.ic_baseline_public_24);
		@StringRes private final int name;
		@DrawableRes private final int icon;
		TimelineType(@StringRes int name, @DrawableRes int icon) {
			this.name = name;
			this.icon = icon;
		}

		public int getName() {
			return name;
		}

		public int getIcon() {
			return icon;
		}
	}

	public interface TimelineSwitcherListener {
		void onSwitch(TimelineType type);
	}

}
