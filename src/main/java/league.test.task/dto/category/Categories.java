package league.test.task.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Data
public class Categories {
    private List<Category> categories;

    @JsonIgnore
    public Category findByName(String name) {
        return this.categories.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().orElseThrow(
                () -> new NoSuchElementException("No category with name = " + name));
    }

    @JsonIgnore
    public Optional<Category> findByNameOptional(String name) {
        return this.categories.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst();
    }
}
