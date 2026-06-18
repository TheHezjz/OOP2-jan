import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 *
 * @author Sjaak Smetsers & Renske Smetsers-Weeda
 * @version 3.0 -- 20-01-2017
 */
public class MyDodo extends Dodo
{
    private int myNrOfEggsHatched;

    public MyDodo() {
        super( EAST );
        myNrOfEggsHatched = 0;
    }

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
     * Test if Dodo can move forward, (there are no obstructions
     *    or end of world in the cell in front of her).
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can move (no obstructions ahead)
     *                 false if Dodo can't move
     *                      (an obstruction or end of world ahead)
     */
    public boolean canMove() {
        if ( borderAhead()|| fenceAhead() ){
            return false;
        } else {
            return true;
        }
    }

    /**
     * Hatches the egg in the current cell by removing
     * the egg from the cell.
     * Gives an error message if there is no egg
     * 
     * <p> Initial: Dodo is somewhere in the world. There is an egg in Dodo's cell.
     * <p> Final: Dodo is in the same cell. The egg has been removed (hatched).     
     */    
    public void hatchEgg () {
        if ( onEgg() ) {
            pickUpEgg();
            myNrOfEggsHatched++;
        } else {
            showError( "There was no egg in this cell" );
        }
    }

    /**
     * Returns the number of eggs Dodo has hatched so far.
     * 
     * @return int number of eggs hatched by Dodo
     */
    public int getNrOfEggsHatched() {
        return myNrOfEggsHatched;
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
            move(); 
            System.out.println("moved");    // take a step
            nrStepsTaken++;                 // increment the counter
        }
    }

    /**
     * Walks to edge of the world printing the coordinates at each step
     * 
     * <p> Initial: Dodo is on West side of world facing East.
     * <p> Final:   Dodo is on East side of world facing East.
     *              Coordinates of each cell printed in the console.
     */

    public void walkToWorldEdge( ){
        while( !borderAhead() ){
            move(); 
        }
    }

    /**
     * Test if Dodo can lay an egg.
     *          (there is not already an egg in the cell)
     * 
     * <p> Initial: Dodo is somewhere in the world
     * <p> Final:   Same as initial situation
     * 
     * @return boolean true if Dodo can lay an egg (no egg there)
     *                 false if Dodo can't lay an egg
     *                      (already an egg in the cell)
     */

    public boolean canLayEgg( ){
        if(onEgg() ){
            return false;
        }else{
            return true;
        }
    }  

    /** Week 1 */
    public void turn180() {
        turnRight();
        turnRight();
    }

    public void climbOverFence() {
        turnLeft();
        move();
        turnRight();
        move();
        move();
        turnRight();
        move();
        turnLeft();

    }

    public boolean grainAhead() {
        move();
        if (onGrain()){
            stepOneCellBackwards();
            return true;
        }else {
            stepOneCellBackwards();
            return false;}
    }

    /** Week 2 */
    public void goToEgg() {
        while (!onEgg()) {
            move();
        }
    }

    public void goBackToStartOfRowAndFaceBack() {
        turn180();
        walkToWorldEdge();
        turn180();
    }

    public void walkToWorldEdgeClimbingOverFences() {
        while(!borderAhead()) {
            if (fenceAhead()) {
                climbOverFence();
            }else{move();}
            if (onNest()) {
                layEgg();
                break;
            }
        } 
    }

    public void pickUpGrainsAndPrintCoordinates() {
        while(!borderAhead()) {
            if(onGrain()) {
                pickUpGrain();
                System.out.println("X = " + getX() + ". Y = " + getY() + ", ");
                move();
            } else{move();}
        }
        if(onGrain()) {
            pickUpGrain();
            System.out.println("X = " + getX() + ". Y = " + getY() + ", ");}
    }

    public void stepOneCellBackwards() {
        turn180();
        move();
        turn180();
    }

    public void worldEmptyNestsTopRow() {
        if(onNest() && canLayEgg()) {
            layEgg();
        }
        while(!borderAhead()) {
            move();
            if(onNest() && canLayEgg()) {
                layEgg();
            }
        }

    }

    public void walkAroundFenceArea() {
        while(!onEgg()) {
            if(!fenceAhead()) {
                turnRight();}
            if(fenceAhead() == true){
                turnLeft();
                move();
            } else {move();}
        }
    }

    public void eggTrailToNest(){
        while(!onNest()) {
            if(eggAhead() == true) {
                move();
                pickUpEgg();
            } else{turnRight();}
            if(nestAhead() == true) {
                move();
            } 
        }
    }

    public void doolhofPathFinder() {
        while(!onNest()) {
            if(fenceAhead() || eggAhead()) {
                turnRight();
            } else {
                layEgg();
                move();}
        }
    }

    public Boolean checkingForFenceOnTheRight() {
        turnRight();
        if (fenceAhead()){
            turnLeft();
            return true;
        } 
        else {
            turnLeft();
            return false;}

    }

    public void doolhofPathFinderAdvance() {
        while(!onNest()) {
            if (checkingForFenceOnTheRight() == false) {
                turnRight();
                move();}
            if (!fenceAhead() || nestAhead()){
                move();} else {turnLeft();}
        }
    }

    public void faceEast() {
        setDirection(EAST);
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

    public Boolean checkingForEggsOnTheRight() {
        turnRight();
        if (eggAhead()){
            turnLeft();
            return true;
        } 
        else {
            turnLeft();
            return false;}
    }

    public int countEggsInRow() {
        int amountEggs = 0;
        while(!onNest()){
            if (eggAhead() || nestAhead()){
                move();
            } 
            if (checkingForEggsOnTheRight() == true) {
                turnRight();
                move();} 
            else {
                turnLeft();
            } 
            if (onEgg()) {
                amountEggs++;
            }
        }
        return amountEggs;
    }

    public void layTrailOfEggs(int howManySteps) {
        layEgg();
        while (howManySteps != 0) {
            move();
            layEgg();
            howManySteps--;
        }
    }

    public int countEggsInTheWorld() {
        int eggCountTotal = 0;
        int posX = getX();
        int posY = getY();
        int posSum = posX + posY;
        int worldX = getWorld().getWidth() -1;
        int worldY = getWorld().getHeight() -1;
        goToLocation(0,0);

        while (eggCountTotal == 0 || posSum != 0) 
        {
            if (posX == 0 && posY == worldY ) {
                goToLocation(0,0);
                System.out.print(eggCountTotal);
            }

            if (borderAhead() == true && getDirection() == EAST) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                setDirection(SOUTH);
                move();
                setDirection(WEST);
            }

            else if (borderAhead() == true && getDirection() == WEST) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                setDirection(SOUTH);
                move();
                setDirection(EAST);
            }

            if (borderAhead() == false) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                posX = getX();
                posY = getY();
                posSum = posX + posY;
                move();
            }
        }
        return eggCountTotal;
    }

    public String mostEggsInARow() {
        int eggCountTotal = 0;
        int mostEggsInRow = 0;
        int posX = getX();
        int posY = getY();
        int posSum = posX + posY;
        int mostEggsY = 0;
        int worldX = getWorld().getWidth() -1;
        int worldY = getWorld().getHeight() -1;
        goToLocation(0,0);
        boolean loopEnd = false;

        while (loopEnd == false || posSum != 0) 
        {
            if (posX == 0 && posY == worldY ) {
                goToLocation(0,0);
                System.out.print(eggCountTotal);
            }

            if (borderAhead() == true && getDirection() == EAST) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                setDirection(SOUTH);
                move();
                setDirection(WEST);
            }

            else if (borderAhead() == true && getDirection() == WEST) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                setDirection(SOUTH);
                move();
                setDirection(EAST);
            }

            if (borderAhead() == false) 
            {
                if (onEgg() == true) 
                {
                    eggCountTotal = eggCountTotal + 1;
                }
                if (eggCountTotal >= mostEggsInRow) {
                    mostEggsInRow = eggCountTotal;
                    mostEggsY = getY();
                }
                eggCountTotal = 0;
                posX = getX();
                posY = getY();
                posSum = posX + posY;
                move();
            }
            if (getX() == worldX && getY() == worldY) {
                loopEnd = true;
            }
        }
        String answer = "the row witht he most eggs is row Y" + mostEggsY + "With " + mostEggsInRow + " eggs in their row";
        return answer;
    }

    public Boolean checkingForWorldBorderRight() {
        turnRight();
        if (borderAhead()){
            turnLeft();
            return true;
        } 
        else {
            turnLeft();
            return false;}
    }

    public void monumentOfEggs() {
        boolean endOfLoop = false;
        int worldX = getWorld().getWidth() -1;
        int worldY = getWorld().getHeight() -1;
        int originXPosition = getX();
        int originYPosition = getY();
        int yMovement = 0;
        int xMovement = -1;
        while (endOfLoop == false){
            for (int i = 0; i <= xMovement; i++) {
                if (!onEgg()) {
                    layEgg();}
                move();
                System.out.println("for loop works and i =" + i);
            }
            if (!onEgg()) {
                    layEgg();}
            xMovement++;
            System.out.println("xMovement =" + xMovement);
            originYPosition++;
            if(checkingForWorldBorderRight() == true || borderAhead() == true) {
                endOfLoop = true;
                break;
            } 
            goToLocation(originXPosition,originYPosition);
        }

    }
}


