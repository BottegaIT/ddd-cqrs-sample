package pl.com.bottega.erp.crm.application;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pl.com.bottega.ddd.application.annotation.ApplicationService;

@ApplicationService("userDetailsService")
public class DummyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        List<GrantedAuthorityImpl> grantedAuthorities = asList(new GrantedAuthorityImpl("ROLE_CLIENT"));
        return new User(username, "secret", true, true, true, true, grantedAuthorities);
    }

}
