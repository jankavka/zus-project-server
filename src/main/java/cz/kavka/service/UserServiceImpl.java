package cz.kavka.service;

import cz.kavka.dto.UserDTO;
import cz.kavka.entity.UserEntity;
import cz.kavka.entity.repository.UserRepository;
import cz.kavka.service.serviceinterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//waits for implementation
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity savedEntity = userRepository.save(userEntity);

        UserDTO returnedUser = new UserDTO();
        returnedUser.setEmail(savedEntity.getEmail());
        returnedUser.setId(savedEntity.getId());

        return returnedUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
    }
}
