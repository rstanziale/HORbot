package beans.facets.values;

/**
 * Define a generic food element for Physical States facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class PhysicalsFood {
    private int caloriesIn;
    private int calories;
    private int carbs;
    private int fat;
    private int fiber;
    private int protein;
    private int sodium;
    private int water;

    public int getCaloriesIn() {
        return caloriesIn;
    }

    public void setCaloriesIn(int caloriesIn) {
        this.caloriesIn = caloriesIn;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getFiber() {
        return fiber;
    }

    public void setFiber(int fiber) {
        this.fiber = fiber;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }
}
