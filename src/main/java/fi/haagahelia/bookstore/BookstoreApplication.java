package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fi.haagahelia.bookstore.domain.AppUser;
import fi.haagahelia.bookstore.domain.AppUserRepository;
import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.web.BookRepository;
import fi.haagahelia.bookstore.web.CategoryRepository;

@SpringBootApplication
public class BookstoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookstoreApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(
            BookRepository bookRepository,
            CategoryRepository categoryRepository,
            AppUserRepository appUserRepository) {

        return (args) -> {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            AppUser user1 = new AppUser(
                    "user",
                    passwordEncoder.encode("user"),
                    "user@bookstore.com", "USER");

            AppUser user2 = new AppUser(
                    "admin", passwordEncoder.encode("admin"),
                    "admin@bookstore.com",
                    "ADMIN");
            appUserRepository.save(user1);
            appUserRepository.save(user2);
            Category category1 = new Category("Fantasy");
            Category category2 = new Category("Science Fiction");
            Category category3 = new Category("Classic");

            categoryRepository.save(category1);
            categoryRepository.save(category2);
            categoryRepository.save(category3);

            Book book1 = new Book(
                    "The Great Gatsby",
                    "F. Scott Fitzgerald",
                    1925,
                    "9780743273565",
                    12.99,
                    category3);

            Book book2 = new Book(
                    "1984",
                    "George Orwell",
                    1949,
                    "9780451524935",
                    10.99,
                    category2);

            Book book3 = new Book(
                    "To Kill a Mockingbird",
                    "Harper Lee",
                    1960,
                    "9780061120084",
                    14.99,
                    category3);

            bookRepository.save(book1);
            bookRepository.save(book2);
            bookRepository.save(book3);
        };
    }
}