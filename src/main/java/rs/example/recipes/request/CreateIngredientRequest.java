package rs.example.recipes.request;

import lombok.Data;
import rs.example.recipes.model.UnitOfMeasure;

import javax.validation.constraints.NotNull;

@Data
public class CreateIngredientRequest {

    @NotNull
    private String name;
    private String description;
    private UnitOfMeasure uom = UnitOfMeasure.G;

}
