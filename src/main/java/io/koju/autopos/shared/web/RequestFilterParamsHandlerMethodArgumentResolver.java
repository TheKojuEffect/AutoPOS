package io.koju.autopos.shared.web;

import io.koju.autopos.catalog.schema.ItemFilter;
import io.koju.autopos.shared.struct.filter.FilterParamType;
import io.koju.autopos.shared.struct.filter.RequestFilterParams;
import io.koju.autopos.shared.util.ClassUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.List;

public class RequestFilterParamsHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return RequestFilterParams.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
        throws Exception {

        List<Field> fields = ClassUtil.getAllFields(ItemFilter.class, FilterParamType.class);

        RequestFilterParams filterRequest = (RequestFilterParams) parameter.getParameterType().newInstance();

        for (Field field : fields) {
            String fieldValue = webRequest.getParameter(field.getName());
            if (fieldValue == null) {
                continue;
            }
            field.setAccessible(true);
            FilterParamType param = (FilterParamType) field.getType().newInstance();
            param.setFieldsFromString(fieldValue);
            field.set(filterRequest, param);
            field.setAccessible(false);
        }

        return filterRequest;
    }


}
