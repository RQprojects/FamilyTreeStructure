package jave.familytree;

import java.util.Scanner;
import java.util.InputMismatchException;

public class FamilyTreeTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // No need to prompt for the ancestor's name
        String ancestorName = "James"; // Set the ancestor's name directly

        // Create a FamilyTree instance with the ancestor's name
        FamilyTree familyTree = new FamilyTree(ancestorName);       

        boolean running = true;
        while (running) {
            System.out.println("1. Add a partner");
            System.out.println("2. Add a child");
            System.out.println("3. Display the whole family");
            System.out.println("4. Display a specific family member");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");

            // Read the user's choice as a string
            String choiceStr = scanner.nextLine();

            // Parse the choice into an integer
            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    addPartner(scanner, familyTree);
                    break;
                case 2:
                    addChild(scanner, familyTree);
                    break;
                case 3:
                    displayWholeFamily(familyTree);
                    break;
                case 4:
                    displaySpecificMember(scanner, familyTree);
                    break;
                case 5:
                    System.out.println("Exiting program...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        scanner.close();
    }

    private static void addPartner(Scanner scanner, FamilyTree familyTree) {
        System.out.println(familyTree.toString());
        System.out.print("Enter the identifier of the family member to add a partner to: ");
        int identifier;
        try {
            identifier = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer identifier.");
            scanner.nextLine(); // Consume invalid input
            return;
        }
        scanner.nextLine(); // Consume newline character
        System.out.print("Enter the name of the partner: ");
        String partnerName = scanner.nextLine();
        try {
            familyTree.addPartner(identifier, partnerName);
            System.out.println("Partner added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addChild(Scanner scanner, FamilyTree familyTree) {
        System.out.println(familyTree.toString());
        System.out.print("Enter the identifier of the parent to add a child to: ");
        String parentIdentifierStr = scanner.nextLine(); // Read as string first
        int parentIdentifier;
        try {
            parentIdentifier = Integer.parseInt(parentIdentifierStr); // Parse into integer
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid integer identifier.");
            return;
        }
        System.out.print("Enter the name of the child: ");
        String childName = scanner.nextLine();
        try {
            familyTree.addChild(parentIdentifier, childName);
            System.out.println("Child added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayWholeFamily(FamilyTree familyTree) {
        System.out.println(familyTree.toString());
    }

    private static void displaySpecificMember(Scanner scanner, FamilyTree familyTree) {
        System.out.print("Enter the identifier of the family member to display: ");
        int identifier;
        try {
            identifier = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer identifier.");
            scanner.nextLine(); // Consume invalid input
            return;
        }
        scanner.nextLine(); // Consume newline character
        String details = familyTree.getFamilyMemberDetails(identifier);
        System.out.println(details);
    }
}
