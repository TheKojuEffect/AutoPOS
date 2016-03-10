package io.koju.autopos.shared.struct.filter;


public class ValueFilter<T> implements FilterParamType<ValueFilter<T>> {

    public enum FilterType {
        EQUAL,
        GREATER_THAN,
        LESS_THAN
    }

    private String fieldName;

    private T filterValue;

    private FilterType filterType;


    @Override
    public ValueFilter<T> setFieldsFromString(String term) {
        return null;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public T getFilterValue() {
        return filterValue;
    }

    public void setFilterValue(T filterValue) {
        this.filterValue = filterValue;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
