package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository userRepository;

    @Test
    public void createNewUser() {
        AppUser user = new AppUser("testuser", "hashedpassword", "testuser@bookstore.com", "USER");
        userRepository.save(user);

        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        userRepository.save(new AppUser("searchuser", "hashedpassword", "search@bookstore.com", "ADMIN"));

        AppUser user = userRepository.findByUsername("searchuser");

        assertThat(user).isNotNull();
        assertThat(user.getEmail()).isEqualTo("search@bookstore.com");
        assertThat(user.getRole()).isEqualTo("ADMIN");
    }

    @Test
    public void deleteUser() {
        AppUser user = new AppUser("deleteuser", "hashedpassword", "delete@bookstore.com", "USER");
        userRepository.save(user);
        Long id = user.getId();

        userRepository.deleteById(id);

        Optional<AppUser> deletedUser = userRepository.findById(id);
        assertThat(deletedUser).isEmpty();
        assertThat(userRepository.findByUsername("deleteuser")).isNull();
    }
}