package io.github.lee0701.mastodon.android.api.requests.statuses;

import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import io.github.lee0701.mastodon.android.MastodonApp;
import io.github.lee0701.mastodon.android.api.ContentUriRequestBody;
import io.github.lee0701.mastodon.android.api.MastodonAPIRequest;
import io.github.lee0701.mastodon.android.api.ProgressListener;
import io.github.lee0701.mastodon.android.api.ResizedImageRequestBody;
import io.github.lee0701.mastodon.android.model.Attachment;
import io.github.lee0701.mastodon.android.ui.utils.UiUtils;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadAttachment extends MastodonAPIRequest<Attachment>{
	private Uri uri;
	private ProgressListener progressListener;
	private int maxImageSize;
	private String description;

	public UploadAttachment(Uri uri){
		super(HttpMethod.POST, "/media", Attachment.class);
		this.uri=uri;
	}

	public UploadAttachment(Uri uri, int maxImageSize, String description){
		this(uri);
		this.maxImageSize=maxImageSize;
		this.description=description;
	}

	public UploadAttachment setProgressListener(ProgressListener progressListener){
		this.progressListener=progressListener;
		return this;
	}

	@Override
	public RequestBody getRequestBody() throws IOException{
		MultipartBody.Builder builder=new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("file", UiUtils.getFileName(uri), maxImageSize>0 ? new ResizedImageRequestBody(uri, maxImageSize, progressListener) : new ContentUriRequestBody(uri, progressListener));
		if(!TextUtils.isEmpty(description))
			builder.addFormDataPart("description", description);
		return builder.build();
	}
}
