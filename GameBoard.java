package Pente;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel implements MouseListener{
	private int bWidthPixels;
	private int bWidthSquares;
	private int bSquareWidth;
	private int currentTurn = PenteMain.Black;
	//Color boardSquareColor = new Color(150,111,51);
	private Square [] [] theBoard;
	//This is for counting captures
	private int whitestoneCaptures = 0;
	private int blackstoneCaptures = 0;
	Ralph computerMoveGenerator = null;
	boolean playingAgainstRalhp = false;
	boolean playingAgainstRalph;
	int ralphStoneColor;
public GameBoard(int bWPixel, int bWSquares)
{
	bWidthPixels= bWPixel-10;
	bWidthSquares = bWSquares;
	//compute the width of the b squares
	bSquareWidth = (int)(Math.ceil(bWidthPixels/bWidthSquares))+2;
	this.setSize(bWidthPixels, bWidthPixels );
	this.setBackground(Color.CYAN);
	//testSquare = new Square(0,0, bSquareWidth);
	theBoard = new Square[bWSquares][bWSquares];
	for(int row = 0; row < bWSquares; ++row){
		for(int col = 0; col < bWSquares; ++col){
		theBoard[row][col]= new Square((col*bSquareWidth), (row*bSquareWidth), bSquareWidth, row, col);
		theBoard[row][col].setState(PenteMain.Empty);
		}
	}
	
	
		
	//set the first stone
	theBoard[(int)(bWidthSquares/2)][(int)(bWidthSquares/2)].setState(PenteMain.Black);
	String computerAnswer = JOptionPane.showInputDialog("Hi, woudl you like to play agains the computer");
	if(computerAnswer.equals("Y") || 
		computerAnswer.equals("Yes") ||
		computerAnswer.equals("y") ||
		computerAnswer.equals("Yeah")){
	computerMoveGenerator = new Ralph(this,currentTurn);
	playingAgainstRalph = true;
	ralphStoneColor = currentTurn;
	}
	this.changeTurn();
	//Get the MouseListener working
	this.addMouseListener(this);
}
public void paintComponent(Graphics g){
	g.setColor(Color.CYAN);
	g.fillRect(0,0, bWidthPixels, bWidthPixels);
	//testSquare.drawMe(g);
	for(int col = 0; col < bWidthSquares; ++col){
		for(int row = 0; row < bWidthSquares; ++row){
			theBoard[row][col].drawMe(g); 
													}
												}	
										}
public void changeTurn(){
	currentTurn *=-1;
}
public void checkForCapture(Square s){
	int sRow = s.getRow();
	int sCol = s.col();
	int theOpposite = this.getTheOppositeState(s);
	//for a right horizontal check
//	for ( int dx = -1; dx <= 1; ++dx){
//	if(dx != 0){
//	if(sCol < bWidthSquares-3){
//		//make sure that rows is first
//		if(theBoard[sRow][sCol+1].getState() == theOpposite){
//			if(theBoard[sRow][sCol+2].getState() == theOpposite){
//				if(theBoard[sRow][sCol+3].getState() == currentTurn){
//					System.out.println("We have a Horizontal Capture checking right");
//					this.takeStones(sRow, sCol+1, sRow, sCol+2, currentTurn);
//					repaint();
//				}
//			}
			//Check if it is the opposite sides pair or the same side
			//If (this gameboard [ sRow + xlocation] [Col + ylocation] == the player's turn
			//Next Step is to see if the player has a piece on the opposite side of the chain
			//If( this game board [ sRow + 3*xLocation] [Col + 3*yLocation] == the side of the player
			//Next if all above is valid, remove the pieces
			//takeStones( row + xLocation, col + yLocation, white)
			//takeStones( row + xLocation, col + yLocation, black)
			
	
		if (sCol < bWidthSquares -3){
				if(theOpposite == theBoard[sRow][sCol+1].getState()&& theOpposite == theBoard[sRow][sCol+2].getState() && s.getState() == theBoard[sRow][sCol+3].getState()){
					theBoard[sRow][sCol+1].setState(PenteMain.Empty);
					theBoard[sRow][sCol+2].setState(PenteMain.Empty);
}
}
			if (sCol > 2){
				if(theOpposite == theBoard[sRow][sCol-1].getState()&& theOpposite == theBoard[sRow][sCol-2].getState() && s.getState() == theBoard[sRow][sCol-3].getState()){
					theBoard[sRow][sCol-1].setState(PenteMain.Empty);
					theBoard[sRow][sCol-2].setState(PenteMain.Empty);
				}}
			if (sRow < bWidthSquares -3){
				if(theOpposite == theBoard[sRow+1][sCol].getState()&& theOpposite == theBoard[sRow+2][sCol].getState() && s.getState() == theBoard[sRow+3][sCol].getState()){
					theBoard[sRow+1][sCol].setState(PenteMain.Empty);
					theBoard[sRow+2][sCol].setState(PenteMain.Empty);	
}}
			if (sRow > 2){
				if(theOpposite == theBoard[sRow-1][sCol].getState()&& theOpposite == theBoard[sRow-2][sCol].getState() && s.getState() == theBoard[sRow-3][sCol].getState()){
					theBoard[sRow-1][sCol].setState(PenteMain.Empty);
					theBoard[sRow-2][sCol].setState(PenteMain.Empty);	
				}}
			if(sRow > 2 && sCol > 2){
				if(theOpposite == theBoard[sRow-1][sCol-1].getState()&& theOpposite == theBoard[sRow-2][sCol-2].getState() && s.getState() == theBoard[sRow-3][sCol-3].getState()){
					theBoard[sRow-1][sCol-1].setState(PenteMain.Empty);
					theBoard[sRow-2][sCol-2].setState(PenteMain.Empty);	
				}
			}
			if(sRow < bWidthSquares -3  && sCol > 2){
				if(theOpposite == theBoard[sRow+1][sCol-1].getState()&& theOpposite == theBoard[sRow+2][sCol-2].getState() && s.getState() == theBoard[sRow+3][sCol-3].getState()){
					theBoard[sRow+1][sCol-1].setState(PenteMain.Empty);
					theBoard[sRow+2][sCol-2].setState(PenteMain.Empty);
				}}
			if(sRow < bWidthSquares -3  && sCol < bWidthSquares -3){
				if(theOpposite == theBoard[sRow+1][sCol+1].getState()&& theOpposite == theBoard[sRow+2][sCol+2].getState() && s.getState() == theBoard[sRow+3][sCol+3].getState()){
					theBoard[sRow+1][sCol+1].setState(PenteMain.Empty);
					theBoard[sRow+2][sCol+2].setState(PenteMain.Empty);
				}}
			if(sRow > 2 && sCol < bWidthSquares -3){
				if(theOpposite == theBoard[sRow-1][sCol+1].getState()&& theOpposite == theBoard[sRow-2][sCol+2].getState() && s.getState() == theBoard[sRow-3][sCol+3].getState()){
					theBoard[sRow-1][sCol+1].setState(PenteMain.Empty);
					theBoard[sRow-2][sCol+2].setState(PenteMain.Empty);
				}}
}
public int getTheOppositeState(Square s){
	if(s.getState() == PenteMain.Black){
		return PenteMain.White;
	}else{
		return PenteMain.Black;
		}
										}

public void takeStones(int r1, int c1, int r2,int c2,int taker) {
	//this is clear stones
	theBoard[r1][c1].setState(PenteMain.Empty);
	theBoard[r2][c2].setState(PenteMain.Empty);
	
	if(taker == PenteMain.Black){
		++blackstoneCaptures;
	}else{
		++whitestoneCaptures;
	}
	this.CheckForWinOnCaptures();
	
}



public void CheckForWinOnCaptures(){
	if(blackstoneCaptures >= 5){
		//Some graphic here to tell you that you win
	JOptionPane.showMessageDialog(null,"black wins on captures");
	}
	if(whitestoneCaptures >= 5){
		//Some graphic here to tell you that you win
	JOptionPane.showMessageDialog(null,"white wins on captures");
	}
}

//public boolean checkForWin(Square s){
//		if(winY(s) || winX(s)){
//			return true;
//		}
//		else{
//			return false;
//		}
//		
//}\



public void checkForWin2(Square s){
boolean done = false;
int[] myDys = {-1, 0, 1};
    int whichDy = 0;
	while(!done && whichDy < 3){
		
		if (checkForWinAllInOne(s,myDys[whichDy], 1 ) == true){
			System.out.println("back in Check For Win 2 part 1 and we have a winner");
			winner();
			done = true;
		}
		whichDy++;
	}
		if(!done){
			if (checkForWinAllInOne(s, 1, 0) == true){
				System.out.println("back in Check For Win 2 part 2 and we have a winner");
				winner();

			}

		}


}
public boolean checkForWinAllInOne(Square s, int dy, int dx){
	boolean isThereAWin = false;
	int sRow = s.getRow();
	int sCol = s.col();
	int howManyRight = 0;
	int howManyLeft = 0;
	int step = 1;
		while((sCol + (step * dx) < bWidthSquares) && (sRow + (step * dy) < bWidthSquares) &&
		(sCol + (step * dx) >= 0) && (sRow + (step * dy) >= 0) &&
		(theBoard[sRow + (step * dy)][sCol + (step * dx)].getState() == currentTurn)){
		howManyRight++;
		step++;
}
	step = 1;
		while((sCol - (step * dx) >= 0) &&  (sRow - (step * dy) >= 0) &&
		(sCol - (step * dx) < bWidthSquares) && (sRow - (step * dy) < bWidthSquares) &&
		(theBoard[sRow - (step * dy)][sCol - (step * dx)].getState() == currentTurn)){
		howManyLeft++;
		step++;
		}
	//System.out.println("aFTER LOOPS in all in one how many left is " + howManyLeft + " and how many right is " + howManyRight);	
		
	if((howManyRight + howManyLeft + 1) >= 5){
		//System.out.println("all in one found a winner");
		isThereAWin = true;
	}
return isThereAWin;
}


public void winner(){
	String theWinner = null;
	if(currentTurn == PenteMain.Black){
		theWinner = "Blackstone Player";
		JOptionPane.showMessageDialog(null,"Black Won");
	}else{
	theWinner = "Whitestone Player";
	JOptionPane.showMessageDialog(null,"White Won");
	}
}
@Override
public void mouseClicked(MouseEvent e) {
	//System.out.println("You Clicked at[" + e.getX() + "," + e.getY() + "].") ;
	playGame(e);
	repaint();
}
	
@Override
public void mouseEntered(MouseEvent e) {
	// TODO Auto-generated method stub
}
@Override
public void mouseExited(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mousePressed(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void mouseReleased(MouseEvent e) {
	// TODO Auto-generated method stub
	
}
public void doPlay(Square s){
	s.setState(currentTurn);
	this.checkForCapture(s);
	this.CheckForWinOnCaptures();
	this.checkForWin2(s);
	//this.winner();//we want this
	this.changeTurn();	

}
public void playGame(MouseEvent e){
	//System.out.println("Hi in playGame at top");
	Square s = findSquare(e.getX(), e.getY());
	System.out.println("The Square is" + s);
		if(s.getState() == PenteMain.Empty){
			//System.out.println("we have a place for the white stone");
			s.setState(currentTurn);
			this.checkForCapture(s);
			this.CheckForWinOnCaptures();
			this.checkForWin2(s);
			//this.winner();//we want this
			this.changeTurn();	
			if(playingAgainstRalph == true && currentTurn == ralphStoneColor){
				//System.out.println("Now planning a move for Ralph");
				Square cs = computerMoveGenerator.doComputerMove(s.getRow(), s.col());
				cs.setState(currentTurn);
				this.repaint();
				this.checkForCapture(cs);
				this.CheckForWinOnCaptures();
				this.checkForWin2(cs);
				//ythis.winner();//we want this
				this.changeTurn();
				this.requestFocus();
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "You cant click on a space with a stone");
		}
}
		
public Square findSquare(int mouseX, int mouseY){

		//System.out.println("Hello this is mouse stuff" + mouseX + mouseY + ".");
		Square clickOnSquare = null;
		for(int col = 0; col < bWidthSquares; ++col)
			for(int row = 0; row < bWidthSquares; ++row)
				if( theBoard[row][col].youClickedMe(mouseX, mouseY) == true){
					clickOnSquare = theBoard[row][col];
	}
		return clickOnSquare;
	}




public int getBoardWidthSquare(){
	return bWidthSquares;
}
public Square[][] getActualGameBoard() {
	// TODO Auto-generated method stub
	return this.theBoard;
}
}