package task;

import dto.category.Category;
import endpoints.CategoriesEndpoint;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

public class SecondTest extends BaseTest {

    private Optional<Category> boxesCategory;
    private CategoriesEndpoint categoriesEndpoint = new CategoriesEndpoint();

    @Test
    public void secondTest() {
        List<Category> categories = categoriesEndpoint.getListOfCategories(spec).getBodyAsListOf(Category.class);
        boxesCategory = categories.stream().filter(x -> x.getName().equalsIgnoreCase(boxes)).findFirst();
        if (!boxesCategory.isPresent()) fail("Нет категории с названием " + boxes);
    }
}
