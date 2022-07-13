import java.util.Map;
import java.util.HashMap;
import java.awt.Graphics2D;

public class CraftingRecipe {

    private static final Map<Class<? extends Item>, CraftingRecipe> recipes = new HashMap<>();

    public static int RECIPE_SIZE = 3;

    private Class<? extends Item>[][] ingredients;
    private Class<? extends Item> result;

    private Item dummyResult;

    public static void loadCraftingRecipes() {
        new CraftingRecipe(new Class[][] {
                { null, null, null },
                { null, Items.Wood.class, null },
                { null, null, null }
        }, Items.WoodenPlank.class);

        new CraftingRecipe(new Class[][] {
                { null, Items.IronBlock.class, null },
                { null, Items.IronBlock.class, null },
                { null, Items.WoodenPlank.class, null }
        }, Items.IronSword.class);
    }

    @SuppressWarnings("rawtypes")
    public CraftingRecipe(Class[][] ingredients, Class<? extends Item> result) {

        @SuppressWarnings("unchecked")
        Class<? extends Item>[][] i = (Class<? extends Item>[][]) ingredients;

        assert ingredients.length == RECIPE_SIZE;
        assert ingredients[0].length == RECIPE_SIZE;

        this.ingredients = i;
        this.result = result;

        try {
            dummyResult = result.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            dummyResult = null;
            e.printStackTrace();
        }

        recipes.put(result, this);
    }

    public boolean matches(Item[][] ingredients) {
        assert ingredients.length == RECIPE_SIZE;
        assert ingredients[0].length == RECIPE_SIZE;

        for (int i = 0; i < RECIPE_SIZE; i++) {
            for (int j = 0; j < RECIPE_SIZE; j++) {
                if (this.ingredients[i][j] != null && ingredients[i][j] != null) {
                    if (!this.ingredients[i][j].equals(ingredients[i][j].getClass())) {
                        return false;
                    }
                } else if (this.ingredients[i][j] != null || ingredients[i][j] != null) {
                    return false;
                }
            }
        }

        return true;
    }

    public Item createResult() {
        try {
            return result.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void drawResult(Graphics2D g2d, int x, int y) {
        dummyResult.draw(g2d, x, y);
    }

    public static CraftingRecipe getMatch(Item[][] ingredients) {
        assert ingredients.length == RECIPE_SIZE;
        assert ingredients[0].length == RECIPE_SIZE;

        for (CraftingRecipe recipe : recipes.values()) {
            if (recipe.matches(ingredients)) {
                return recipe;
            }
        }

        return null;
    }
}