package io.github.lee0701.mastodon.android.ui.displayitems;

import android.content.Context;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.fragments.BaseStatusListFragment;
import io.github.lee0701.mastodon.android.fragments.account_list.StatusFavoritesListFragment;
import io.github.lee0701.mastodon.android.fragments.account_list.StatusReblogsListFragment;
import io.github.lee0701.mastodon.android.fragments.account_list.StatusRelatedAccountListFragment;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;
import org.parceler.Parcels;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import androidx.annotation.PluralsRes;
import me.grishka.appkit.Nav;

public class ExtendedFooterStatusDisplayItem extends StatusDisplayItem{
	public final Status status;

	private static final DateTimeFormatter TIME_FORMATTER=DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT);

	public ExtendedFooterStatusDisplayItem(String parentID, BaseStatusListFragment parentFragment, Status status){
		super(parentID, parentFragment);
		this.status=status;
	}

	@Override
	public Type getType(){
		return Type.EXTENDED_FOOTER;
	}

	public static class Holder extends StatusDisplayItem.Holder<ExtendedFooterStatusDisplayItem>{
		private final TextView reblogs, favorites, time;
		private final View buttonsView;

		public Holder(Context context, ViewGroup parent){
			super(context, R.layout.display_item_extended_footer, parent);
			reblogs=findViewById(R.id.reblogs);
			favorites=findViewById(R.id.favorites);
			time=findViewById(R.id.timestamp);
			buttonsView=findViewById(R.id.button_bar);

			reblogs.setOnClickListener(v->startAccountListFragment(StatusReblogsListFragment.class));
			favorites.setOnClickListener(v->startAccountListFragment(StatusFavoritesListFragment.class));
		}

		@Override
		public void onBind(ExtendedFooterStatusDisplayItem item){
			Status s=item.status;
			if(s.favouritesCount>0){
				favorites.setVisibility(View.VISIBLE);
				favorites.setText(getFormattedPlural(R.plurals.x_favorites, s.favouritesCount));
			}else{
				favorites.setVisibility(View.GONE);
			}
			if(s.reblogsCount>0){
				reblogs.setVisibility(View.VISIBLE);
				reblogs.setText(getFormattedPlural(R.plurals.x_reblogs, s.reblogsCount));
			}else{
				reblogs.setVisibility(View.GONE);
			}
			if(s.favouritesCount==0 && s.reblogsCount==0){
				buttonsView.setVisibility(View.GONE);
			}else{
				buttonsView.setVisibility(View.VISIBLE);
			}
			String timeStr=TIME_FORMATTER.format(item.status.createdAt.atZone(ZoneId.systemDefault()));
			if(item.status.application!=null && !TextUtils.isEmpty(item.status.application.name)){
				time.setText(item.parentFragment.getString(R.string.timestamp_via_app, timeStr, item.status.application.name));
			}else{
				time.setText(timeStr);
			}
		}

		@Override
		public boolean isEnabled(){
			return false;
		}

		private SpannableStringBuilder getFormattedPlural(@PluralsRes int res, int quantity){
			String str=item.parentFragment.getResources().getQuantityString(res, quantity, quantity);
			String formattedNumber=String.format(Locale.getDefault(), "%,d", quantity);
			int index=str.indexOf(formattedNumber);
			SpannableStringBuilder ssb=new SpannableStringBuilder(str);
			if(index>=0){
				ssb.setSpan(new TypefaceSpan("sans-serif-medium"), index, index+formattedNumber.length(), 0);
				ssb.setSpan(new ForegroundColorSpan(UiUtils.getThemeColor(item.parentFragment.getActivity(), android.R.attr.textColorPrimary)), index, index+formattedNumber.length(), 0);
			}
			return ssb;
		}

		private void startAccountListFragment(Class<? extends StatusRelatedAccountListFragment> cls){
			Bundle args=new Bundle();
			args.putString("account", item.parentFragment.getAccountID());
			args.putParcelable("status", Parcels.wrap(item.status));
			Nav.go(item.parentFragment.getActivity(), cls, args);
		}
	}
}
