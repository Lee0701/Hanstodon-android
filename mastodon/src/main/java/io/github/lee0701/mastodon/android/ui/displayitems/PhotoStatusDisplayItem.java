package io.github.lee0701.mastodon.android.ui.displayitems;

import android.app.Activity;
import android.view.ViewGroup;

import io.github.lee0701.mastodon.android.R;
import io.github.lee0701.mastodon.android.fragments.BaseStatusListFragment;
import io.github.lee0701.mastodon.android.model.Attachment;
import io.github.lee0701.mastodon.android.model.Status;
import io.github.lee0701.mastodon.android.ui.PhotoLayoutHelper;

import me.grishka.appkit.imageloader.requests.UrlImageLoaderRequest;

public class PhotoStatusDisplayItem extends ImageStatusDisplayItem{
	public PhotoStatusDisplayItem(String parentID, Status status, Attachment photo, BaseStatusListFragment parentFragment, int index, int totalPhotos, PhotoLayoutHelper.TiledLayoutResult tiledLayout, PhotoLayoutHelper.TiledLayoutResult.Tile thisTile){
		super(parentID, parentFragment, photo, status, index, totalPhotos, tiledLayout, thisTile);
		request=new UrlImageLoaderRequest(photo.url, 1000, 1000);
	}

	@Override
	public Type getType(){
		return Type.PHOTO;
	}

	public static class Holder extends ImageStatusDisplayItem.Holder<PhotoStatusDisplayItem>{

		public Holder(Activity activity, ViewGroup parent){
			super(activity, R.layout.display_item_photo, parent);
		}
	}
}
