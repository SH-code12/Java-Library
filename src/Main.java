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

        switch (choice){
            case 1:
                GAController.main(null);
                break;
            case 2:
                FuzzyController.main(null);
                break;
            default:
                System.out.println("Invalid choice.");
                break;


        }
    }
}

