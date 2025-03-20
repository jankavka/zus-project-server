package cz.kavka.service.serviceinterface;

import cz.kavka.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO create (UserDTO userDTO);
}
