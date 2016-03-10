package io.koju.autopos.catalog.schema;

import io.koju.autopos.shared.struct.filter.RequestFilterParams;
import io.koju.autopos.shared.struct.filter.TextFilter;

import java.util.Optional;

public class ItemFilter implements RequestFilterParams {

    private TextFilter code;

    private TextFilter name;

    public Optional<TextFilter> getCodeFilter() {
        return Optional.ofNullable(code);
    }

    public Optional<TextFilter> getNameFilter() {
        return Optional.ofNullable(name);
    }

}
