package io.koju.autopos.catalog.struct.filter;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import io.koju.autopos.catalog.domain.Item;
import io.koju.autopos.catalog.domain.QItem;
import io.koju.autopos.shared.struct.filter.FilterRequest;
import io.koju.autopos.shared.struct.filter.TextFilter;

import java.util.Optional;

public class ItemFilter implements FilterRequest<Item, QItem> {

    private TextFilter code;

    private TextFilter name;

    @Override
    public Predicate toQueryDslPredicate(QItem item) {
        BooleanBuilder predicate = new BooleanBuilder();
        Optional.ofNullable(code).map(codeFilter -> codeFilter.toQueryDslPredicate(item.code)).ifPresent(predicate::and);
        Optional.ofNullable(name).map(nameFilter -> nameFilter.toQueryDslPredicate(item.name)).ifPresent(predicate::and);
        return predicate;
    }
}
