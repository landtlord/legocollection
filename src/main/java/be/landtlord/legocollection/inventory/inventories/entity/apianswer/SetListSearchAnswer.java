package be.landtlord.legocollection.inventory.inventories.entity.apianswer;

import be.landtlord.legocollection.inventory.sets.entity.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class SetListSearchAnswer {
    List<Set> results;
}
