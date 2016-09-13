package io.koju.autopos.party.repo;

import com.querydsl.core.types.dsl.StringExpression;
import io.koju.autopos.party.domain.QVehicle;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface VehicleQuerydslBinderCustomizer extends QuerydslBinderCustomizer<QVehicle> {

    @Override
    default void customize(QuerydslBindings bindings, QVehicle qVehicle) {
        bindings.bind(qVehicle.number, qVehicle.owner.name)
                .first(StringExpression::containsIgnoreCase);
    }
}
