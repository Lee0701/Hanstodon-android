package io.github.lee0701.mastodon.android.utils;

import io.github.lee0701.mastodon.android.api.session.AccountSessionManager;
import io.github.lee0701.mastodon.android.model.Filter;
import io.github.lee0701.mastodon.android.model.Status;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StatusFilterPredicate implements Predicate<Status>{
	private final List<Filter> filters;

	public StatusFilterPredicate(List<Filter> filters){
		this.filters=filters;
	}

	public StatusFilterPredicate(String accountID, Filter.FilterContext context){
		filters=AccountSessionManager.getInstance().getAccount(accountID).wordFilters.stream().filter(f->f.context.contains(context)).collect(Collectors.toList());
	}

	@Override
	public boolean test(Status status){
		CharSequence content=status.getContentStatus().content;
		for(Filter filter:filters){
			if(filter.matches(content))
				return false;
		}
		return true;
	}
}
