package io.koju.autopos.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityTestUtil {

    public static void makeSystemUserCurrentUser() {
        setCurrentUserDetails(CurrentUserTestUtil.getSystemUser());
    }

    public static void setCurrentUserDetails(UserDetails userDetails) {
        mockSecurityContext(userDetails);
    }

    private static void mockSecurityContext(Object principal) {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication())
            .thenReturn(authentication);

        when(authentication.getPrincipal())
            .thenReturn(principal);

        SecurityContextHolder.setContext(securityContext);
    }
}
