package com.kapilkoju.autopos.web.rest.dto;

import com.kapilkoju.autopos.config.Constants;
import com.kapilkoju.autopos.user.domain.Authority;
import com.kapilkoju.autopos.user.domain.User;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated;

    @Size(min = 2, max = 5)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for MapStruct.
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getLangKey(),
            "", user.getCreatedDate(), "", user.getLastModifiedDate(),
            user.getUserAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()));
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
        String email, boolean activated, String langKey,
        String createdBy, Instant createdDate, String lastModifiedBy, Instant lastModifiedDate,
        Set<String> authorities) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
