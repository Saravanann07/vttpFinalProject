package vttp2022batch1.finalProjectServer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022batch1.finalProjectServer.models.User;
import vttp2022batch1.finalProjectServer.repositories.UserRepository;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepo;

    public boolean addUser(User user){
        Integer addUser = userRepo.addUser(user);
        return addUser == 1;  
    }

    public boolean authenticate(User user){
        return 1 == userRepo.countUsersByNameAndPassword(user);
    }
    
}
