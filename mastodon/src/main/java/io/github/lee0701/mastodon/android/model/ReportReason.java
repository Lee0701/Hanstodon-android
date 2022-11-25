package io.github.lee0701.mastodon.android.model;

import com.google.gson.annotations.SerializedName;

public enum ReportReason{
	PERSONAL,
	@SerializedName("spam")
	SPAM,
	@SerializedName("violation")
	VIOLATION,
	@SerializedName("other")
	OTHER
}
