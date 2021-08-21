package league.test.task;

import league.test.task.dto.category.Category;
import league.test.task.endpoints.CategoriesEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.fail;

public class SecondTest extends BaseTest {

    @Lazy
    @Autowired
    private CategoriesEndpoint categoriesEndpoint;

    private Optional<Category> boxesCategory;
    private String boxes = "boxes";

    @Test
    public void secondTest() {
        List<Category> categories = categoriesEndpoint.getListOfCategories().getBodyAsListOf(Category.class);
        boxesCategory = categories.stream().filter(x -> x.getName().equalsIgnoreCase(boxes)).findFirst();
        if (!boxesCategory.isPresent()) fail("Нет категории с названием " + boxes);
    }

    //правильная строка []{}()
    @Test
    public void hardTaskFromSobes() {
        List<String> poolOfStrings = asList("[]{}()", "{}][()", ")({}[]", "(]{)[}", "()[){}");
        for(String str : poolOfStrings) defineWasTheStringCorrect(str);
    }

    private void defineWasTheStringCorrect(String str) {
        LinkedHashMap<Integer, List<Character>> linkedHashMap = new LinkedHashMap<Integer, List<Character>>() {{
            put(40, asList('(', ')'));
            put(41, asList('(', ')'));
            put(123, asList('{', '}'));
            put(125, asList('{', '}'));
            put(91, asList('[', ']'));
            put(93, asList('[', ']'));
        }};
        boolean result = false;
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i = i + 2) {
            Character currentSymbol = array[i];
            int integerRepresentation = (int) currentSymbol;
            List<Character> characterList = linkedHashMap.get(integerRepresentation);
            Character nextSymbol = array[i + 1];
            result = characterList.contains(nextSymbol);
            if(result == false) break;
        }
        System.out.println("Result for string '" + str + "' is: " + result);
    }
}