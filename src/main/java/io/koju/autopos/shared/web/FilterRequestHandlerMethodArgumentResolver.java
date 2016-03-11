package io.koju.autopos.shared.web;

import io.koju.autopos.catalog.struct.filter.ItemFilter;
import io.koju.autopos.shared.struct.filter.FilterParam;
import io.koju.autopos.shared.struct.filter.FilterRequest;
import io.koju.autopos.shared.util.ClassUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.List;

public class FilterRequestHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return FilterRequest.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
        throws Exception {

        List<Field> fields = ClassUtil.getAllFields(ItemFilter.class, FilterParam.class);

        FilterRequest filterRequest = (FilterRequest) parameter.getParameterType().newInstance();

        for (Field field : fields) {
            String fieldValue = webRequest.getParameter(field.getName());
            if (fieldValue == null) {
                continue;
            }
            field.setAccessible(true);
            Class<FilterParam> filterParamType = (Class<FilterParam>) field.getType();
            FilterParam filterParam = filterParamType.getDeclaredConstructor(String.class).newInstance(fieldValue);
            filterParam.populateWithRequestParam(fieldValue);
            field.set(filterRequest, filterParam);
            field.setAccessible(false);
        }

        return filterRequest;
    }


}
