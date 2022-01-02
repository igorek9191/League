package league.test.task.dto.favourites;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class Favourite {
    @JsonProperty("id")
    private Integer id;
    private String user_id;
    private String image_id;
    private Object sub_id;
    private Date created_at;
    private Image image;
}
