import java.util.Random;
import java.util.ArrayList;
import java.util.function.Function;
public class Maze {
    private ArrayList<ArrayList<Character>> maze = new ArrayList<>();
    private Characters C;
    private Random rand = new Random();
    private int nMonsters = rand.nextInt(25)+1; // Generate a random number of monsters from 1 to 25
    private ArrayList<Monster> monsters = new ArrayList<>(nMonsters); // will store the random no. of monsters here
    private int nHealth = rand.nextInt(15)+1; 
    private ArrayList<HealthItem> HItems = new ArrayList<>(nHealth);
    private int nPower = rand.nextInt(15)+1;
    private ArrayList<PowerItem> PItems = new ArrayList<>(nPower);


    // Constructor initializes the maze, places player at (0,0), and randomly places monsters, health, and power items
    public Maze(char playerFirstLetter){
        // initialize the player character 
        C = new Characters(playerFirstLetter); 
        // Create a 10x10 maze grid
        for(int i=0; i<10; i++){ 
            maze.add(new ArrayList<>(10));
        }
        // Fill the maze grid with '*' to represent empty spaces
        for(ArrayList<Character> x : maze){ 
            for(int i=0; i<10;i++){
                x.add('*');
            }
        }
        // function to place monster and items at random position
        placeThings(nMonsters,'M',monsters, position -> new Monster(position)); 
        placeThings(nHealth, 'H',HItems, position -> new HealthItem(position)); 
        placeThings(nPower, 'P',PItems,position -> new PowerItem(position)); 

        // Place the player at position (0,0)
        maze.get(0).set(0,C.getSymbol()); 
    }

    // Print the current state of the maze
    public void printMaze(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                System.out.print(maze.get(i).get(j)+ "  ");
            }
            System.out.println();
        }
    }
    // Display player's current health and power statistics
    public void playerStat(){
        System.out.println("Your Health = " + C.getHealth());
        System.out.println("Your Power = " + C.getPower());
    }


    /**
     * Places monsters, health items, or power items at random positions in the maze.
     * @param nThings The number of objects to place (monsters, health, or power items)
     * @param symbol The symbol representing the object (M for monsters, H for health, P for power)
     * @param things The list to store the created objects (monsters, health items, or power items)
     * @param constructor Lambda function to create the object (Monster, HealthItem, or PowerItem)
     */

    public <T> void placeThings(int nThings, char symbol, ArrayList<T> things, Function<int[] ,T> constructor){
        for(int i=0;i<nThings;i++){

            // generate 2 random numbers from 0 to 9
            int randRow = rand.nextInt(10); 
            int randCol = rand.nextInt(10); 
            maze.get(randRow).set(randCol, symbol);
            
            //take these numbers as indexes for the Objects
            int[] position = new int[] {randRow, randCol};


            // Use the lambda function to create the object and add it to the list
            things.add(constructor.apply(position));
        }
    }
    // find the current postion of the character on the maze grid to check if the move is valid
    public int[] findPosition(){
        for(int i=0; i<10; i++){
            for(int j=0; j<10; j++){
                if (maze.get(i).get(j)==C.getSymbol()){
                    return new int[] {i,j} ;
                }
            }
        }
        return null;
    }

    // Check if the player's move is within valid bounds of the maze
    public boolean checkmove(char move){   // Right D | Left A | up W | down S
        int[] position = findPosition();
        switch (move) {
            case 'D':  // Move Right
                return (position[1]<9);// return true if the player isn't in the last column
                
            case 'A': // Move Left
                return (position[1]>0);// returns true if the player isn't in the first column
                
            case 'W': // Move Up
                return (position[0]>0);// returns true if the player isn't in the first Row
                
            case 'S': // Move Down
                return (position[0]<9);// returns true if the player isn't in the last Row

            default:
                return false;
        }
    }

    // Change the player's position based on the move input and handle encounters with monsters, health items, or power items
    public void changePosition(char move){
        int[] pos = findPosition();
        maze.get(pos[0]).set(pos[1], '*'); //Remove Character from old position and replace with *
        switch (move) { 
            case 'D': // Right 
                pos[1]++;  // move to next cell  
                break;
            case 'A': // Left
                pos[1]--; // move to previous cell
                break;
            case 'W': // Up
                pos[0]--;
                break;
            case 'S': // Down
                pos[0]++;
                break;
            default:
                break;
        }

        // Check if new position has M/H/P else '*'
        char cell = maze.get(pos[0]).get(pos[1]);

        // Check if new position contains a monster (M), initiate a battle
        if(cell == 'M'){ 
            for(Monster monster:monsters){
                // look for this found monster in monsters arraylist
                if(monster.getPosition()[0] == pos[0] && monster.getPosition()[1] == pos[1]){
                  
                    // fight
                    if(C.getPower() > monster.getPower()){ // if player's power is greater, the monster dies 
                        C.setPower(C.getPower() -monster.getPower() ); // player's power decreases
                    }
                    else{ // if monster's power in greater then player's power decreases
                        int powerDiff = monster.getPower() - C.getPower();
                        C.setPower(0);
                        C.setHealth(C.getHealth()-powerDiff); // if player's power reached 0 then player's health starts decreasing
                        if(C.getHealth() <=0){
                            C.setHealth(0);
                            C.setLost(true); // check Loss funtion will return true
                        }
                    }
                    monsters.remove(monster); // remove monster from the grid
                    maze.get(pos[0]).set(pos[1],C.getSymbol()); // replace it with player's character
                    break;
                }
            }
            
            

        }
        // If player encounters a health item, increase player's health
        else if (cell == 'H'){
            for(HealthItem hItem : HItems){  // search for the found health item in the list of health items
                if (hItem.getPosition()[0]==pos[0] && hItem.getPosition()[1]==pos[1] ){ 

                    C.setHealth(C.getHealth() + hItem.getValue()); // add it's value to players health
                    if (C.getHealth() > 100){  // player's health value can't exceed 100
                        C.setHealth(100);
                    }
                    HItems.remove(hItem); // remove the used health item and change player's position
                    maze.get(pos[0]).set(pos[1],C.getSymbol());
                    break;
            }
            
            }}
        // If player encounters a power item, increase player's power
        else if (cell == 'P'){
            for(PowerItem pItem : PItems){ // search for the found power item in the list of power items
                if (pItem.getPosition()[0] == pos[0] && pItem.getPosition()[1] == pos[1]){
                   
                    int powerValue = pItem.getValue(); 
                    C.setPower(C.getPower() + powerValue);  // add it's value to players power
      
                    if(C.getPower() > 100){  // player's power value can't exceed 100
                        C.setPower(100);
                    }

                    PItems.remove(pItem); // remove the used power item and change player's position
         
                    maze.get(pos[0]).set(pos[1],C.getSymbol());
                    break;
                }
            }
        }
            
        // if no items or monsters
        else{
            maze.get(pos[0]).set(pos[1],C.getSymbol()); // Add character to new position
        }
    }

    // Check if all monsters have been defeated, indicating a victory
    public boolean checkVictory(){ // if there is at least one monster then the game keeps going
        for(int i=0;i<10;i++){
            for (int j=0;j<10;j++){
                if(maze.get(i).get(j) == 'M'){
                return false;
                }
            }
        }
        return true; // if no monsters found --> Victory
    }

    // Check if the player's health has reached zero, indicating a loss
    public boolean checkLoss(){ 
        if(C.getLoss() == true){
            return true; // 
        }
        return false;
    }

}

    

    

