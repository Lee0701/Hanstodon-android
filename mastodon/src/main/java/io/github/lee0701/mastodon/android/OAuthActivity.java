package io.github.lee0701.mastodon.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import io.github.lee0701.mastodon.android.api.requests.accounts.GetOwnAccount;
import io.github.lee0701.mastodon.android.api.requests.oauth.GetOauthToken;
import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.model.Account;
import io.github.lee0701.mastodon.android.model.Application;
import io.github.lee0701.mastodon.android.model.Instance;
import io.github.lee0701.mastodon.android.model.Token;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;

import androidx.annotation.Nullable;
import me.grishka.appkit.api.Callback;
import me.grishka.appkit.api.ErrorResponse;

public class OAuthActivity extends Activity{
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState){
		UiUtils.setUserPreferredTheme(this);
		super.onCreate(savedInstanceState);
		Uri uri=getIntent().getData();
		if(uri==null || isTaskRoot()){
			finish();
			return;
		}
		if(uri.getQueryParameter("error")!=null){
			String error=uri.getQueryParameter("error_description");
			if(TextUtils.isEmpty(error))
				error=uri.getQueryParameter("error");
			Toast.makeText(this, error, Toast.LENGTH_LONG).show();
			finish();
			restartMainActivity();
			return;
		}
		String code=uri.getQueryParameter("code");
		if(TextUtils.isEmpty(code)){
			finish();
			return;
		}
		Instance instance=AccountSessionManager.getInstance().getAuthenticatingInstance();
		Application app=AccountSessionManager.getInstance().getAuthenticatingApp();
		if(instance==null || app==null){
			finish();
			return;
		}
		ProgressDialog progress=new ProgressDialog(this);
		progress.setMessage(getString(R.string.finishing_auth));
		progress.setCancelable(false);
		progress.show();
		new GetOauthToken(app.clientId, app.clientSecret, code, GetOauthToken.GrantType.AUTHORIZATION_CODE)
				.setCallback(new Callback<>(){
					@Override
					public void onSuccess(Token token){
						new GetOwnAccount()
								.setCallback(new Callback<>(){
									@Override
									public void onSuccess(Account account){
										AccountSessionManager.getInstance().addAccount(instance, token, account, app, true);
										progress.dismiss();
										finish();
										// not calling restartMainActivity() here on purpose to have it recreated (notice different flags)
										Intent intent=new Intent(OAuthActivity.this, MainActivity.class);
										intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
										startActivity(intent);
									}

									@Override
									public void onError(ErrorResponse error){
										handleError(error);
										progress.dismiss();
									}
								})
								.exec(instance.uri, token);
					}

					@Override
					public void onError(ErrorResponse error){
						handleError(error);
						progress.dismiss();
					}
				})
				.execNoAuth(instance.uri);
	}

	private void handleError(ErrorResponse error){
		error.showToast(OAuthActivity.this);
		finish();
		restartMainActivity();
	}

	private void restartMainActivity(){
		Intent intent=new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
