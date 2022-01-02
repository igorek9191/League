package league.test.task.dto.favourites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Image {
    @JsonProperty("id")
    private String id;
    private String url;
}
