package task;

import dto.breed.Breed;
import dto.favourites.DeleteFavouriteAnswer;
import dto.favourites.Favourite;
import dto.favourites.PostFavouriteAnswer;
import dto.images.BreedImages;
import endpoints.BreedsEndpoint;
import endpoints.FavouritesEndpoint;
import endpoints.ImagesEndpoint;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class OurLittleTest extends BaseTest {

    private String breedText = "Scottish Fold";

    private String id;
    private BreedImages imageBody;
    private String breed_id;
    private String imageId;
    private String imageUrl;
    private String message;
    private Optional<Favourite> ourFavourite;
    private Integer favouriteId;
    private List<Favourite> favouriteList;

    private BreedsEndpoint breedsEndpoint = new BreedsEndpoint();
    private ImagesEndpoint imagesEndpoint = new ImagesEndpoint();
    private FavouritesEndpoint favouritesEndpoint = new FavouritesEndpoint();

    private String fileName = "report.txt";
    private Path pathToReport;
    private BufferedWriter writer;

    @BeforeAll
    public void beforeALlInOurLittleTest() throws IOException {
        prepareReport();
    }

    private void prepareReport() throws IOException {
        Files.deleteIfExists(get(fileName));
        pathToReport = Files.createFile(get(fileName));
        writer = new BufferedWriter(new FileWriter(pathToReport.toFile(), true));
    }

    //правильная строка []{}()
    @Test
    public void hardTaskFromSobes() {
        LinkedHashMap<Integer, List<Character>> linkedHashMap = new LinkedHashMap<Integer, List<Character>>() {{
            put(40, asList('(', ')'));
            put(41, asList('(', ')'));
            put(123, asList('{', '}'));
            put(125, asList('{', '}'));
            put(91, asList('[', ']'));
            put(93, asList('[', ']'));
        }};

        String str = "[}{]";
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
        System.out.println("RESULT: " + result);
    }

    @Test
    public void test() throws IOException {
        //1
        List<Breed> breedList = breedsEndpoint.search(spec, breedText).getBodyAsListOf(Breed.class);
        id = breedList.get(0).getId();
        //2
        List<BreedImages> breedImages = imagesEndpoint.search(spec, id).getBodyAsListOf(BreedImages.class);
        if (breedImages.size() == 0) fail("Нет картинок для породы с id = " + id);
        imageBody = breedImages.get(0);
        breed_id = imageBody.getBreeds().get(0).getId();
        assertEquals(id, breed_id);
        imageId = imageBody.getId();
        imageUrl = imageBody.getUrl();

        writer.append("Breed id: " + breed_id + "\n");
        writer.append("Image id: " + imageId + "\n");
        writer.append("Image URL: " + imageUrl + "\n");
        writer.close();
        attachReport();
        //3
        PostFavouriteAnswer favouritesBody = favouritesEndpoint.postFavourites(spec, imageId).getBodyAs(PostFavouriteAnswer.class);
        message = favouritesBody.getMessage();
        assertEquals(message, "SUCCESS");
        favouriteId = favouritesBody.getId();
        //4
        favouriteList = favouritesEndpoint.getFavourites(spec).getBodyAsListOf(Favourite.class);
        ourFavourite = favouriteList.stream().filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (!ourFavourite.isPresent()) fail("Не нашли избранного кота с id " + favouriteId);
        assertEquals(ourFavourite.get().getImage().getUrl(), imageUrl);
        //5
        message = favouritesEndpoint.deleteFavourite(spec, favouriteId).getBodyAs(DeleteFavouriteAnswer.class).getMessage();
        assertEquals(message, "SUCCESS");
        //6
        favouriteList = favouritesEndpoint.getFavourites(spec).getBodyAsListOf(Favourite.class);
        ourFavourite = favouriteList.stream().filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (ourFavourite.isPresent()) fail(format("Изображение с id = %d все еще в избранных", favouriteId));
    }

    @Attachment(value = "report", type = "text/plain", fileExtension = ".txt")
    public byte[] attachReport() throws IOException {
        byte[] array = Files.readAllBytes(pathToReport);
        return array;
    }

}
