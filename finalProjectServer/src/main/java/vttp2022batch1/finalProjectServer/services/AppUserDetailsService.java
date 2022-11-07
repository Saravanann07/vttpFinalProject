package vttp2022batch1.finalProjectServer.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vttp2022batch1.finalProjectServer.models.AppUser;
import vttp2022batch1.finalProjectServer.repositories.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<AppUser> opt = userRepo.findByUsername(username);

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        AppUser appUser = opt.get();

        return new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }

    public Integer getUserId(String username){

        return userRepo.getUserId(username);
    }


    
}
