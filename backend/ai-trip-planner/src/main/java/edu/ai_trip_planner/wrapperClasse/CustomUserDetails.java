package edu.ai_trip_planner.wrapperClasse;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.ai_trip_planner.entities.User;

public class CustomUserDetails implements UserDetails{

    private User user;

    public CustomUserDetails(User user){
        this.user=user;
    }

   @Override
    public String getUsername() {
        return user.getEmail();       // use email as username
    }

    @Override
    public String getPassword() {
        return user.getPassword();    // returns hashed password
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();             // no roles — all users equal
    }

    @Override public boolean isAccountNonExpired()     { return true; }
    @Override public boolean isAccountNonLocked()      { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()               { return true; }

    // expose the original User if needed elsewhere
    public User getUser() {
        return user;
    }

}
