package io.github.lee0701.mastodon.android.fragments;

import android.app.Fragment;
import android.app.NotificationManager;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;

import io.github.lee0701.mastodon.android.PushNotificationReceiver;
import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.api.session.AccountSession;
import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.fragments.discover.DiscoverFragment;
import io.github.lee0701.mastodon.android.model.Account;
import io.github.lee0701.mastodon.android.ui.AccountSwitcherSheet;
import io.github.lee0701.mastodon.android.ui.TimelineSwitcherSheet;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;
import io.github.lee0701.mastodon.android.ui.views.TabBar;
import org.parceler.Parcels;

import java.util.ArrayList;

import me.grishka.appkit.FragmentStackActivity;
import me.grishka.appkit.fragments.AppKitFragment;
import me.grishka.appkit.fragments.LoaderFragment;
import me.grishka.appkit.fragments.OnBackPressedListener;
import me.grishka.appkit.imageloader.ViewImageLoader;
import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;
import me.grishka.appkit.utils.V;
import me.grishka.appkit.views.FragmentRootLinearLayout;

public class HomeFragment extends AppKitFragment implements OnBackPressedListener{
	private FragmentRootLinearLayout content;
	private HomeTimelineFragment homeTimelineFragment;
	private PublicTimelineFragment publicTimelineFragment;
	private LocalTimelineFragment localTimelineFragment;
	private NotificationsFragment notificationsFragment;
	private DiscoverFragment searchFragment;
	private ProfileFragment profileFragment;
	private TabBar tabBar;
	private View tabBarWrap;
	private ImageView tabBarAvatar;
	@IdRes
	private int currentTab=R.id.tab_timeline;
	private TimelineSwitcherSheet.TimelineType currentTimeline = TimelineSwitcherSheet.TimelineType.HOME;

	private String accountID;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		accountID=getArguments().getString("account");
		setTitle(R.string.app_name);

		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N)
			setRetainInstance(true);

		if(savedInstanceState==null){
			Bundle args=new Bundle();
			args.putString("account", accountID);
			homeTimelineFragment=new HomeTimelineFragment();
			homeTimelineFragment.setArguments(args);
			args=new Bundle(args);
			publicTimelineFragment=new PublicTimelineFragment();
			publicTimelineFragment.setArguments(args);
			args=new Bundle(args);
			localTimelineFragment=new LocalTimelineFragment();
			localTimelineFragment.setArguments(args);
			args=new Bundle(args);
			args.putBoolean("noAutoLoad", true);
			searchFragment=new DiscoverFragment();
			searchFragment.setArguments(args);
			notificationsFragment=new NotificationsFragment();
			notificationsFragment.setArguments(args);
			args=new Bundle(args);
			args.putParcelable("profileAccount", Parcels.wrap(AccountSessionManager.getInstance().getAccount(accountID).self));
			args.putBoolean("noAutoLoad", true);
			profileFragment=new ProfileFragment();
			profileFragment.setArguments(args);

		}

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
		content=new FragmentRootLinearLayout(getActivity());
		content.setOrientation(LinearLayout.VERTICAL);

		FrameLayout fragmentContainer=new FrameLayout(getActivity());
		fragmentContainer.setId(R.id.fragment_wrap);
		content.addView(fragmentContainer, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));

		inflater.inflate(R.layout.tab_bar, content);
		tabBar=content.findViewById(R.id.tabbar);
		tabBar.setListeners(this::onTabSelected, this::onTabLongClick);
		tabBarWrap=content.findViewById(R.id.tabbar_wrap);

		tabBarAvatar=tabBar.findViewById(R.id.tab_profile_ava);
		tabBarAvatar.setOutlineProvider(new ViewOutlineProvider(){
			@Override
			public void getOutline(View view, Outline outline){
				outline.setOval(0, 0, view.getWidth(), view.getHeight());
			}
		});
		tabBarAvatar.setClipToOutline(true);
		Account self=AccountSessionManager.getInstance().getAccount(accountID).self;
		ViewImageLoader.load(tabBarAvatar, null, new UrlImageLoaderRequest(self.avatar, V.dp(28), V.dp(28)));

		if(savedInstanceState==null){
			getChildFragmentManager().beginTransaction()
					.add(R.id.fragment_wrap, homeTimelineFragment)
					.add(R.id.fragment_wrap, publicTimelineFragment).hide(publicTimelineFragment)
					.add(R.id.fragment_wrap, localTimelineFragment).hide(localTimelineFragment)
					.add(R.id.fragment_wrap, searchFragment).hide(searchFragment)
					.add(R.id.fragment_wrap, notificationsFragment).hide(notificationsFragment)
					.add(R.id.fragment_wrap, profileFragment).hide(profileFragment)
					.commit();

			String defaultTab=getArguments().getString("tab");
			if("notifications".equals(defaultTab)){
				tabBar.selectTab(R.id.tab_notifications);
				fragmentContainer.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
					@Override
					public boolean onPreDraw(){
						fragmentContainer.getViewTreeObserver().removeOnPreDrawListener(this);
						onTabSelected(R.id.tab_notifications);
						return true;
					}
				});
			}
		}else{
		}

		return content;
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState){
		super.onViewStateRestored(savedInstanceState);
		if(savedInstanceState==null || homeTimelineFragment!=null)
			return;
		homeTimelineFragment=(HomeTimelineFragment) getChildFragmentManager().getFragment(savedInstanceState, "homeTimelineFragment");
		publicTimelineFragment=(PublicTimelineFragment) getChildFragmentManager().getFragment(savedInstanceState, "publicTimelineFragment");
		localTimelineFragment=(LocalTimelineFragment) getChildFragmentManager().getFragment(savedInstanceState, "localTimelineFragment");
		searchFragment=(DiscoverFragment) getChildFragmentManager().getFragment(savedInstanceState, "searchFragment");
		notificationsFragment=(NotificationsFragment) getChildFragmentManager().getFragment(savedInstanceState, "notificationsFragment");
		profileFragment=(ProfileFragment) getChildFragmentManager().getFragment(savedInstanceState, "profileFragment");
		currentTab=savedInstanceState.getInt("selectedTab");
		Fragment current= fragmentFor(currentTab, currentTimeline);
		getChildFragmentManager().beginTransaction()
				.hide(homeTimelineFragment)
				.hide(publicTimelineFragment)
				.hide(localTimelineFragment)
				.hide(searchFragment)
				.hide(notificationsFragment)
				.hide(profileFragment)
				.show(current)
				.commit();
		maybeTriggerLoading(current);
	}

	@Override
	public void onHiddenChanged(boolean hidden){
		super.onHiddenChanged(hidden);
		fragmentFor(currentTab, currentTimeline).onHiddenChanged(hidden);
	}

	@Override
	public boolean wantsLightStatusBar(){
		return currentTab!=R.id.tab_profile && !UiUtils.isDarkTheme();
	}

	@Override
	public boolean wantsLightNavigationBar(){
		return !UiUtils.isDarkTheme();
	}

	@Override
	public void onApplyWindowInsets(WindowInsets insets){
		if(Build.VERSION.SDK_INT>=27){
			int inset=insets.getSystemWindowInsetBottom();
			tabBarWrap.setPadding(0, 0, 0, inset>0 ? Math.max(inset, V.dp(36)) : 0);
			super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), 0));
		}else{
			super.onApplyWindowInsets(insets.replaceSystemWindowInsets(insets.getSystemWindowInsetLeft(), 0, insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom()));
		}
		WindowInsets topOnlyInsets=insets.replaceSystemWindowInsets(0, insets.getSystemWindowInsetTop(), 0, 0);
		homeTimelineFragment.onApplyWindowInsets(topOnlyInsets);
		publicTimelineFragment.onApplyWindowInsets(topOnlyInsets);
		localTimelineFragment.onApplyWindowInsets(topOnlyInsets);
		searchFragment.onApplyWindowInsets(topOnlyInsets);
		notificationsFragment.onApplyWindowInsets(topOnlyInsets);
		profileFragment.onApplyWindowInsets(topOnlyInsets);
	}

	private Fragment fragmentFor(@IdRes int tab, TimelineSwitcherSheet.TimelineType type){
		if(tab==R.id.tab_timeline){
			if(type == TimelineSwitcherSheet.TimelineType.PUBLIC) return publicTimelineFragment;
			if(type == TimelineSwitcherSheet.TimelineType.LOCAL) return localTimelineFragment;
			else return homeTimelineFragment;
		}else if(tab==R.id.tab_search){
			return searchFragment;
		}else if(tab==R.id.tab_notifications){
			return notificationsFragment;
		}else if(tab==R.id.tab_profile){
			return profileFragment;
		}
		throw new IllegalArgumentException();
	}

	private void onTabSelected(@IdRes int tab){
		Fragment newFragment= fragmentFor(tab, currentTimeline);
		if(tab==currentTab){
			if(newFragment instanceof ScrollableToTop scrollable)
				scrollable.scrollToTop();
			return;
		}
		switchFragment(newFragment);
		currentTab=tab;
		if(currentTab != R.id.tab_timeline) {
			currentTimeline = null;
			ImageView homeIcon = (ImageView) tabBar.findViewById(R.id.tab_home_ico);
			homeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_fluent_home_28_selector, getActivity().getTheme()));
		}
		((FragmentStackActivity)getActivity()).invalidateSystemBarColors(this);
	}

	private void switchFragment(Fragment newFragment) {
		getChildFragmentManager().beginTransaction()
				.hide(homeTimelineFragment)
				.hide(publicTimelineFragment)
				.hide(localTimelineFragment)
				.hide(searchFragment)
				.hide(notificationsFragment)
				.hide(profileFragment)
				.show(newFragment)
				.commit();
		maybeTriggerLoading(newFragment);
	}

	private void onTimelineSelected(TimelineSwitcherSheet.TimelineType type){
		currentTimeline = type;
		Fragment fragment;
		if(type == TimelineSwitcherSheet.TimelineType.PUBLIC) {
			fragment = publicTimelineFragment;
		} else if(type == TimelineSwitcherSheet.TimelineType.LOCAL) {
			fragment = localTimelineFragment;
		} else {
			fragment = homeTimelineFragment;
		}
		tabBar.clearSelected();
		tabBar.selectTab(R.id.tab_timeline);
		switchFragment(fragment);
		currentTab=R.id.tab_timeline;
		ImageView homeIcon = (ImageView) tabBar.findViewById(R.id.tab_home_ico);
		if(type == TimelineSwitcherSheet.TimelineType.HOME) {
			homeIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_fluent_home_28_filled, getActivity().getTheme()));
		} else {
			homeIcon.setImageDrawable(getResources().getDrawable(type.getIcon(), getActivity().getTheme()));
		}
		((FragmentStackActivity)getActivity()).invalidateSystemBarColors(this);
	}

	private void maybeTriggerLoading(Fragment newFragment){
		if(newFragment instanceof LoaderFragment lf){
			if(!lf.loaded && !lf.dataLoading)
				lf.loadData();
		}else if(newFragment instanceof DiscoverFragment){
			((DiscoverFragment) newFragment).loadData();
		}else if(newFragment instanceof NotificationsFragment){
			((NotificationsFragment) newFragment).loadData();
			// TODO make an interface?
			NotificationManager nm=getActivity().getSystemService(NotificationManager.class);
			nm.cancel(accountID, PushNotificationReceiver.NOTIFICATION_ID);
		}
	}

	private boolean onTabLongClick(@IdRes int tab){
		if(tab == R.id.tab_timeline) {
			new TimelineSwitcherSheet(getActivity(), this::onTimelineSelected).show();
		} if(tab==R.id.tab_profile){
			ArrayList<String> options=new ArrayList<>();
			for(AccountSession session:AccountSessionManager.getInstance().getLoggedInAccounts()){
				options.add(session.self.displayName+"\n("+session.self.username+"@"+session.domain+")");
			}
			new AccountSwitcherSheet(getActivity()).show();
			return true;
		}
		return false;
	}

	@Override
	public boolean onBackPressed(){
		if(currentTab==R.id.tab_profile)
			return profileFragment.onBackPressed();
		if(currentTab==R.id.tab_search)
			return searchFragment.onBackPressed();
		return false;
	}

	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putInt("selectedTab", currentTab);
		getChildFragmentManager().putFragment(outState, "homeTimelineFragment", homeTimelineFragment);
		getChildFragmentManager().putFragment(outState, "publicTimelineFragment", publicTimelineFragment);
		getChildFragmentManager().putFragment(outState, "localTimelineFragment", localTimelineFragment);
		getChildFragmentManager().putFragment(outState, "searchFragment", searchFragment);
		getChildFragmentManager().putFragment(outState, "notificationsFragment", notificationsFragment);
		getChildFragmentManager().putFragment(outState, "profileFragment", profileFragment);
	}

}
