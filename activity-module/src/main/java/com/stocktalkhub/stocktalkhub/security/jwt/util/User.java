package com.stocktalkhub.stocktalkhub.security.jwt.util;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
public class User implements UserDetails {
    private final Long id;
    private final String email;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public User(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

//    public static User from(Member member) {
//
//        return new User(
//                member.getId(),
//                member.getEmail(),
//                member.getPassword(),
//                Set.of());
//                        roleTypes.stream()
//                                .map(MemberRole::getName)
//                                .map(SimpleGrantedAuthority::new)
//                                .collect(Collectors.toUnmodifiableSet()))

//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}