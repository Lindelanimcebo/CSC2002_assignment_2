package FlowSkeleton;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
*  Flow class for initializing the simulation and acts as a view to the user 
 */
public class Flow {

	static long startTime = 0;
	static long pauseTime = 0;
	static int frameX;
	static int frameY;
	static FlowPanel fp;

	/**
	* Function to start timer in case timing is required.
	 */
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	/**
	* Function to stop time and return duration
	* @return Timer duration ( current time - start time )
	 */
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	/**
	* Function for setting up GUI and initializing all data required
	* @param frameX the x dimension of the GUI frame
	* @param frameY the y dimension of the GUI frame
	* @param landdata the terrain data to be used
	* @param water the water data to be used
	 */
	public static void setupGUI(int frameX,int frameY,Terrain landdata, Water water) {
		
		Dimension fsize = new Dimension(800, 800);
    	JFrame frame = new JFrame("Waterflow"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(new BorderLayout());
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 

		landdata.genPermute(); // Generate Land dat permutation 
   
		fp = new FlowPanel(landdata, water );
		fp.setPreferredSize(new Dimension(frameX,frameY));
		g.add(fp);
	    
		// timestamp label
		JLabel timer = new JLabel(String.format("Simulation timesteps : %d ", 0 ));
		FlowPanel.timestep = timer;

		// MouseListener 
		g.addMouseListener( new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				fp.addWater(e.getX(), e.getY());
			}
		});
		
	   	
		JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

		// Reset Button
		JButton resetB = new JButton("Reset");
		resetB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fp.reset();
			}
		});

		// Pause Button
		JButton pauseB = new JButton("Pause");
		pauseB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fp.pause();
			}
		});

		// Play Button
		JButton playB = new JButton("Play");
		playB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fp.play();
			}
		});

		JButton endB = new JButton("End");;
		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				fp.exit();
				frame.dispose();
			}
		});
		
		b.add(timer);
		b.add(resetB);
		b.add(pauseB);
		b.add(playB);
		b.add(endB);
		g.add(b);
    	
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp);
        fpt.start();
	}
	
	/**
	* Main runner function to run the controller class
	* @param args input comands from the terminal
	 */
	public static void main(String[] args) {
		Terrain landdata = new Terrain();
		
		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			System.exit(0);
		}
				
		// landscape information from file supplied as argument
		landdata.readData(args[0]);
		
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();

		Water water = new Water( frameX, frameY );

		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata, water ));
		
	}
}
