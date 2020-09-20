package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.concurrent.ForkJoinPool;

public class FlowPanel extends JPanel implements Runnable {
	Terrain land;
	Water water;

	// Flags
    boolean play;
    boolean pause;
    boolean exit;

	static final ForkJoinPool fjpool = new ForkJoinPool();
	
	FlowPanel(Terrain land, Water water) {
		this.land=land;
		this.water=water;
	}
		
	// responsible for painting the terrain and water
	// as images
	@Override
    protected void paintComponent(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		  
		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}

		// Water Renderer
		WaterRenderer.g = g;
		WaterRenderer.terrain = land;
		WaterRenderer.water = water;
		
		fjpool.invoke( new WaterRenderer( 0, land.dim() ) );
	}

	// Add Water
	public void addWater(int x, int y){
        int depth = 5;
        int startX = x - depth; int stopX = x + depth;
        int startY = y - depth; int stopY = y + depth;

        for (int i = startX; i < stopX; i++){
            for (int j = startY; j < stopY; j++){
                if (land.inside( i, j )){
                    water.inc( i, j );
                }
                    
            }
        }
		repaint();
    }

	public void pause(){
        play = false;
        pause = true;
    }

    public void play(){
        this.play = true;
    } 

    public void exit(){
        exit = true;
    }

    public void reset(){
        water.clear();
    }
	


	public void run() {	

		int dim = land.dim();
        int num_threads = 4;

        FlowThread.terrain = land;
        FlowThread.water = water;

		repaint();

        try {
            while(!exit){

				repaint();

                if (play){
					
                    FlowThread [] threads = new FlowThread[num_threads];

                    for (int i = 0; i < num_threads; i++){
                        // threads[i] = new FlowThread(terrain, water, (dim/num_threads)*i , dim / (num_threads-i) );
                        threads[i] = new FlowThread( (dim/num_threads)*i , dim / (num_threads-i) );
                    }

                    for (int i = 0; i < num_threads; i++){
                        threads[i].start();
                    }
                    
                    for (int i = 0; i < num_threads; i++){
                        threads[i].join();
                    }
					repaint();
                }
            }
        } catch (Exception e){
            System.err.println(e);
        }
	    repaint();
	}
}