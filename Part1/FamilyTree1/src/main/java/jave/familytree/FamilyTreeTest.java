package jave.familytree;
import java.util.Scanner;
 
public class FamilyTreeTest {
 
    public static void main(String[] args) {
 
        FamilyTree familyTree = new FamilyTree("James", "Mary");
 
        Scanner scanner = new Scanner(System.in);
 
        int choice = 0;
 
        do {
 
            System.out.println("Menu:");
            System.out.println("1. Add a child");
            System.out.println("2. Display family tree");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
 
            try {
 
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
 
            } catch (Exception e) {
 
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume newline
                continue;
 
            }
 
            switch (choice) {
 
                case 1:
 
                    System.out.print("Enter child's name: ");
                    String childName = scanner.nextLine();
 
                    try {
                        familyTree.addChild(childName);
                        System.out.println("Child added successfully.");
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
 
                    break;
 
                case 2:
                    System.out.println("Family Tree:");
                    System.out.println(familyTree);
                    break;
 
                case 3:
                    System.out.println("Exiting...");
                    break;
 
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
 
            }
 
        } while (choice != 3);
 
        scanner.close();
    }
}