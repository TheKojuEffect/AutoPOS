package io.koju.autopos.shared.struct.filter;

import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;

public abstract class FilterParam<T, P extends Path<T>> {

    protected T filter;

    protected FilterParam(String requestParam) {
        populateWithRequestParam(requestParam);
    }

    public abstract void populateWithRequestParam(String requestParam);

    public abstract Predicate toQueryDslPredicate(P path);

    protected T getFilter() {
        return filter;
    }

    protected void setFilter(T filter) {
        this.filter = filter;
    }
}
