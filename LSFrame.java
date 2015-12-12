import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class LSFrame extends Frame {

	private Grid grid;

	public LSFrame(String title, int cellWidth, int cellHeight, int rows, int columns, int[][] colorMatrix) {
		super("sim");
		if(title != null)
			setTitle(title);
		// Frame & input setup
//		addMouseListener(menu);
//		addKeyListener(this);
		setLayout(null);
		int winX = (columns*cellWidth < 400) ? 400 : columns*cellWidth; 
		int winY = (rows*cellHeight < 400) ? 400 : rows*cellHeight;
		setSize(winX,winY);
		setBackground(Color.white);
		setResizable(true);
		setVisible(true);
		setLocationRelativeTo(null);
		
		addWindowListener( new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we) {
			System.exit(0);
		    }
		} );

		update(cellWidth, cellHeight, rows, columns, colorMatrix);
	}

	public void update(int cellWidth, int cellHeight, int rows, int columns, int[][] colorMatrix) {
		if(grid == null || cellWidth != grid.cellWidth || cellHeight != grid.cellHeight || columns != grid.columns || rows != grid.rows)
			grid = new Grid(cellWidth, cellHeight, rows, columns, colorMatrix);
		else 
			grid.updateColors(colorMatrix);
		repaint();
	}

	public void paint(Graphics g) {
		grid.paint(g);
//		repaint();
	}
	

}

class Grid {

	public int rows = 15, columns = 15;		// width and height of cells are
							// proportional to the screen
	public int cellWidth = 6, cellHeight = 6;
	private String barColor = "0xff0000", backgroundColor; 	// hex colors of bars and background

	private int[][] matrix = new int[rows*columns][4];	
	private int[][] colorMatrix = new int[rows*columns][3];

	private boolean matrixGenerated = false;

	public Grid(int cellWidth, int cellHeight, int columns, int rows, int[][] colors) {
		this.rows = rows;
		this.columns = columns;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;
		matrix = new int[rows*columns][4];
		calculateMatrix();
		updateColors(colors);
	}

	public void updateColors(int[][] colors) {
		colorMatrix = colors;
	}

	private void calculateMatrix()
	{
		Rectangle bounds = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int winWidth = bounds.width, winHeight = bounds.height;
		System.out.println("rows = " + rows + ", columns = " + columns);
		for(int i = 0; i < columns; i++)
		{
			for(int j = 0; j < rows; j++)
			{	
				int pos = (j*columns) + i, startX =43 + i * cellWidth, startY = 43 + j * cellHeight;
				// first element is starting x
				matrix[pos][0] = startX;
				// second element is starting y
				matrix[pos][1] = startY;
				// third element is starting x
				matrix[pos][2] = cellWidth;
				// fourth element is starting x
				matrix[pos][3] = cellHeight;
			}
		}
		matrixGenerated = true;
	}

	public void paint(Graphics g) {
		if(!matrixGenerated)
			return;
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i < matrix.length; i++){
			int[] rgb = colorMatrix[i];
			g2.setPaint(new Color(rgb[0], rgb[1], rgb[2]));
			g2.fill(new Rectangle2D.Double(matrix[i][0], matrix[i][1], matrix[i][2], matrix[i][3]));
			g2.setPaint(Color.decode(barColor));
			g2.draw(new Rectangle2D.Double(matrix[i][0], matrix[i][1], matrix[i][2], matrix[i][3]));
		}
	}
}
