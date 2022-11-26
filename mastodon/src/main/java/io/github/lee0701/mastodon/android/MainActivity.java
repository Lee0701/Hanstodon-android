package io.github.lee0701.mastodon.android;

import android.app.Application;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import io.github.lee0701.mastodon.android.api.ObjectValidationException;
import io.github.lee0701.mastodon.android.api.session.AccountSession;
import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.fragments.ComposeFragment;
import io.github.lee0701.mastodon.android.fragments.HomeFragment;
import io.github.lee0701.mastodon.android.fragments.ProfileFragment;
import io.github.lee0701.mastodon.android.fragments.SplashFragment;
import io.github.lee0701.mastodon.android.fragments.ThreadFragment;
import io.github.lee0701.mastodon.android.fragments.onboarding.AccountActivationFragment;
import io.github.lee0701.mastodon.android.model.Notification;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;
import org.parceler.Parcels;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.Nullable;
import me.grishka.appkit.FragmentStackActivity;

public class MainActivity extends FragmentStackActivity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState){
		UiUtils.setUserPreferredTheme(this);
		super.onCreate(savedInstanceState);

		if(savedInstanceState==null){
			if(AccountSessionManager.getInstance().getLoggedInAccounts().isEmpty()){
				showFragmentClearingBackStack(new SplashFragment());
			}else{
				AccountSessionManager.getInstance().maybeUpdateLocalInfo();
				AccountSession session;
				Bundle args=new Bundle();
				Intent intent=getIntent();
				if(intent.getBooleanExtra("fromNotification", false)){
					String accountID=intent.getStringExtra("accountID");
					try{
						session=AccountSessionManager.getInstance().getAccount(accountID);
						if(!intent.hasExtra("notification"))
							args.putString("tab", "notifications");
					}catch(IllegalStateException x){
						session=AccountSessionManager.getInstance().getLastActiveAccount();
					}
				}else{
					session=AccountSessionManager.getInstance().getLastActiveAccount();
				}
				args.putString("account", session.getID());
				Fragment fragment=session.activated ? new HomeFragment() : new AccountActivationFragment();
				fragment.setArguments(args);
				showFragmentClearingBackStack(fragment);
				if(intent.getBooleanExtra("fromNotification", false) && intent.hasExtra("notification")){
					Notification notification=Parcels.unwrap(intent.getParcelableExtra("notification"));
					showFragmentForNotification(notification, session.getID());
				}else if(intent.getBooleanExtra("compose", false)){
					showCompose();
				}
			}
		}

		if(BuildConfig.BUILD_TYPE.startsWith("appcenter")){
			// Call the appcenter SDK wrapper through reflection because it is only present in beta builds
			try{
				Class.forName("io.github.lee0701.mastodon.android.AppCenterWrapper").getMethod("init", Application.class).invoke(null, getApplication());
			}catch(ClassNotFoundException|NoSuchMethodException|IllegalAccessException|InvocationTargetException ignore){}
		}
	}

	@Override
	protected void onNewIntent(Intent intent){
		super.onNewIntent(intent);
		if(intent.getBooleanExtra("fromNotification", false)){
			String accountID=intent.getStringExtra("accountID");
			AccountSession accountSession;
			try{
				accountSession=AccountSessionManager.getInstance().getAccount(accountID);
			}catch(IllegalStateException x){
				return;
			}
			if(intent.hasExtra("notification")){
				Notification notification=Parcels.unwrap(intent.getParcelableExtra("notification"));
				showFragmentForNotification(notification, accountID);
			}else{
				AccountSessionManager.getInstance().setLastActiveAccountID(accountID);
				Bundle args=new Bundle();
				args.putString("account", accountID);
				args.putString("tab", "notifications");
				Fragment fragment=new HomeFragment();
				fragment.setArguments(args);
				showFragmentClearingBackStack(fragment);
			}
		}else if(intent.getBooleanExtra("compose", false)){
			showCompose();
		}
	}

	private void showFragmentForNotification(Notification notification, String accountID){
		Fragment fragment;
		Bundle args=new Bundle();
		args.putString("account", accountID);
		args.putBoolean("_can_go_back", true);
		try{
			notification.postprocess();
		}catch(ObjectValidationException x){
			Log.w("MainActivity", x);
			return;
		}
		if(notification.status!=null){
			fragment=new ThreadFragment();
			args.putParcelable("status", Parcels.wrap(notification.status));
		}else{
			fragment=new ProfileFragment();
			args.putParcelable("profileAccount", Parcels.wrap(notification.account));
		}
		fragment.setArguments(args);
		showFragment(fragment);
	}

	private void showCompose(){
		AccountSession session=AccountSessionManager.getInstance().getLastActiveAccount();
		if(session==null || !session.activated)
			return;
		ComposeFragment compose=new ComposeFragment();
		Bundle composeArgs=new Bundle();
		composeArgs.putString("account", session.getID());
		compose.setArguments(composeArgs);
		showFragment(compose);
	}
}
