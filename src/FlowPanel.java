package FlowSkeleton;

import java.awt.Graphics;
import java.awt.Color;
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

        // Paint water;
        g.setColor(Color.BLUE);
        int [] positions = new int[2];
        for ( int i = 0; i < land.dim(); i++){
            land.getPermute(i, positions);
            if ( water.getDepth( positions[0], positions[1] ) > 0 ){
                    g.fillRect( positions[0], positions[1], 1, 1 );
                }
        }


		// Water Renderer
		// WaterRenderer.g = g;
		// WaterRenderer.terrain = this.land;
		// WaterRenderer.water = this.water;

		// fjpool.invoke( new WaterRenderer( 0, land.dim() ) );
	}

	// Add Water
	public void addWater(int x, int y){
        int depth = 5;
        int startX = x - depth; int stopX = x + depth;
        int startY = y - depth; int stopY = y + depth;

        for (int i = startX; i < stopX; i++){
            for (int j = startY; j < stopY; j++){
                if (land.inside( i, j )){
                    water.setDepth( i, j, 1 );
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

        System.out.println("0");

		repaint();

        try {
            while(!exit){

				repaint();

                if (play){

                    // /repaint();
					FlowThread.terrain = land;
                    FlowThread.water = water;
                    // FlowThread [] threads = new FlowThread[num_threads];

                    // for (int i = 0; i < num_threads; i++){
                    //     // threads[i] = new FlowThread(terrain, water, (dim/num_threads)*i , dim / (num_threads-i) );
                    //     threads[i] = new FlowThread( (dim/num_threads)*i , dim / (num_threads-i) );
                    // }

                    // for (int i = 0; i < num_threads; i++){
                    //     threads[i].start();
                    // }
                    
                    // for (int i = 0; i < num_threads; i++){
                    //     threads[i].join();
                    // }
					//repaint();

                    FlowThread thread1 = new FlowThread(0, dim/num_threads);
                    FlowThread thread2 = new FlowThread(dim/num_threads, (dim/num_threads)*2);
                    FlowThread thread3 = new FlowThread((dim/num_threads)*2, (dim/num_threads)*3);
                    FlowThread thread4 = new FlowThread((dim/num_threads)*3, dim);

                    thread1.start();
                    thread2.start();
                    thread3.start();
                    thread4.start();

                    thread1.join();
                    thread2.join();
                    thread3.join();
                    thread4.join();

                    repaint();
                
                }
            }
        } catch (Exception e){
            System.err.println(e);
        }
	    repaint();
	}
}