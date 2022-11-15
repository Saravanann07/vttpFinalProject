package vttp2022batch1.finalProjectServer.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import vttp2022batch1.finalProjectServer.models.AppUser;
import vttp2022batch1.finalProjectServer.models.Registration;
import vttp2022batch1.finalProjectServer.repositories.UserRepository;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepo;


    public boolean addUser(Registration reg){
        Integer addUser = userRepo.addUser(reg);
        return addUser == 1;  
    }

    public boolean authenticate(AppUser user){
        return 1 == userRepo.countUsersByNameAndPassword(user);
    }

    public boolean checkUserExists(String username){
        return 1 == userRepo.checkUserExists(username);
    }




    
}
