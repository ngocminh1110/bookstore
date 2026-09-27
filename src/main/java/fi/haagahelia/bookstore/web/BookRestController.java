package fi.haagahelia.bookstore.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fi.haagahelia.bookstore.domain.Book;

@RestController
public class BookRestController {
    @Autowired
    private BookRepository repository;

    @GetMapping("/books")
    public Iterable<Book> getBooks() {
        return repository.findAll();
    }

    @GetMapping("/books/{id}")
    public Optional<Book> getBook(@PathVariable("id") Long bookId) {
        return repository.findById(bookId);
    }

}
