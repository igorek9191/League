package task;

import dto.breed.Breed;
import dto.category.Category;
import dto.favourites.DeleteFavouriteAnswer;
import dto.favourites.Favourite;
import dto.favourites.PostFavouriteAnswer;
import dto.images.BreedImages;
import endpoints.Breeds;
import endpoints.Categories;
import endpoints.Favourites;
import endpoints.Images;
import io.qameta.allure.Attachment;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

import static java.lang.String.format;
import static java.nio.file.Paths.get;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

public class OurLittleTest extends BaseTest {

    private String FILE_NAME = "report.txt";
    private Path path;
    private BufferedWriter writer;

    private String breedText = "Scottish Fold";
    private String boxes = "boxes";
    private String id;
    private BreedImages imageBody;
    private String breed_id;
    private String imageId;
    private String imageUrl;
    private String message;
    private Optional<Favourite> ourFavourite;
    private Integer favouriteId;
    private Favourite[] favourites;
    private Category[] categories;
    private Optional<Category> boxesCategory;

    private String baseUrl = "https://api.thecatapi.com/v1/";
    protected RequestSpecification spec;

    @BeforeSuite
    public void beforeSuite() throws IOException {
        Files.deleteIfExists(get(FILE_NAME));

        path = Files.createFile(get(FILE_NAME));
        writer = new BufferedWriter(new FileWriter(path.toFile(), true));

        String apiKey = System.getProperty("api_key");
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(baseUrl)
                .addHeader("x-api-key", apiKey)
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Test
    public void test() throws IOException {
        //1
        Optional<Breed> breed = Arrays.stream(Breeds.search(spec, breedText).as(Breed[].class)).findFirst();
        if (!breed.isPresent()) fail("Нет породы " + breedText);
        id = breed.get().getId();
        //2
        Optional<BreedImages> breedImages = Arrays.stream(Images.search(spec, id).as(BreedImages[].class)).findFirst();
        if (!breedImages.isPresent()) fail("Нет картинок для породы с id = " + id);
        imageBody = breedImages.get();
        breed_id = imageBody.getBreeds().get(0).getId();
        assertEquals(id, breed_id);
        imageId = imageBody.getId();
        imageUrl = imageBody.getUrl();

        writer.append("Breed id: " + breed_id + "\n");
        writer.append("Image id: " + imageId + "\n");
        writer.append("Image URL: " + imageUrl + "\n");
        writer.close();
        //3
        PostFavouriteAnswer favouritesBody = Favourites.postFavourites(spec, imageId).as(PostFavouriteAnswer.class);
        message = favouritesBody.getMessage();
        assertEquals(message, "SUCCESS");
        favouriteId = favouritesBody.getId();
        //4
        favourites = Favourites.getFavourites(spec).as(Favourite[].class);
        ourFavourite = Arrays.stream(favourites).filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (!ourFavourite.isPresent()) fail("Не нашли избранного кота с id " + favouriteId);
        assertEquals(ourFavourite.get().getImage().getUrl(), imageUrl);
        //5
        message = Favourites.deleteFavourite(spec, favouriteId).as(DeleteFavouriteAnswer.class).getMessage();
        assertEquals(message, "SUCCESS");
        //6
        favourites = Favourites.getFavourites(spec).as(Favourite[].class);
        ourFavourite = Arrays.stream(favourites).filter(x -> x.getId().equals(favouriteId)).findFirst();
        if (ourFavourite.isPresent()) fail(format("Изображение с id = %d все еще в избранных", favouriteId));
    }

    @Test
    public void secondTest() {
        categories = Categories.getListOfCategories(spec).as(Category[].class);
        boxesCategory = Arrays.stream(categories).filter(x -> x.getName().equalsIgnoreCase(boxes)).findFirst();
        if (!boxesCategory.isPresent()) fail("Нет категории с названием " + boxes);
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        attachReport();
    }

    @Attachment(value = "report", type = "text/plain", fileExtension = ".txt")
    public byte[] attachReport() throws IOException {
        byte[] array = Files.readAllBytes(path);
        return array;
    }

}
