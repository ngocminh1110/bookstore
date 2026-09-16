package fi.haagahelia.bookstore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.web.BookRepository;

@SpringBootApplication
public class BookstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}
@Bean
public CommandLineRunner initializeDatabase(BookRepository bookRepository) {
    return (args) -> {
        
			Book book1 = new Book("The Great Gatsby",
                    "F. Scott Fitzgerald",
                    1925,
                    "9780743273565",
                    12.99
            );
			bookRepository.save(book1);

			Book book2 = new Book("1984",
                    "George Orwell",
                    1949,
                    "9780451524935",
                    10.99
            );
			bookRepository.save(book2);
			Book book3 = new Book(
                    "To Kill a Mockingbird",
                    "Harper Lee",
                    1960,
                    "9780061120084",
                    14.99
            );
            bookRepository.save(book3);

    };
}
}
