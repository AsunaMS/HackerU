import java.util.ArrayList;

public class ShopList {
    private ArrayList<Product> productList = new ArrayList<>();

    /**
     * שיטה להוספת מוצר חדש
     */
    public void addProduct() {
        // מופע חדש של מוצר, מפעיל שורה של קלטים מהמשתמש
        Product p = new Product();
        // נוסיף לרשימה את המוצר שבנינו
        productList.add(p);
    }

    /**
     * שיטה למחיקת מוצר לפי שם
     */
    public void removeProduct() {
        // נקלוט שם של מוצר מהמשתמש
        String toRemove = IO.inputString("Enter product name to remove");
        // נחפש את המוצר ברשימה לפי שם
        // במידה ומצאנו מוצר מתאים, נמחק מהרשימה
        for (Product product : productList) {
            if (product.getTitle().equals(toRemove)) {
                productList.remove(product);
            }
        }
    }

    /**
     * שיטה להצגת כל המוצרים
     */
    public void showAllProducts() {
        System.out.println("All Products: ");
        // נדפיס את כל המוצרים ברשימה
        for (Product product : productList) {
            System.out.println(product);
        }
    }

    /**
     * שיטה לחיפוש מוצר לפי שם
     */
    public void searchProduct() {
        // נקלוט שם של מוצר מהמשתמש
        String toSearch = IO.inputString("Enter product name to search: ");
        // נחפש ברשימה מוצר עם השם שקלטנו מהמשתמש
        // במידה ומצאנו מוצר מתאים, נדפיס אותו ונצא מהשיטה
        for (Product product : productList) {
            if (product.getTitle().equals(toSearch)) {
                System.out.println("Found product!" + product);
                return;
            }
        }
        // אם הגענו לכאן, לא מצאנו מוצר מתאים, נעדכן בהדפסה
        System.out.println("No product found with the name: " + toSearch);
    }

    /**
     * שיטה המחפשת את המוצר עם המחיר לקילו הנמוך ביותר
     */
    public void searchLowCostProduct() {
        // אם איו מוצרים ברשימה נדפיס שאין ונצא מהשיטה
        if (productList.size() < 1) {
            System.out.println("No products...");
            return;
        }
        // אם יש ברשימה לפחות מוצר אחד
        // אז נתחיל מהמוצר הראשון ברשימה כמוצר בעל המחיר הנמוך ביותר
        Product low = productList.get(0);
        // נחפש מוצר אחר ברשימה שמחירו קטן מהמחיר האחרוו
        // במידה ומצאנו כזה מוצר, נחליף את low בהתאם
        for (Product product : productList) {
            if (product.getPricePerKg() < low.getPricePerKg()) {
                low = product;
            }
        }
        // נדפיס את המוצר
        System.out.println("Lowest price product: " + low);
    }


}
