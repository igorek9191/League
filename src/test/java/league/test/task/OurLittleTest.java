package league.test.task;

import io.qameta.allure.Attachment;
import league.test.task.dto.breed.Breed;
import league.test.task.dto.favourites.Favourite;
import league.test.task.dto.favourites.Favourites;
import league.test.task.dto.favourites.PostFavouriteResponce;
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

@TestMethodOrder(MethodOrderer.MethodName.class)
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

    private List<Breed> breedList;
    private List<BreedImages> breedImages;
    private BreedImages imageBody;
    private Favourite ourFavourite;
    private Favourites favourites;

    private String breedText = "Scottish Fold";
    private String breedId;
    private String imageId;
    private String imageUrl;
    private String message;
    private String id;
    private Integer favouriteId;

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
        breedList = breedsEndpoint.search(breedText);
        id = breedList.get(0).getId();
    }

    @Test
    public void test2() throws IOException {
        breedImages = imagesEndpoint.search(id);
        if (breedImages.size() == 0) fail("There is no pictures for breed with id = " + id);

        imageBody = breedImages.get(0);
        breedId = imageBody.getBreeds().get(0).getId();
        assertEquals(id, breedId);
        imageId = imageBody.getId();
        imageUrl = imageBody.getUrl();

        writer.append("Breed id: " + breedId + "\n");
        writer.append("Image id: " + imageId + "\n");
        writer.append("Image URL: " + imageUrl + "\n");
        writer.close();
        attachReport();
    }

    @Test
    public void test3() {
        PostFavouriteResponce favouritesBody = favouritesEndpoint.postFavourites(imageId);
        message = favouritesBody.getMessage();
        assertEquals(message, "SUCCESS");
        favouriteId = favouritesBody.getId();
    }

    @Test
    public void test4() {
        favourites = favouritesEndpoint.getFavourites();
        ourFavourite = favourites.findFavourite(favouriteId);
        assertEquals(ourFavourite.getImage().getUrl(), imageUrl);
    }

    @Test
    public void test5() {
        message = favouritesEndpoint.deleteFavourite(favouriteId).getMessage();
        assertEquals(message, "SUCCESS");
    }

    @Test
    public void test6() {
        favourites = favouritesEndpoint.getFavourites();
        Optional<Favourite> ourFavourite = favourites.findOptionalFavourite(favouriteId);
        if (ourFavourite.isPresent()) fail(format("Picture with id = %d is still in favourites", favouriteId));
    }

    @Attachment(value = "report", type = "text/plain", fileExtension = ".txt")
    public byte[] attachReport() throws IOException {
        byte[] array = Files.readAllBytes(pathToReport);
        return array;
    }
}