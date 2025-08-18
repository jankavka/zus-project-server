package service;

import cz.kavka.dto.UserDTO;
import cz.kavka.entity.UserEntity;
import cz.kavka.entity.repository.UserRepository;
import cz.kavka.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    UserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(userRepository, passwordEncoder);
    }

    @Test
    void create_encodesPassword_savesEntity_andReturnsDtoWithoutPassword() {
        // arrange
        UserDTO input = new UserDTO();
        input.setEmail("john@example.com");
        input.setPassword("secret");

        when(passwordEncoder.encode("secret")).thenReturn("ENCODED");
        UserEntity saved = new UserEntity();
        saved.setId(42L);
        saved.setEmail("john@example.com");
        saved.setPassword("ENCODED");
        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);

        // act
        UserDTO result = service.create(input);

        // assert: encoder called with raw password
        verify(passwordEncoder).encode(eq("secret"));

        // assert: repository saved the encoded password
        ArgumentCaptor<UserEntity> captor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(captor.capture());
        UserEntity toSave = captor.getValue();
        assertEquals("john@example.com", toSave.getEmail());
        assertEquals("ENCODED", toSave.getPassword());
        assertNotEquals("secret", toSave.getPassword());

        // assert: returned DTO maps id & email, not password
        assertEquals(42L, result.getId());
        assertEquals("john@example.com", result.getEmail());
        // if UserDTO has getPassword(), it should not be exposed
        try {
            var m = UserDTO.class.getMethod("getPassword");
            assertNull(m.invoke(result), "Returned DTO should not expose password");
        } catch (NoSuchMethodException ignored) {
            // DTO has no password getter – that's fine and even better
        } catch (Exception e) {
            fail(e);
        }
    }

    @Test
    void loadUserByUsername_returnsEntity_whenFound() {
        // Given: repository returns a user (UserEntity should implement UserDetails)
        UserEntity entity = new UserEntity();
        entity.setEmail("john@example.com");
        entity.setPassword("hash");
        when(userRepository.findByEmail("john@example.com"))
                .thenReturn(Optional.of(entity));

        // When
        UserDetails details = service.loadUserByUsername("john@example.com");

        // Then
        assertSame(entity, details);
        verify(userRepository).findByEmail("john@example.com");
    }

    @Test
    void loadUserByUsername_throws_whenMissing() {
        when(userRepository.findByEmail("missing@example.com"))
                .thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(
                UsernameNotFoundException.class,
                () -> service.loadUserByUsername("missing@example.com")
        );
        assertTrue(ex.getMessage().contains("missing@example.com"));
        verify(userRepository).findByEmail("missing@example.com");
    }
}
