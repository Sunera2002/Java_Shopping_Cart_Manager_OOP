import java.io.Serializable;
public abstract class Product implements Serializable{
    private String productId;
    private String productName;
    private int availableItems;
    private double priceofproduct;

    protected Product(String productId, String productName, int avaiableItems, double priceofproduct){
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.priceofproduct = priceofproduct;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId){
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public int getAvailableItems(){
        return availableItems;
    }
    public void setAvailableItems(int availableItems){
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return priceofproduct;
    }

    public void setPrice(double priceofproduct){
        this.priceofproduct = priceofproduct;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", availableItems=" + availableItems +
                ", price=" + priceofproduct +
                '}';
    }
}
