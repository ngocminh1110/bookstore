package fi.haagahelia.bookstore;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import fi.haagahelia.bookstore.domain.Category;
import fi.haagahelia.bookstore.web.CategoryRepository;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void createNewCategory() {
        Category category = new Category("Horror");
        categoryRepository.save(category);

        assertThat(category.getId()).isNotNull();
    }

    @Test
    public void findByNameShouldReturnCategory() {
        categoryRepository.save(new Category("Poetry"));

        List<Category> categories = categoryRepository.findByName("Poetry");

        assertThat(categories).hasSize(1);
        assertThat(categories.get(0).getName()).isEqualTo("Poetry");
    }

    @Test
    public void deleteCategory() {
        Category category = new Category("Delete Category");
        categoryRepository.save(category);
        Long id = category.getId();

        categoryRepository.deleteById(id);

        Optional<Category> deletedCategory = categoryRepository.findById(id);
        assertThat(deletedCategory).isEmpty();
    }
}