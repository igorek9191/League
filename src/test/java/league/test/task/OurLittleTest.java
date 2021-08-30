package league.test.task;

import io.qameta.allure.Attachment;
import league.test.task.BaseTest;
import league.test.task.dto.breed.Breed;
import league.test.task.dto.favourites.DeleteFavouriteAnswer;
import league.test.task.dto.favourites.Favourite;
import league.test.task.dto.favourites.PostFavouriteAnswer;
import league.test.task.dto.images.BreedImages;
import league.test.task.endpoints.BreedsEndpoint;
import league.test.task.endpoints.FavouritesEndpoint;
import league.test.task.endpoints.ImagesEndpoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class OurLittleTest extends BaseTest {

    @Lazy
    @Autowired
    private BreedsEndpoint breedsEndpoint;
    @Lazy
    @Autowired
    private ImagesEndpoint imagesEndpoint;
    @Lazy
    @Autowired
    private FavouritesEndpoint favouritesEndpoint;

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

    private String fileName = "report.txt";
    private Path pathToReport;
    private BufferedWriter writer;

    @BeforeAll
    public void beforeALlInOurLittleTest() throws IOException {
        Files.deleteIfExists(get(fileName));
        pathToReport = Files.createFile(get(fileName));
        writer = new BufferedWriter(new FileWriter(pathToReport.toFile(), true));
    }

    @Test
    public void test1() {
        List<Breed> breedList = breedsEndpoint
                                .search(breedText)
                                .getBodyAsListOf(Breed.class);
        id = breedList.get(0).getId();
    }

    @Test
    public void test2() throws IOException {
        List<BreedImages> breedImages = imagesEndpoint.search(id).getBodyAsListOf(BreedImages.class);
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
    }

    @Test
    public void test3() {
        PostFavouriteAnswer favouritesBody = favouritesEndpoint.postFavourites(imageId).getBodyAs(PostFavouriteAnswer.class);
        message = favouritesBody.getMessage();
        assertEquals(message, "SUCCESS");
        favouriteId = favouritesBody.getId();
    }

    @Test
    public void test4() {
        favouriteList = favouritesEndpoint.getFavourites().getBodyAsListOf(Favourite.class);
        ourFavourite = favouriteList.stream().filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (!ourFavourite.isPresent()) fail("Не нашли избранного кота с id " + favouriteId);
        assertEquals(ourFavourite.get().getImage().getUrl(), imageUrl);
    }

    @Test
    public void test5() {
        message = favouritesEndpoint.deleteFavourite(favouriteId).getBodyAs(DeleteFavouriteAnswer.class).getMessage();
        assertEquals(message, "SUCCESS");
    }

    @Test
    public void test6() {
        favouriteList = favouritesEndpoint.getFavourites().getBodyAsListOf(Favourite.class);
        ourFavourite = favouriteList.stream().filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (ourFavourite.isPresent()) fail(format("Изображение с id = %d все еще в избранных", favouriteId));
    }

    @Attachment(value = "report", type = "text/plain", fileExtension = ".txt")
    public byte[] attachReport() throws IOException {
        byte[] array = Files.readAllBytes(pathToReport);
        return array;
    }

}
