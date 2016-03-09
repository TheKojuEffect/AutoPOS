package io.koju.autopos.shared.util;


import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ClassUtilTest {

    @Test
    public void testGetAllFields() {

        List<Field> parentFields = ClassUtil.getAllFields(Parent.class);
        assertThat(parentFields, hasSize(1));

        List<Field> childFields = ClassUtil.getAllFields(Child.class);
        assertThat(childFields, hasSize(2));

    }

    static class Parent {
        private String parent;
    }

    static class Child extends Parent {
        private String child;
    }

}
