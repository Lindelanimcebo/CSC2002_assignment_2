package FlowSkeleton;

public class Water{

    private int [][] depths;
    private int dimx;
    private int dimy;
    // private float depth_float;
    // private static final float depth_unit = 0.01f;

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
    public boolean inside(int x, int y){
        return ( (x < dimx) && (y < dimy) && (x >= 0) && (y >= 0) );
    }
    public synchronized void clear(){
        depths = new int[dimx][dimy];
    }

    public synchronized int getDepth(int x, int y){
        return this.depths[x][y];
    }

    public synchronized void setDepth(int x, int y, int depth){
        this.depths[x][y] = depth;
    }

    public synchronized void inc(int x, int y){
        this.depths[x][y]++;
    }

    public synchronized void dec(int x, int y){
        if (this.depths[x][y] >= 1){
            this.depths[x][y]--;
        } else {
            this.depths[x][y] = 0;
        }
    }
}