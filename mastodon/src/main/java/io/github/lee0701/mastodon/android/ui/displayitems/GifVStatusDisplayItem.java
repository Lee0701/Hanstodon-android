package io.github.lee0701.mastodon.android.ui.displayitems;

import android.app.Activity;
import android.graphics.Outline;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.fragments.BaseStatusListFragment;
import io.github.lee0701.mastodon.android.model.Attachment;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.ui.PhotoLayoutHelper;

import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;

public class GifVStatusDisplayItem extends ImageStatusDisplayItem{
	public GifVStatusDisplayItem(String parentID, Status status, Attachment attachment, BaseStatusListFragment parentFragment, int index, int totalPhotos, PhotoLayoutHelper.TiledLayoutResult tiledLayout, PhotoLayoutHelper.TiledLayoutResult.Tile thisTile){
		super(parentID, parentFragment, attachment, status, index, totalPhotos, tiledLayout, thisTile);
		request=new UrlImageLoaderRequest(attachment.previewUrl, 1000, 1000);
	}

	@Override
	public Type getType(){
		return Type.GIFV;
	}

	public static class Holder extends ImageStatusDisplayItem.Holder<GifVStatusDisplayItem>{

		public Holder(Activity activity, ViewGroup parent){
			super(activity, R.layout.display_item_gifv, parent);
			View play=findViewById(R.id.play_button);
			play.setOutlineProvider(new ViewOutlineProvider(){
				@Override
				public void getOutline(View view, Outline outline){
					outline.setOval(0, 0, view.getWidth(), view.getHeight());
					outline.setAlpha(.99f); // fixes shadow rendering
				}
			});
		}
	}
}
