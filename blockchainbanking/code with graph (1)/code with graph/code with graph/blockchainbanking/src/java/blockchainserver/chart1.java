package blockchainserver;

import java.awt.Color;
import java.awt.GradientPaint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * A bar chart with only two bars - here the 'maxBarWidth' attribute in the renderer prevents
 * the bars from getting too wide.
 *
 */
public class chart1 extends ApplicationFrame implements MouseListener,MouseMotionListener
{
	
    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
	int x,y,x0,y0;
	
	private JFrame frame;
	
	private JButton buttonExit;
	Vector attack;
	
	public chart1( )
	{
		super("ACTOR");		
	}
    	
	public void addComponent( )
	{
		this.setDefaultCloseOperation(ApplicationFrame.DISPOSE_ON_CLOSE);

		buttonExit = new JButton( );
		
		buttonExit.setBackground(new Color(205, 198, 236));
		buttonExit.setFont( new Font( "italic",Font.BOLD,15));	
		buttonExit.setText( "Exit" );
		buttonExit.setBounds( 400,250,70,13 );
		buttonExit.setBorderPainted( false );
		buttonExit.setFocusPainted( false );

		this.add( buttonExit ); 

		buttonExit.addActionListener( new ActionListener(){ 
		public void actionPerformed( ActionEvent e )
		{
				System.out.println( "chart1" );
				
				 frame.dispose( );
				
		}	}	 );
 	}
	
	public chart1(String title1,Vector v1,Vector v2) 
	{
        super(title1);
        
//        attack=v3;
        
        final CategoryDataset dataset = createDataset(v1,v2);
        final JFreeChart chart = createChart(dataset);
		
		addComponent( );
		
		// add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel( chart );
 		chartPanel.setPreferredSize(new java.awt.Dimension( 500, 270 ));
 		this.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		this.setResizable( false );
	//	this.setUndecorated( true );
		chartPanel.add( buttonExit );
		frame = this;
		this.setVisible( true );
		chartPanel.setLayout( null );
		setContentPane( chartPanel );
     }
	
    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
     CategoryDataset createDataset(Vector v1,Vector v2) {
        
       // row keys...
 		String series[]=new String[v1.size()];
 		for (int i=0;i<v1.size();i++)
 		series[i]=v1.get(i).toString();
       
       // column keys...
	   final String category1 = "Category 1";

	//	double d1 = ;
	//	double d2 = 3367;
	//	double d3 = 4367;
       
		// create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i=0;i<v2.size();i++)
        dataset.addValue((Long)v2.get(i), series[i], "");  //8888888888888888888888888888888888
		
        return dataset;
    }
 
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    JFreeChart createChart(final CategoryDataset dataset) {
        
        // create the chart...
         JFreeChart chart = ChartFactory.createBarChart(
            "Response Comparison",         // chart title
            "INFORMATION                   ",               // domain axis label
            "Time(MS)",         // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(new Color(0xBBBBDD));

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        renderer.setMaximumBarWidth(0.10);
       // renderer.setMinimumBarLength();
        
        // set up gradient paints for series...
        
        
        final GradientPaint gp1 = new GradientPaint(
            0.0f, 0.0f, Color.green, 
            0.0f, 0.0f, Color.lightGray
        );
        final GradientPaint gp2 = new GradientPaint(
            0.0f, 0.0f, Color.red, 
            0.0f, 0.0f, Color.lightGray
        );
        
       
        		renderer.setSeriesPaint(0, gp1);
        	
        		renderer.setSeriesPaint(1, gp2);
        	
        	
        
        
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
     }
    
    public void Fun( Vector v1,Vector v2)
	{
        chart1 demo = new chart1("MAP",v1,v2);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
	}

	// mouse handling method
	 public void mouseMoved( MouseEvent event )
	{
				
	}
  	 public void mouseDragged( MouseEvent event )
	{
				
	}
   	 public void mouseClicked(MouseEvent event) 
	{
				
	}
  	  public void mouseEntered(MouseEvent event) 
	{
			
	}
  	  public void mouseExited(MouseEvent event) 
	{
			
	}
   	 public void mousePressed(MouseEvent event) 
	{
		x = event.getX( );
		y = event.getY( );
	}
   	 public void mouseReleased(MouseEvent event) 
	{
		Graphics gfx = this.getGraphics( );
		x0 = event.getX( );
		y0 = event.getY( );
		gfx.setColor( new Color(  45, 110, 3  ) );
		System.out.println( "x:" + x + "y:" + y + "x0:" + x0 + "y0:" + y0 );
		gfx.drawLine( x,y,x0,y0 );
    }
}
