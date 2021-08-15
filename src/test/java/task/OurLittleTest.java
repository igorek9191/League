package task;

import dto.breed.Breed;
import dto.favourites.DeleteFavouriteAnswer;
import dto.favourites.Favourite;
import dto.favourites.PostFavouriteAnswer;
import dto.images.BreedImages;
import endpoints.BreedsEndpoint;
import endpoints.FavouritesEndpoint;
import endpoints.ImagesEndpoint;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    private Path path;
    private BufferedWriter writer;

    @BeforeAll
    public void beforeALlInOurLittleTest() throws IOException {
        prepareReport();
    }

    private void prepareReport() throws IOException {
        Files.deleteIfExists(get(fileName));
        path = Files.createFile(get(fileName));
        writer = new BufferedWriter(new FileWriter(path.toFile(), true));
    }

    @Test
    public void test() throws IOException {
        //1
        List<Breed> breedBody = breedsEndpoint.search(spec, breedText).getBodyAsListOf(Breed.class);
        id = breedBody.get(0).getId();
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

    @AfterAll
    public void afterSuite() throws IOException {
        attachReport();
    }

    @Attachment(value = "report", type = "text/plain", fileExtension = ".txt")
    public byte[] attachReport() throws IOException {
        byte[] array = Files.readAllBytes(path);
        return array;
    }

}
