public class Electronics extends Product{
    private String brand;
    private double warranty;

    public Electronics(String productId, String productName, int availableItems, double priceofproduct, String brand, Double warranty){
        super(productId, productName, availableItems, priceofproduct);
        this.brand = brand;
        this.warranty = warranty;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
}
