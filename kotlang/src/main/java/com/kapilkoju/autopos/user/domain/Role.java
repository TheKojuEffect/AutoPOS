package com.kapilkoju.autopos.user.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

public enum Role {

    ADMIN,
    USER;

    private final static String PREFIX = "ROLE_";

    public String getName() {
        return PREFIX + name();
    }

    @Converter
    public static class RoleConverter implements AttributeConverter<Role, String> {

        @Override
        public String convertToDatabaseColumn(Role role) {
            return role.getName();
        }

        @Override
        public Role convertToEntityAttribute(String dbRole) {

            final String roleName = dbRole.replace(PREFIX, "");
            return Role.valueOf(roleName);
        }
    }


}
