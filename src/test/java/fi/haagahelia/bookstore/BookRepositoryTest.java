package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.web.BookRepository;
import fi.haagahelia.bookstore.web.CategoryRepository;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createNewBook() {
        Category category = new Category("Test Category");
        categoryRepository.save(category);

        Book book = new Book("Test Book", "Test Author", 2024, "1234567890", 19.99, category);
        bookRepository.save(book);

        assertThat(book.getId()).isNotNull();
    }

    @Test
    public void findByTitleShouldReturnBook() {
        bookRepository.save(new Book("Search Title", "Some Author", 2020, "1111111111", 9.99, null));

        List<Book> books = bookRepository.findByTitle("Search Title");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getAuthor()).isEqualTo("Some Author");
    }

    @Test
    public void findByAuthorShouldReturnBook() {
        bookRepository.save(new Book("Another Book", "Unique Author", 2021, "2222222222", 15.50, null));

        List<Book> books = bookRepository.findByAuthor("Unique Author");

        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Another Book");
    }

    @Test
    public void deleteBook() {
        Book book = new Book("Delete Me", "Delete Author", 2019, "3333333333", 5.00, null);
        bookRepository.save(book);
        Long id = book.getId();

        bookRepository.deleteById(id);

        Optional<Book> deletedBook = bookRepository.findById(id);
        assertThat(deletedBook).isEmpty();
    }
}