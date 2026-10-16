
import java.util.*;

interface Billable {
    double generateBill(double litres);
}

class Household {
    private String name;

    Household(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println("Household Name: " + name);
    }
}

class ResidentialHousehold extends Household implements Billable {
    ResidentialHousehold(String name) {
        super(name);
    }

    @Override
    public double generateBill(double litres) {
        return (litres / 1000.0) * 25;
    }
}

class CommercialHousehold extends Household implements Billable {
    CommercialHousehold(String name) {
        super(name);
    }

    @Override
    public double generateBill(double litres) {
        return (litres / 1000.0) * 40;
    }
}

class WaterUsage<T extends Number> {
    public double calculateTotal(List<T> readings) {
        double total = 0;

        for (T reading : readings) {
            total += reading.doubleValue();
        }

        return total;
    }
}

class Pair<K, V> {
    K key;
    V value;

    Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    void display() {
        System.out.println(key + ": " + value);
    }
}

public class WaterBillingMonitor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== HOUSEHOLD WATER MONITOR ===");

        System.out.print("Enter household name: ");
        String name = sc.nextLine();

        System.out.println("1. Residential");
        System.out.println("2. Commercial");
        System.out.print("Select household type: ");

        // Accept both numbers and words
        String choice = sc.nextLine().trim();
        int type;

        if (choice.equals("1")
                || choice.equalsIgnoreCase("Residential")) {
            type = 1;
        } else if (choice.equals("2")
                || choice.equalsIgnoreCase("Commercial")) {
            type = 2;
        } else {
            System.out.println("Invalid household type.");
            sc.close();
            return;
        }

        Household household;
        Billable billable;

        if (type == 1) {
            household = new ResidentialHousehold(name);
            billable = (Billable) household;
        } else {
            household = new CommercialHousehold(name);
            billable = (Billable) household;
        }

        System.out.print("Enter number of days: ");

        if (!sc.hasNextInt()) {
            System.out.println("Please enter a valid whole number of days.");
            sc.close();
            return;
        }

        int days = sc.nextInt();

        if (days <= 0) {
            System.out.println("Number of days must be positive.");
            sc.close();
            return;
        }

        double[][] usage = new double[days][2];
        ArrayList<Double> dailyUsage = new ArrayList<>();
        HashMap<Integer, Double> usageMap = new HashMap<>();

        for (int i = 0; i < days; i++) {
            System.out.println("\nDay " + (i + 1));

            System.out.print("Morning usage (litres): ");

            if (!sc.hasNextDouble()) {
                System.out.println("Please enter a valid number.");
                sc.close();
                return;
            }
            usage[i][0] = sc.nextDouble();

            System.out.print("Evening usage (litres): ");

            if (!sc.hasNextDouble()) {
                System.out.println("Please enter a valid number.");
                sc.close();
                return;
            }
            usage[i][1] = sc.nextDouble();

            if (usage[i][0] < 0 || usage[i][1] < 0) {
                System.out.println("Usage cannot be negative.");
                sc.close();
                return;
            }

            double daily = usage[i][0] + usage[i][1];

            dailyUsage.add(daily);
            usageMap.put(i + 1, daily);
        }

        WaterUsage<Double> calculator = new WaterUsage<>();
        double total = calculator.calculateTotal(dailyUsage);

        double average = dailyUsage.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double peak = dailyUsage.get(0);
        int peakDay = 1;

        for (int i = 0; i < dailyUsage.size(); i++) {
            if (dailyUsage.get(i) > peak) {
                peak = dailyUsage.get(i);
                peakDay = i + 1;
            }
        }

        double bill = billable.generateBill(total);

        System.out.println("\n=== WATER USAGE REPORT ===");
        household.display();

        System.out.println("Household Type: "
                + (type == 1 ? "Residential" : "Commercial"));

        System.out.printf("Total Usage: %.2f litres%n", total);
        System.out.printf("Average Daily Usage: %.2f litres%n", average);
        System.out.printf("Peak Usage: %.2f litres%n", peak);
        System.out.println("Peak Usage Day: " + peakDay);
        System.out.printf("Total Water Bill: Rs. %.2f%n", bill);

        System.out.println("\nDaily Usage Details:");

        for (int i = 0; i < dailyUsage.size(); i++) {
            System.out.printf("Day %d: %.2f litres%n",
                    i + 1, usageMap.get(i + 1));
        }

        Pair<String, Double> billDetails =
                new Pair<>("Final Bill", bill);

        billDetails.display();

        sc.close();
    }
}
