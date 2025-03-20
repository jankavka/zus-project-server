package cz.kavka.service;

import cz.kavka.dto.UserDTO;
import cz.kavka.service.serviceinterface.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//waits for implementation
@Service
public class UserServiceImpl implements UserService {
    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
