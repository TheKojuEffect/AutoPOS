package io.koju.autopos.security;

import io.koju.autopos.user.domain.User;
import io.koju.autopos.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component

public class CurrentUserTestUtil {

    private static User SYSTEM_USER;

    public static User getSystemUser() {
        return SYSTEM_USER;
    }

    @Autowired
    public CurrentUserTestUtil(UserService userService) {
        User systemUser = userService.getSystemUser();
        Assert.notNull(systemUser);

        SYSTEM_USER = systemUser;
    }
}
