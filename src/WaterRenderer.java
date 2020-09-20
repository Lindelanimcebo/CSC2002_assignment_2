package FlowSkeleton;

import java.awt.Graphics;
import java.awt.Color;
import java.util.concurrent.RecursiveAction;

public class WaterRenderer extends RecursiveAction{

    static final int SEQUENTIAL_CUTOFF  = 150;
    static Graphics g;
    static Terrain terrain;
    static Water water;

    private int startIndex;
    private int stopIndex;

    public WaterRenderer(int startIndex, int stopIndex){
        this.startIndex = startIndex; this.stopIndex = stopIndex;
    }



    @Override
    protected void compute(){

        if ( (stopIndex - startIndex) < SEQUENTIAL_CUTOFF ){
            g.setColor(Color.BLUE);

            int [] positions = new int[2];
            for (int i = startIndex; i < stopIndex; i++){
                terrain.getPermute(i, positions);

                if ( water.getDepth( positions[0], positions[1] ) > 0 ){
                    g.fillRect( positions[0], positions[1], 1, 1 );
                }

            }

        } else {
            int midIndex = ( startIndex + stopIndex ) / 2;

            WaterRenderer left = new WaterRenderer( startIndex, midIndex );
            WaterRenderer right = new WaterRenderer( midIndex, stopIndex );

            left.fork();
            right.compute();
            left.join();

        }
    }

}
