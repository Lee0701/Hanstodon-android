package io.github.lee0701.mastodon.android.events;

public class FinishReportFragmentsEvent{
	public final String reportAccountID;

	public FinishReportFragmentsEvent(String reportAccountID){
		this.reportAccountID=reportAccountID;
	}
}
