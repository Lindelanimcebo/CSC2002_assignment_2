package FlowSkeleton;

import java.util.concurrent.atomic.AtomicBoolean;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class FlowPanel extends JPanel implements Runnable {

	Terrain land;
	Water water;

	// Flags
    AtomicBoolean play = new AtomicBoolean();
    boolean pause;
    boolean exit;

    // Timer
    static long count = 0;
    static JLabel timestep;
	
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
        play.set(false);
        pause = true;
    }

    public void play(){
        this.pause = false;
        this.play.set(true);
    } 

    public void exit(){
        exit = true;
    }

    public void reset(){
        play.set(false);
        water.clear();
        count = 0;
        timestep.setText(String.format("Simulation timesteps : %d ", count ));
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

                if (play.get()){

					FlowThread.terrain = land;
                    FlowThread.water = water;
                    repaint();
                    FlowThread thread1 = new FlowThread(0, dim/num_threads);
                    FlowThread thread2 = new FlowThread(dim/num_threads, (dim/num_threads)*2);
                    FlowThread thread3 = new FlowThread((dim/num_threads)*2, (dim/num_threads)*3);
                    FlowThread thread4 = new FlowThread((dim/num_threads)*3, dim);

                    thread1.start();
                    thread2.start();
                    thread3.start();
                    thread4.start();
                    repaint();
                    thread1.join();
                    repaint();
                    thread2.join();
                    repaint();
                    thread3.join();
                    repaint();
                    thread4.join();

                    timestep.setText(String.format("Simulation timesteps : %d ", count++ ));
                    repaint();


                }
            }
            System.out.println( water.conserved() ? "Water Conserved" : "Water not conserved" );
        } catch (Exception e){
            System.err.println(e);
        }
	    repaint();
	}
}