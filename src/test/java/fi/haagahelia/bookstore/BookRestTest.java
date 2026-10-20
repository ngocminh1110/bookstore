package fi.haagahelia.bookstore;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import fi.haagahelia.bookstore.domain.Book;
import fi.haagahelia.bookstore.web.BookRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class BookRestTest {
        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private BookRepository bookRepository;

        @Test
        public void getBooksWithoutLoginRedirectsToLogin() throws Exception {
                mockMvc.perform(get("/books"))
                                .andExpect(status().is3xxRedirection());
        }

        @Test
        @WithMockUser(username = "user", authorities = "USER")
        public void getAllBooksReturnsJson() throws Exception {
                mockMvc.perform(get("/books"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                                .andExpect(content().string(containsString("The Great Gatsby")));

        }

        @Test
        @WithMockUser(username = "user", authorities = "USER")
        public void getOneBookById() throws Exception {
                Book book = bookRepository
                                .save(new Book("Rest Test Book", "Rest Author", 2024, "9999999999", 20.0, null));

                mockMvc.perform(get("/books/" + book.getId()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Rest Test Book"))
                                .andExpect(jsonPath("$.author").value("Rest Author"));
        }

        @Test
        @WithMockUser(username = "user", authorities = "USER")
        public void getBooksFromDataRestApi() throws Exception {
                mockMvc.perform(get("/api/books"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.books").isArray());
        }

        @Test
        @WithMockUser(username = "user", authorities = "USER")
        public void getCategoriesFromDataRestApi() throws Exception {
                mockMvc.perform(get("/api/categories"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$._embedded.categories").isArray());
        }

        @Test
        @WithMockUser(username = "admin", authorities = "ADMIN")
        public void createAndDeleteBookWithDataRestApi() throws Exception {
                String json = "{\"title\":\"Api Book\",\"author\":\"Api Author\","
                                + "\"publicationYear\":2023,\"isbn\":\"5555555555\",\"price\":11.5}";

                String location = mockMvc.perform(post("/api/books")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json))
                                .andExpect(status().isCreated())
                                .andReturn().getResponse().getHeader("Location");

                mockMvc.perform(delete(location).with(csrf()))
                                .andExpect(status().isNoContent());
                mockMvc.perform(get(location))
                                .andExpect(status().isNotFound());
        }
}
