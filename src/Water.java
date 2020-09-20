package FlowSkeleton;

public class Water{

    private int [][] depths;
    private int dimx;
    private int dimy;

    private long totalWater;
    private long waterRemoved;

    public Water(int dimx, int dimy){
        this.depths = new int[dimx][dimy];
        for (int i = 0; i < dimx; i++){
            for (int j = 0; j < dimy; j++){
                this.depths[i][j] = 0;
            }
        }
        this.dimx = dimx;
        this.dimy = dimy;
    }
    public synchronized boolean inside(int x, int y){
        return ( (x < dimx) && (y < dimy) && (x >= 0) && (y >= 0) );
    }
    public boolean boundary(int x, int y){
		return ( (x == 0 ) || ( y == 0 ) || (x == dimx - 1) || (y == dimy - 1) );
	}

    public synchronized void clear(){
        depths = new int[dimx][dimy];
    }

    public synchronized boolean conserved(){
        long waterLeft = 0;
        for (int i = 0; i < dimx; i++){
            for (int j = 0; j < dimy; j++){
                waterLeft += depths[i][j];
            }
        }
        return ( (totalWater - (waterRemoved + waterLeft)) == 0 );
    }

    public synchronized int getDepth(int x, int y){
        return this.depths[x][y];
    }

    public synchronized void setDepth(int x, int y, int depth){
        if ( depth == 0 ){
            this.waterRemoved += this.depths[x][y];
        } else {
            totalWater += depth;
        }
        this.depths[x][y] = depth;
    }

    public synchronized void inc(int x, int y){
        this.depths[x][y]++;
        totalWater++;
    }

    public synchronized void dec(int x, int y){
        this.depths[x][y]--;
        totalWater--;
    }
}