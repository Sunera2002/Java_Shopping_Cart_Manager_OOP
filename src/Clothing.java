public class Clothing extends Product{
    private String size;
    private String color;

    public Clothing(String productId, String productName, int availableItems, double priceofitem, String size, String color) {
        super(productId, productName, availableItems, priceofitem);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
