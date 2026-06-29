import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.1 -- 29-07-2017
 */
public class MyDodo extends Dodo
{
    /* ATTRIBUTE DECLARATIONS: */
    private int myNrOfStepsTaken;
           
    public MyDodo() {
        super( EAST );
        /* INITIALISATION OF ATTRIBUTES: */
        myNrOfStepsTaken = 0;
    }

    /* METHODS OF THE CLASS: */

    public void act() {
    }

    /**
     * Move one cell forward in the current direction.
     * 
     * <P> Initial: Dodo is somewhere in the world
     * <P> Final: If possible, Dodo has moved forward one cell
     *
     */
    public void move() {
        if ( canMove() ) {
            step();
        } else {
            showError( "I'm stuck!" );
        }
    }

    /**
     * Test if Dodo can move forward, 
     * i.e. there are no obstructions or end of world in the cell in front of her.
     * 
     * <p> Initial:   Dodo is somewhere in the world
     * <p> Final:     Same as initial situation
     * 
     * @return  boolean true if Dodo can move (thus, no obstructions ahead)
     *                  false if Dodo can't move
     *                      there is an obstruction or end of world ahead
     */
    public boolean canMove() {
        if ( borderAhead() || fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Move given number of cells forward in the current direction.
     * 
     * <p> Initial:   
     * <p> Final:  
     * 
     * @param   int distance: the number of steps made
     */
    public void jump( int distance ) {
        int nrStepsTaken = 0;               // set counter to 0
        while ( nrStepsTaken < distance ) { // check if more steps must be taken  
            move();                         // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
    * Places all the Egg objects in the world in a list.
    * 
    * @return List of Egg objects in the world
    */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }
    
      public List<BlueEgg> getListOfBlueEggsInWorld() {
        return getWorld().getObjects(BlueEgg.class);
    }
    
      public List<GoldenEgg> getListOfGoldenEggsInWorld() {
        return getWorld().getObjects(GoldenEgg.class);
    }
    
    public List<Integer> createListOfNumbers() {
        return new ArrayList<> (Arrays.asList( 2, 43, 7, -5, 12, 7 ));
    }
    
    /**
     * Method for praciticing with lists.
     */
    public void practiceWithLists( ){
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("First element: " + listOfNumbers.get(1) ); 
    }

    public void practiceWithListsOfSurpriseEgss( ){
        List<SurpriseEgg>  listOfEgss = SurpriseEgg.generateListOfSurpriseEggs( 12, getWorld() );
    }
    
    public void faceEast() {
    setDirection(EAST);
    }
    
    public void turn180(){
    turnRight();
    turnRight();
    }
    
    public void goToLocation(int inputX, int inputY) {
        int x = getX();
        int y = getY();
        int Xmovement = x-inputX; 
        int Ymovement = y-inputY;      
        if (Xmovement > 0) {
            turn180();
            for (int i=0; i<Xmovement; i++) {
                move();
            }
        } else if (Xmovement < 0) {
            for (int i=0; i>Xmovement; i--) {
                move();
            } 
            faceEast();
        } 

        if (Ymovement > 0) {
            setDirection(NORTH);
            for (int i=0; i<Ymovement; i++) {
                move();
            } 
            faceEast();
        }else if (Ymovement < 0) {
            setDirection(SOUTH);
            for (int i=0; i>Ymovement; i--) {
                move();
            } 
            faceEast();
        }
    }

    public Boolean validcoordinates(int x, int y){
        Boolean truth = true;
        int worldX = getWorld().getWidth();
        int worldY = getWorld().getHeight();
        worldX--;
        worldY--;
        System.out.println(worldX + " " +  worldY);
        if (x > worldX) {
            truth = false;
            System.out.println("invalid x coordinates");
        } else if (x < 0) {
            truth = false;
            System.out.println("invalid x coordinates");
        } 
        if (y > worldY) {
            truth = false;
            System.out.println("invalid y coordinates");
        } else if (y < 0) {
            truth = false;
            System.out.println("invalid y coordinates");
        } 
        return truth;
    }
    
    public int getAsManyEggsAsYouCanMimi() {
    List<GoldenEgg> goldenEggs = getListOfGoldenEggsInWorld();
    List<BlueEgg> blueEggs = getListOfBlueEggsInWorld();
    int movesLeft = 40;    
    int totalPoints = 0;
    if (!goldenEggs.isEmpty()) {
        GoldenEgg golden = goldenEggs.get(0);
        int cost = Math.abs(getX() - golden.getX()) + Math.abs(getY() - golden.getY());
        if (cost <= movesLeft) {
            goToLocation(golden.getX(), golden.getY());
            movesLeft = movesLeft - cost;
            pickUpEgg();
            totalPoints = totalPoints + 5;
        }
    }

    List<int[]> blueEggInformation = new ArrayList<>();
    for (BlueEgg eachEgg : blueEggs) {
        blueEggInformation.add(new int[]{eachEgg.getX(), eachEgg.getY()});
    }
     
    while (!blueEggInformation.isEmpty() && movesLeft > 0) {
        for (int i = 0; i < blueEggInformation.size(); i++) {
            for (int i2 = i + 1; i2 < blueEggInformation.size(); i2++) {
                int distance1 = Math.abs(getX() - blueEggInformation.get(i)[0]) + Math.abs(getY() - blueEggInformation.get(i)[1]);
                int distance2 = Math.abs(getX() - blueEggInformation.get(i2)[0]) + Math.abs(getY() - blueEggInformation.get(i2)[1]);
                if (distance1 > distance2) {
                    int[] tempPlaceHolder = blueEggInformation.get(i);
                    blueEggInformation.set(i, blueEggInformation.get(i2));
                    blueEggInformation.set(i2, tempPlaceHolder);
                }
            }
        }

        int[] nearestEgg = blueEggInformation.get(0);
        int cost = Math.abs(getX() - nearestEgg[0]) + Math.abs(getY() - nearestEgg[1]);
        if (cost <= movesLeft) {
            goToLocation(nearestEgg[0], nearestEgg[1]);
            movesLeft = movesLeft - cost;
            pickUpEgg();
            totalPoints++;
        }
        blueEggInformation.remove(0);
    }
    return totalPoints;
    }
}

