public class Main {

    /**
     * הארה:
     * כשאינטליגיי מעירה לנו בכתום על שורות מסוימות, לרוב
     * הסיבה היא שיש דרך מקוצרת לבצע את אותה פעולה
     * אין צורך לתקן לפעולה המקוצרת, אם עדייו לא נלמדה
     * את מי שמעניין לחקור,ניתן למצוא מידע לגבי ביטויי למבדה בקישור הבא
     * https://www.w3schools.com/java/java_lambda.asp
     */
    public static void main(String[] args) {
        ShopList shopList = new ShopList();
        int choice = 0;
        System.out.println("Welcome to our Shop!");
        while (choice != 6) {
            choice = IO.inputInt("1) Add a new product\n" +
                    "2) Remove a product\n" +
                    "3) Search lowest price product\n" +
                    "4) Search product by name\n" +
                    "5) Show all products\n" +
                    "6) Exit Shop");

            switch (choice) {
                case 1:
                    shopList.addProduct();
                    break;
                case 2:
                    shopList.removeProduct();
                    break;
                case 3:
                    shopList.searchLowCostProduct();
                    break;
                case 4:
                    shopList.searchProduct();
                    break;
                case 5:
                    shopList.showAllProducts();
                    break;
                case 6:
                    System.out.println("Bye bye! Hoping to see you again soon!");
                    break;
            }

        }

    }


}
