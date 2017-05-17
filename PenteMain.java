package Pente;
import javax.swing.JFrame;
public class PenteMain {

	public static final int Empty = 0;
	public static final int Black = 1;
	public static final int White = -1;
	public static void main(String[] args){
		int boardWidth = 720;
		int boardWidthInSquares = 19;
		
		JFrame f = new JFrame( " Play Pente -- All the Time...");
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		f.setSize(boardWidth,boardWidth);
		//at this point we make the board and load it.. but not now
		GameBoard p = new GameBoard(boardWidth, boardWidthInSquares);
		
		f.add(p);
		f.setVisible(true);
	}
}
