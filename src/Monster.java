import java.util.Random;
public class Monster {
    int[] position;
    int power;
    private static final char symbol = 'M';

    // constructor
    public Monster(int[] position){
        Random rand = new Random();
        power = rand.nextInt(91)+10;  // Value is random between 10 & 100    
        this.position = position;
    
    }

    // Getters only  no need for setters because values are set randomly
    public int[] getPosition() {
        return position;
    }
    public char getSymbol(){
            return symbol;
        }
    public int getPower() {
        return power;
    }
}
