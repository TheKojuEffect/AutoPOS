package io.koju.autopos.kernel.struct.filter;


import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;

public interface FilterRequest<T, B extends EntityPathBase<T>> {

    Predicate toQueryDslPredicate(B entityPath);

}
