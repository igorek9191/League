package league.test.task.dto.favourites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Data
public class Favourites {
    private List<Favourite> favourites;

    @JsonIgnore
    public Favourite findFavourite(int id) {
        return this.favourites.stream().filter(x -> x.getId().equals(id)).findFirst().orElseThrow(
                () -> new NoSuchElementException("No favourite with id = " + id));
    }

    @JsonIgnore
    public Optional<Favourite> findOptionalFavourite(int id) {
        return this.favourites.stream().filter(x -> x.getId().equals(id)).findFirst();
    }

}
