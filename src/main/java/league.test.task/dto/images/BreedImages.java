package league.test.task.dto.images;

import league.test.task.dto.breed.Breed;
import lombok.Data;

import java.util.List;

@Data
public class BreedImages {
    private List<Breed> breeds;
    private int height;
    private String id;
    private String url;
    private int width;
}
