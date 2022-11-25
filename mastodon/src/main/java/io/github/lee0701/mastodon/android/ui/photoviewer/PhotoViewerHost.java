package io.github.lee0701.mastodon.android.ui.photoviewer;

import io.github.lee0701.mastodon.android.model.Status;

public interface PhotoViewerHost{
	void openPhotoViewer(String parentID, Status status, int attachmentIndex);
}
