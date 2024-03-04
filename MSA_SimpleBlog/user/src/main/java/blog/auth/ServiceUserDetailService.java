package blog.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import blog.domain.User;
import blog.domain.UserRepository;


@Service
public class ServiceUserDetailService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user!=null){
            return new ServiceUserDetail(user);
        } else {
            System.out.println("로그인 실패");
        }
        return null;
        
    }
    
}