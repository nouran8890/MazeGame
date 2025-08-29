public class Item {
    protected int[] position;
    protected int value;  // will be set to random value in children classes

    // Constructor
    public Item(int[] position){
        this.position = position;   
    }

    // Getters only no need for setters because values are set randomly
    
    public int[] getPosition() {
        return this.position;
    }

    public int getValue() {
        return this.value;
    }


}
