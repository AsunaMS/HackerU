import java.util.Scanner;

public class IO {
    public static String inputString(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.next();
    }

    public static boolean inputBoolean(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message + " Please enter YES/NO");
        String answer = scanner.next().toLowerCase();
        switch (answer) {
            case "yes":
                return true;
            case "no":
                return false;
        }
        return false;
    }

    public static int inputInt(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextInt();
    }
    public static double inputDouble(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(message);
        return scanner.nextDouble();
    }
}
