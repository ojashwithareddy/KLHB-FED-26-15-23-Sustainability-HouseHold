import java.util.Scanner;

public class WaterUsageBillingMonitor {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("=== Household Water-Usage & Billing Monitor ===");

        System.out.print("Enter household name: ");
        String household = sc.nextLine();

        System.out.print("Enter previous meter reading (litres): ");
        double previousReading = sc.nextDouble();

        System.out.print("Enter current meter reading (litres): ");
        double currentReading = sc.nextDouble();

        System.out.print("Enter price per litre: ");
        double pricePerLitre = sc.nextDouble();

        double waterUsed = currentReading - previousReading;
        double bill = waterUsed * pricePerLitre;

        System.out.println("\n--- Water Usage Report ---");
        System.out.println("Household: " + household);
        System.out.println("Water Used: " + waterUsed + " litres");
        System.out.println("Price per Litre: " + pricePerLitre);
        System.out.println("Total Water Bill: ₹" + bill);

        if (waterUsed > 1000) {
            System.out.println("Alert: High water consumption!");
        } else {
            System.out.println("Water consumption is within the normal range.");
        }

        sc.close();
    }
}
