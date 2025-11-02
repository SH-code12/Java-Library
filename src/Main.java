import ApplicationLayer.Controller.GAController;

import ApplicationLayer.Controller.FuzzyController;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose System to Run:");
        System.out.println("1. Genetic Algorithm");
        System.out.println("2. Fuzzy Logic");
        int choice = input.nextInt();

        if (choice == 1) GAController.main(null);
        else if (choice == 2) FuzzyController.main(null);
        else System.out.println("Invalid choice.");
    }
}

