package league.test.task;

import league.test.task.dto.category.Categories;
import league.test.task.dto.category.Category;
import league.test.task.endpoints.CategoriesEndpoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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
        Categories categories = categoriesEndpoint.getListOfCategories();
        boxesCategory = categories.findByNameOptional(boxes);
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

    @Test
    public void wasTheStringAPalindrome(){
        Boolean result = true;
        String str = "qweww";
        int i = 0, j = str.length() - 1;

        while (i < j) {
            if (str.charAt(i) != str.charAt(j)){
                result = false;
                break;
            }
            i++;
            j--;
        }
        System.out.println("Was the string: '" +str+ "' a palindrome? " + result);
    }

    @Test
    public void revertString() {
        String string = "Y*T*R*E*W*Q";
        String result = "";
        char[] array = string.toCharArray();
        for(int i = array.length - 1; i >=0 ; i --){
            String currentSymbol = String.valueOf(array[i]);
            result = result + currentSymbol;
        }
        System.out.println("Initial string:  " + string);
        System.out.println("Reverted string: " + result);
    }
}