import java.util.InputMismatchException;
import java.util.Scanner;
public class App {
    public static void main(String[] args) throws Exception {
        // Display welcome message and game instructions
        System.out.println("-------------------------------------------------");
        System.out.println("-------------- Welcome to the Maze --------------");
        System.out.println("-------------------------------------------------");
        System.out.println("Monsters are M");
        System.out.println("Health and Power item are H and P ");
        System.out.println("Defeat all monsters to win the game");
        System.out.println("-------------------------------------------------");
        
         // Create a scanner to handle user input
        Scanner input = new Scanner(System.in);

        char move = ' ';  // Stores player's move (D, A, W, S)
        String name ="";  // Stores player's character name
        char firstLetter =' '; // First letter of the player's name, used to represent their character

        // Outer loop to allow restarting the game
        while(true){
            System.out.println("What is your character Name ?");

            while (true){
                name = input.nextLine();

                // Input validation for character name
                if(name.isEmpty()){
                    System.out.println("Name cannot be empty");
                    continue;
                }
                else{
                    // Ensure the first letter of the name is alphabetic
                    firstLetter = name.charAt(0);
                    if(Character.isLetter(firstLetter)){
                        break;
                    }
                    else{
                        System.out.println("Invalid name! First letter must be Alphabetic");
                    }

                }

            }

            // Welcome the player and inform them about their character representation in the game
            System.out.println("==============   Welcome " + name + "   ==============");
            
            firstLetter = Character.toLowerCase(firstLetter); // Convert the first letter to lowercase

            Maze maze = new Maze(firstLetter); // Create a Maze instance, passing the player's character representation
            System.out.println("Your Character will be denoted by ' " + firstLetter + " ' in the game");
            
            // Display player's stats
            maze.playerStat();
            
            // Main game loop
            while(true){
                maze.printMaze();  // Print the current maze
                maze.playerStat(); // Print player's stats

                // Prompt player for their move
                System.out.println("Choose your move");
                System.out.println("    D        |      A        |    W    |      S ");
                System.out.println("  Right ->   |     Left <-   |    Up   |     Down");
                
                // Input validation for player's move
                boolean ValidInput = false;
                while (!ValidInput){
                    try{
                        // Read input, trim spaces, and convert to uppercase
                        String moveInput = input.nextLine().trim().toUpperCase(); 

                        // Ensure the input is not empty
                        if(moveInput.isEmpty()){
                            throw new InputMismatchException("Input cannot be empty. Please enter D, A, W, or S.");
                        }

                        // Ensure the input has exactly one character
                        if (moveInput.length() == 1){
                            move = moveInput.charAt(0); // Get the first character of the input
                        
        
                        // Handle player's move based on input    
                        switch (move) {
                            case 'A':// Move Left 
                                ValidInput = true;
                                if(maze.checkmove('A')){
                                    maze.changePosition('A');
                                }
                                break;
                            case 'D': // Move Right
                                ValidInput = true;
                                if(maze.checkmove('D')){
                                    maze.changePosition('D');
                                }
                                break;
                            case 'W':  // Move Up
                                ValidInput = true;
                                if(maze.checkmove('W')){
                                    maze.changePosition('W');
                                }
                                break;
                            case 'S':   // Move Down
                                ValidInput = true;
                                if(maze.checkmove('S')){
                                    maze.changePosition('S');
                                }
                                break;
                            default:  // Invalid input case
                                System.out.println("Invalid Input! Only Enter D, A, W or S ");
                                break;
                       }
                      } else{
                                System.out.println("Invalid Input! Only Enter a single character (D, A, W, or S).");
                       }
                    } catch(InputMismatchException e){
                            System.out.println(e.getMessage());
                      }
                    
                }
                
                 // Check if the player has won the game
                if(maze.checkVictory()){
                    maze.playerStat();
                    System.out.println("-----------------------------------------------------");
                    System.out.println("    -------------- Congratulations --------------    ");
                    System.out.println("    ---------------------------------------------    ");
                    System.out.println("    ------------------ You WON ------------------    ");
                    System.out.println("-----------------------------------------------------");
                    break;
                }

                // Check if the player has lost the game
                if(maze.checkLoss()){
                    maze.playerStat();
                    System.out.println("************************");
                    System.out.println("*****  You Lost :( *****");
                    System.out.println("************************");
                    break;
                }
            }

            // After the game ends, ask if the player wants to play again
            while (true){
                System.out.println("Do you want to play again ?");
                System.out.println("Yes --- No");
                String answer = input.nextLine().trim().toLowerCase();
                if(answer.equals("yes")){
                    break; // back to the outer loop and Restart the game
                }
                else if (answer.equals("no")){
                    System.out.println("Exiting Game ....");
                    input.close();
                    System.exit(0);  // Exit the program
                }
                else{

                    System.out.println("Invalid answer!   Enter 'Yes'  or 'No'.");
                    
                }
            }
            
        }
        
    }
}
    
