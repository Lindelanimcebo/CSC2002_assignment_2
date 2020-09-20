package FlowSkeleton;

public class FlowThread extends Thread{

    static Terrain terrain;
    static Water water;

    // private Terrain terrain;
    // private Water water;

    int startIndex;
    int stopIndex;

    // public FlowThread(Terrain terrain, Water water, int startIndex, int stopIndex){
    //     this.terrain = terrain;
    //     this.water = water;
    //     this.startIndex = startIndex;
    //     this.stopIndex = stopIndex;
    // }

    public FlowThread( int startIndex, int stopIndex ){
            this.startIndex = startIndex;
            this.stopIndex = stopIndex;
            
    }

    @Override
    public void run(){

        int [] positions = new int[2];

        for (int i = startIndex; i < stopIndex; i++){
            terrain.getPermute( i, positions );
            waterFlow( positions[0], positions[1] );
        }

    }

    void waterFlow(int x, int y){

        if ( water.getDepth(x,y) <= 0 ){
            return;
        }

        float minSurface = ( water.getDepth(x,y)*0.01f ) + terrain.height[x][y];
        int minX = x; int minY = y;

        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if ( terrain.inside(i,j) ){
                    float surface = ( water.getDepth(i,j)/100f ) + terrain.height[i][j];
                    if (surface < minSurface){
                        minSurface = surface;
                        minX = i; minY = j;
                        
                    }

                }
            }
        }

        water.dec(x,y);
        if ( terrain.boundary(minX,minY) ){
            water.setDepth( minX, minY, 0 );
        } else {
            water.inc( minX, minY );
        }

    }


}