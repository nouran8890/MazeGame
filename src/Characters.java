public class Characters {
   private int[] position;
   private int power = 100;
   private int health = 100; 
   private char symbol;
   private boolean loss = false;
    

    // Constructor
    public Characters( char symbol){
        //this.position = position;
        this.symbol = symbol;
    } 

    // Setters and Getters
    public int[] getPosition() {
        return position;
    }
    public int getPower() {
        return power;
    }
    public int getHealth(){
        return health;
    }
    public char getSymbol(){
        return symbol;
    }
    public boolean getLoss(){  
        return loss;
    }
    public void setPower(int power){
        this.power = power;
    }
    public void setSymbol(char symbol){
        this.symbol = symbol;
    }
    public void setHealth(int health){
        this.health = health;
    }
    public void setLost(boolean loss){
        this.loss = loss;
    }
    
}
