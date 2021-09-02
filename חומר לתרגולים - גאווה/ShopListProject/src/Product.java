public class Product {

    // מאפיינים פרטיים
    // כדי לגשת נוסיף getters & setters

    private String title;
    private boolean isVegan;
    private boolean isDiary;
    private double pricePerKg;
    private double kg;

    Product() {
        this.title = IO.inputString("Enter product's title: ");
        this.isVegan = IO.inputBoolean("Product is Vegan: ");
        if (isVegan) {
            this.isDiary = false;
        } else {
            this.isDiary = IO.inputBoolean("Product is Diary: ");
        }
        this.pricePerKg = IO.inputDouble("Enter price per kg: ");
        this.kg = IO.inputDouble("Enter product's kg: ");
    }

    public double getTotalPrice() {
        return this.kg * this.pricePerKg;
    }

    public String getTitle() {
        return title;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public double getKg() {
        return kg;
    }

    public void setKg(double kg) {
        this.kg = kg;
    }


    public boolean isDiary() {
        return isDiary;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public void setVegan(boolean vegan) {
        isVegan = vegan;
    }

    public void setPricePerKg(double pricePerKg) {
        this.pricePerKg = pricePerKg;
    }

    public void setDiary(boolean diary) {
        isDiary = diary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Title: " + title + '\n' +
                "isVegan: " + isVegan + '\n' +
                "is Diary: " + isDiary + '\n' +
                "Price per KG: " + pricePerKg + '\n' +
                "KGS: " + kg + '\n' +
                "Total Price: " + getTotalPrice() + '\n';
    }
}
