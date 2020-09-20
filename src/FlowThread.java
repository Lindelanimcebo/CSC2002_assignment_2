package FlowSkeleton;

/**
* Thread class to control the flow of water off the terrain
 */
public class FlowThread extends Thread{

    static Terrain terrain;
    static Water water;

    int startIndex;
    int stopIndex;

    /**
    * Constructor to initialize the thread
    * @param startIndex index to start at
    * @param stopIndex index to stop at
     */
    public FlowThread( int startIndex, int stopIndex ){
            this.startIndex = startIndex;
            this.stopIndex = stopIndex;
            
    }

    /**
    * Thread overriden run function to run the current thread
     */
    public void run(){

        int [] positions = new int[2];

        for (int i = startIndex; i < stopIndex; i++){
            terrain.getPermute( i, positions );
            waterFlow( positions[0], positions[1] );
        }

    }

    /**
    * Helper function to manage all the flow of water from the current position
    * to the neighbour with the minimum surface
    * @param x x index
    * @param y y index
     */
    synchronized void waterFlow(int x, int y){


        if ( water.getDepth(x,y) > 0 ){
            
            float minSurface = ( water.getDepth(x,y)*0.01f ) + terrain.height[x][y];
            int minX = x; int minY = y;

            for (int i = x - 1; i <= x + 1; i++){
                for (int j = y - 1; j <= y + 1; j++){
                    if ( terrain.inside(i,j) ){
                        float surface = ( water.getDepth(i,j)*0.01f ) + terrain.height[i][j];
                        if (surface <= minSurface){
                            minSurface = surface;
                            minX = i; minY = j;
                            
                        }

                    }
                }
            }
            
            if ( water.boundary(minX,minY) ){
                water.setDepth( minX, minY, 0 );
            } else if (x != minX || y != minY){
                water.dec(x,y);
                water.inc( minX, minY );
            }
        }
    }
}