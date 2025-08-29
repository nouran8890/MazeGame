import java.util.Random;
public class HealthItem extends Item {
    private static final char symbol = 'H';
    private int value;
    
    // Constructor
    public HealthItem(int[] position){
        super(position);  
        Random rand = new Random();
        this.value= rand.nextInt(31)+10; // Value is random between 10 & 40     
    }

    // Getters only  no need for setters because values are set randomly

    public char getSymbol(){
        return symbol;
    }
    public int getValue(){
        return value;
    }
    @Override
    public int[] getPosition(){
        return position;
    }
}
