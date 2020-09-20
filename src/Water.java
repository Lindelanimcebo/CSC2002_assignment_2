package FlowSkeleton;

/**
* Water class to represant water depths at different points in the terrain.
 */
public class Water{

    private int [][] depths;
    private int dimx;
    private int dimy;

    private long totalWater;
    private long waterRemoved;

    /**
    * Contructor to initialize water data and set every depth to zero.
     */
    public Water(int dimx, int dimy){
        this.depths = new int[dimx][dimy];
        for (int i = 0; i < dimx; i++){
            for (int j = 0; j < dimy; j++){
                this.depths[i][j] = 0;
            }
        }
        this.dimx = dimx;
        this.dimy = dimy;

        totalWater = 0;
        waterRemoved = 0;
    }

    /**
    * Function to determine whether the a coordinate is inside the water array or not
    * @param x x index
    * @param y y index
    * @return boolean indication of whethe ther coordinate is inside or not.
    */
    public synchronized boolean inside(int x, int y){
        return ( (x < dimx) && (y < dimy) && (x >= 0) && (y >= 0) );
    }

    /**
    * Function to determine whether the a coordinate is at the bounds the water array or not
    * @param x x index
    * @param y y index
    * @return boolean indication of whethe ther coordinate is at the bounds or not.
    */
    public boolean boundary(int x, int y){
		return ( (x == 0 ) || ( y == 0 ) || (x == dimx - 1) || (y == dimy - 1) );
	}

    /**
    * Clears water by setting all the depths to 0; and creating a new water array.
    */
    public synchronized void clear(){
        depths = new int[dimx][dimy];
        totalWater = 0;
        waterRemoved = 0;
    }

    /**
    * Tester function to test if the water was conserved or not.
    * Should be used at the end of the simulation as a check for correctness.
    * @return boolean indication for water conservation.
    */
    public synchronized boolean conserved(){
        long waterLeft = 0;
        for (int i = 0; i < dimx; i++){
            for (int j = 0; j < dimy; j++){
                waterLeft += depths[i][j];
            }
        }
        return ( (totalWater - (waterRemoved + waterLeft)) == 0 );
    }

    /**
    * Returns the water depth at a certain position
    * @param x x index
    * @param y y index
    * @return depth at the position
    */
    public synchronized int getDepth(int x, int y){
        return this.depths[x][y];
    }

    /**
    * sets the water depth at a certain position
    * @param x x index
    * @param y y index
    * @param depth depth to set to
    */
    public synchronized void setDepth(int x, int y, int depth){
        if ( depth == 0 ){
            this.waterRemoved += this.depths[x][y];
        } else {
            totalWater += depth;
        }
        this.depths[x][y] = depth;
    }

    /**
    * increments of the water depth at a certain position by one.
    * @param x x index
    * @param y y index
    */
    public synchronized void inc(int x, int y){
        this.depths[x][y]++;
        totalWater++;
    }

    /**
    * decrements the water depth at a certain position by one.
    * @param x x index
    * @param y y index
    */
    public synchronized void dec(int x, int y){
        this.depths[x][y]--;
        totalWater--;
    }
}