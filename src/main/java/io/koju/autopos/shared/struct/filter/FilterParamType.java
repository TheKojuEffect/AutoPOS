package io.koju.autopos.shared.struct.filter;

public interface FilterParamType<T extends FilterParamType<?>> {

    T setFieldsFromString(String term);

}
