import java.util.Random;

public class PowerItem extends Item{
    private static final char symbol = 'P';
    private int value;

    // Constructor
    public PowerItem(int[] position){
        super(position);
        Random rand = new Random();
        this.value= rand.nextInt(31)+10;  // Value is random between 10 & 40     
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
