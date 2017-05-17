package Pente;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class RalphHelper {
	GameBoard myBoard;
	int myStoneColor; 
	int boardWidthSquares;
	int opponentStoneColor;
	Square[][] theGameBoard;

	boolean timeToMakeAmove = false;
	boolean moveToMake = false;
	int moveToDealWithRow;
	int moveToDealWithCol;

	ArrayList<OpponentGroup> groups4 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups3 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups2 = new ArrayList<OpponentGroup>();
	ArrayList<OpponentGroup> groups1   = new ArrayList<OpponentGroup>();
	//Ralph is the computer generator for the pente game

	public RalphHelper(GameBoard b, int stoneColor){
		myBoard = b;
		myStoneColor = stoneColor;
		this.setOpponentStoneColor(); 
		boardWidthSquares = b.getBoardWidthSquare();
		theGameBoard = b.getActualGameBoard();
		JOptionPane.showMessageDialog(null,"Hi, Ralph here");
	}
	public void setOpponentStoneColor(){
		if(myStoneColor == PenteMain.Black){
			opponentStoneColor = PenteMain.White;
		}else{
			opponentStoneColor = PenteMain.Black;
		}
	}
public Square doComputerMove(int lastMoveRow, int lastMoveCol ){
	this.assessBoard(lastMoveRow, lastMoveCol);
		
			Square nextMove = null;
			nextMove = this.blockItEverybody(groups4,4);
			if(nextMove == null){
				nextMove = this.blockItEverybody(groups3,3);
				if(nextMove == null){
					nextMove= this.blockItEverybody(groups2, 2);
					if(nextMove == null){
							nextMove=makeARandomMove();
		}
	}
			}
return nextMove;
}
	// Assess Board
	public void assessBoard(int lastMoveRow, int lastMoveCol){
		groups4.clear();
		groups3.clear();
		groups2.clear();
		groups1.clear();
		//System.out.println("In assess board about to call look for groups horizontal");
		this.lookForGroupsHorizontally(lastMoveRow, lastMoveCol);
		this.lookForGroupsVertically(lastMoveRow, lastMoveCol);
		this.lookForGroupsDiagRightInClass(lastMoveRow, lastMoveCol);
		this.doInMiddleCheck(2);
		this.doInMiddleCheck(3);
		this.doInMiddleCheck(4);
		this.captureATwo(groups2, 2);
		this.setupForWin(groups4, 4);
		
		this.lookForGroupsDiagLeftFromClass(lastMoveRow, lastMoveCol);
		//System.out.print("In asses board, looked for Groups");
	}
	public Square findOpponentStartHorizontal(int whatRow, int whatCol){
		Square opponentStart = null;
		boolean done = false;
		int currentCol = whatCol;

		while( !done && currentCol < boardWidthSquares){
			if(theGameBoard[whatRow][currentCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[whatRow][currentCol];
				done = true;
			}
			currentCol++;
		}
		//System.out.println("This is findOpponentStart Horizontal");
		return opponentStart;
	}

	public Square findOpponentStartsVertical(int whatRow, int whatCol){
		Square opponentStart = null;
		boolean done = false;
		int currentRow = whatRow;

		while( !done && currentRow < boardWidthSquares ){
			if(theGameBoard[currentRow][whatCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[currentRow][whatCol]; done = true;
			} currentRow++;

		}
		return opponentStart;
	}

	public Square findOpponentDiagRight(int whatRow, int whatCol, int r){
		Square opponetStart = null;
		boolean done = false;
		int currentCol = whatCol;
		int currentRow = whatRow;

		while(!done && currentCol < boardWidthSquares && currentRow < boardWidthSquares){
			if (theGameBoard[currentRow][currentCol].getState()== opponentStoneColor){
				opponetStart = theGameBoard[currentRow][currentCol];
				done = true;
			}
			currentRow++;
			currentCol++;

		}
		return opponetStart;
	}

	public Square findOpponentStartDiagLeft( int whatRow, int whatCol ){

		//System.out.println();
		//System.out.println("At top of findOpponentStartDiagonalLEFT whatRow is " +
		//		whatRow + " and whatCol is " + whatCol);
		Square opponentStart = null;
		boolean done = false; 
		int currentCol = whatCol;
		int currentRow = whatRow;

		while( !done && currentCol >= 0 && currentRow < boardWidthSquares){

			//	System.out.println("In findOpponentDiagLEFT loop, checking currentRow " + currentRow + " and currentCol  " + currentCol );

			if(theGameBoard[currentRow][currentCol].getState() == this.opponentStoneColor){
				opponentStart = theGameBoard[currentRow][currentCol];
				done = true;	
			}
			currentCol--;
			currentRow++;
		}

		return opponentStart;
	}
	//2A -- LOOK FOR GROUPS HORIZONTALLY  ************
	public void lookForGroupsHorizontally( int lastMoveRow, int lastMoveCol){
		int curCol;
		for( int row = 0 ; row < boardWidthSquares; ++ row){

			curCol = 0;
			while(curCol < boardWidthSquares){
				//Step 1 skip over stones until you find an opponent stone
				Square newStart = findOpponentStartHorizontal( row,  curCol)	;

				if(newStart != null ){
					//Now I can do stuff
					//  Make an object group

					OpponentGroup newGroup = new OpponentGroup( OpponentGroup.HORIZONTAL_GROUP);
					// Add add stone to array
					newGroup.addSquareToGroup(newStart);

					// Check Edge
					int startRow = newStart.getRow();
					int startCol = newStart.col();
					if( startCol <= 0){
						newGroup.setEnd1Square(null);
					} else {
						newGroup.setEnd1Square(theGameBoard[startRow][startCol-1]);
					}

					// Check to see if the current player move is this stone
					if( startRow == lastMoveRow && startCol == lastMoveCol){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength()-1);
					}

					// Start getting neighbors
					startCol++;
					while(startCol < boardWidthSquares  && 
							theGameBoard[startRow][startCol].getState() == this.opponentStoneColor){
						newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);
						if( startRow == lastMoveRow && startCol == lastMoveCol){
							newGroup.setCurrentMoveIsInThisGroup(true);
							newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength()-1);
						}

						startCol++;  // Mr. Roche is a chicken but lives safely...
					}

					// Set the second edge  this is abridged from first edge
					if( startCol >= boardWidthSquares){
						newGroup.setEnd2Square(null);
					} else {
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					}
					curCol = startCol;

					//Finally add this to the group list
					this.addNewGrouptoGroupLists(newGroup);

				} else {
					// If startSquare is null
					// this forces it to end the loop
					curCol = boardWidthSquares;

				}
			}	
		}	
	}
	//Vertical Check
	public void lookForGroupsVertically( int lastMoveRow, int lastMoveCol){

		for( int col = 0; col < boardWidthSquares; ++col ){

			int curRow = 0;

			while(curRow < boardWidthSquares){
				Square groupStart = findOpponentStartsVertical( curRow,  col );
				if(groupStart != null){
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.Vertical_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.col();
					if(startRow <= 0){
						newGroup.setEnd1Square(null);
					} else {
						newGroup.setEnd1Square(theGameBoard[startRow-1][col]);

					}

					curRow = groupStart.getRow() + 1;

					//load the Squares of the opponent group until there is an end
					boolean done = false;
					while(curRow < boardWidthSquares && !done){
						if(theGameBoard[curRow][col].getState() == this.opponentStoneColor){
							//this method in squareGroup handles adding length etc...
							newGroup.addSquareToGroup(theGameBoard[curRow][col]);
							curRow++;
						} else {
							done=true;
						}
					}

					// if there is an end set the end edge
					if(curRow >= boardWidthSquares){
						newGroup.setEnd2Square(null);
					} else {
						newGroup.setEnd2Square(theGameBoard[curRow][col]);
					}

					// based on the length of the opponent group add it to the lists.
					this.addNewGrouptoGroupLists(newGroup);


				} else {
					curRow = boardWidthSquares;
				}
			}

		}
	}



	public void lookForGroupsDiagRightInClass( int lastMoveRow, int lastMoveCol ){
		//Do Part 1 of the Diagonal...
		for(int row = 0 ; row < boardWidthSquares; ++row ){
			int curCol = 0;
			int curRow = row;
			while(curCol < boardWidthSquares - row && curRow < boardWidthSquares) {

				Square groupStart = findOpponentDiagRight( curRow,  curCol, 0);

				if( groupStart != null ) {	
					//You have a start so set up a new group!
					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.Diag_Right_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.col();

					// Check first edge
					if(startRow - 1 >= 0 && startCol - 1 >= 0) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol-1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol++;
					startRow++;
					boolean done = false;

					while( startCol < boardWidthSquares - row && startRow < boardWidthSquares && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol++;
						} else {
							done = true;
						}	
					}	
					//check other edge
					if(startRow + 1 < boardWidthSquares && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd2Square(theGameBoard[startRow+1][startCol+1]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGrouptoGroupLists(newGroup);

				} else {
					//get out of loop!!
					curRow = boardWidthSquares;
				}	
			}

		}



		//Do Part 2 of the Diagonal
		for(int col = 1 ; col < boardWidthSquares; ++col ){

			int curCol = col;
			int curRow = 0;

			while(curRow < boardWidthSquares - col && curCol < boardWidthSquares) {

				Square groupStart = findOpponentDiagRight( curRow,  curCol, 0);

				if(groupStart != null){

					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.Diag_Right_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.col();

					// Check first edge  same problem so same code from above should work...
					if(startRow - 1 >= 0 && startCol - 1 >= 0) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol-1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol++;
					startRow++;
					boolean done = false;

					while( startCol < boardWidthSquares  && startRow < boardWidthSquares-col && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol++;
						} else {
							done = true;
						}		
					}

					//check other edge
					if(startRow + 1 < boardWidthSquares && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd2Square(theGameBoard[startRow+1][startCol+1]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGrouptoGroupLists(newGroup);


				} else {

					//get out of loop
					curCol = boardWidthSquares;

				}
			}
		}	
	}
	// Left Diagnol
	public void lookForGroupsDiagLeftFromClass( int lastMoveRow, int lastMoveCol ){
		//Do Part 1 of the Diagonal...
		for(int row = 0 ; row < boardWidthSquares; ++row ){

			int curCol = boardWidthSquares-1; 
			int curRow = row;


			while(curCol >= row  && curRow < boardWidthSquares) { 

				Square groupStart = findOpponentStartDiagLeft( curRow,  curCol);

				if( groupStart != null ) {	
					//You have a start so set up a new group!
					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", " + 
					//groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.Diag_Left_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.col();

					// Check first edge
					if(startRow - 1 >= 0 && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol+1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol--;  
					startRow++;
					boolean done = false;

					while( startCol >= row && startRow < boardWidthSquares && !done){
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol--;
						} else {
							done = true;
						}	
					}	
					//check other edge
					if(startRow < boardWidthSquares && startCol >= 0) {
						newGroup.setEnd2Square(theGameBoard[startRow][startCol]);
					} else {
						newGroup.setEnd2Square(null);

					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGrouptoGroupLists(newGroup);

				} else {
					//get out of loop!!
					curRow = boardWidthSquares;
					curCol = row-1; 
				}	
			}	
		}

		//System.out.println("Start of second part of diagonal");
		//Do Part 2 of the Diagonal
		for(int col = boardWidthSquares-2 ; col >= 0; --col ){

			int curCol = col;
			int curRow = 0;

			//System.out.println("At start of searching loop cur row is " + curRow + " and curCol is " 
			//+ curCol);


			while(curRow <= col  && curCol >= 0) {	

				//System.out.println("Right before findOpponentStartDiagLeft curRow and col are "
				// + curRow + ",  " + curCol);
				Square groupStart = findOpponentStartDiagLeft( curRow,  curCol);

				if(groupStart != null){

					//System.out.println ("Hi I found a group start at " + groupStart.getRow() + ", "
					// + groupStart.getCol() );
					OpponentGroup newGroup = new OpponentGroup(OpponentGroup.Diag_Left_GROUP);
					newGroup.addSquareToGroup(groupStart);
					int startRow = groupStart.getRow();
					int startCol = groupStart.col();

					// Check first edge  same problem so same code from above should work...
					if(startRow - 1 >= 0 && startCol + 1 < boardWidthSquares) {
						newGroup.setEnd1Square(theGameBoard[startRow-1][startCol+1]);
					} else {
						newGroup.setEnd1Square(null);

					}
					//see if current move is part of this group
					if( startRow == lastMoveRow && startCol == lastMoveCol ){
						newGroup.setCurrentMoveIsInThisGroup(true);
						newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
					}

					//look for additional group members
					startCol--;  // startCol[__];
					startRow++;
					boolean done = false;

					while( !done && startCol >=0  && startRow < boardWidthSquares ){	
						if(theGameBoard[startRow][startCol].getState() == this.opponentStoneColor ) {
							newGroup.addSquareToGroup(theGameBoard[startRow][startCol]);		
							if( startRow == lastMoveRow && startCol == lastMoveCol ){
								newGroup.setCurrentMoveIsInThisGroup(true);
								newGroup.setCurrentMoveArrayListLocation(newGroup.getGroupLength());
							}

							startRow++;
							startCol--;
						} else {
							done = true;
						}		
					}

					//check other edge
					if(startRow + 1 < boardWidthSquares && startCol - 1 >= 0) {
						newGroup.setEnd2Square(theGameBoard[startRow + 1][startCol - 1]);
					} else {
						newGroup.setEnd2Square(null);
					}

					//Important to stop infinite loop
					curCol = startCol;
					curRow = startRow;
					//add group to list
					this.addNewGrouptoGroupLists(newGroup);


				} else {

					//get out of loop
					curCol = -1;

				}
			}
		}		
	}
	private void addNewGrouptoGroupLists(OpponentGroup ng) {
		switch(ng.getGroupLength()){
		case 1:
			groups1.add(ng);
			break;
		case 2:
			System.out.println( "I have a " + ng.getOpponentGroupType()+ " Group with two opponents stone");
			groups2.add(ng);
			break;
		case 3:
			System.out.println( "I have a " + ng.getOpponentGroupType()+ " Group with three opponents stone");
			groups3.add(ng);
			break;
		case 4:
			System.out.println( "I have a " + ng.getOpponentGroupType()+ " Group with four opponents stone");
			groups4.add(ng);
			break;
		default:
			System.out.println( "I have a " + ng.getOpponentGroupType()+ " Group with " + ng.getGroupLength() + " opponent stones");
			System.out.println("something is really messed up");
			break;
		}	
	}

	public String getMiddleGroupText(int dx, int dy, int groupSize){
		String gs = "";
		if(groupSize == 4){
			gs = "4";
		} else {
			gs = "3";
		}
		String theType = "";
		if(dx == 1){
			if(dy == 1) theType = "Diag Right";
			if(dy == 0) theType = "Horizontal";
			if(dy == -1) theType = "Diag Left";
		} else {
			theType = "Vertical";
		}

		return "Middle " + gs + ": " + theType;
	}


public void checkForBlockInMiddle(int row, int col, int groupSize){

		boolean done = false;
		int[] myDys = {-1, 0, 1};
		int whichDy = 0;

		while(!done && whichDy < 3){
			checkForBlockInMiddleAllAround(row, col, groupSize, myDys[whichDy], 1 );
			whichDy++;
		}
		checkForBlockInMiddleAllAround(row, col, groupSize, 1, 0 );	
	}
public void checkForBlockInMiddleAllAround(int row, int col, int groupSize, int dy, int dx)
	{

		int sRow = row;
		int sCol = col;

		//for a right-check and left...
		int howManyRight = 0;
		int howManyLeft = 0;

		//loop to check right side of where stone s is
		int step = 1;
		//System.out.println("In checkForWinAllInOne sRow and sCol are [" + sRow + ", " + sCol + "]");
		//System.out.println("In checkForWinAllInOne dy and dx are [" + dy + ", " + dx + "]");
		while((sCol + (step * dx) < boardWidthSquares) && (sRow + (step * dy) < boardWidthSquares) &&
				(sCol + (step * dx) >= 0) && (sRow + (step * dy) >= 0) &&
				(theGameBoard[sRow + (step * dy)][sCol + (step * dx)].getState() == this.opponentStoneColor)){
			howManyRight++;
			step++;
		}
		//Moving Left....
		step = 1;
		while((sCol - (step * dx) >= 0) &&  (sRow - (step * dy) >= 0) &&
				(sCol - (step * dx) < boardWidthSquares) && (sRow - (step * dy) < boardWidthSquares) &&
				(theGameBoard[sRow - (step * dy)][sCol - (step * dx)].getState() == this.opponentStoneColor)){
			howManyLeft++;
			step++;
		}


		if((howManyRight + howManyLeft) >= groupSize){
			//If you have this then you want to set Up an Opponent group for this
			//System.out.println("For square at " + row + ", " + col + " we have group of size of " + (howManyRight + howManyLeft));
			OpponentGroup newGroup;
			if( groupSize == 4 ) {
				String middleGroupText = getMiddleGroupText(dx, dy, 4);
				System.out.println("this is middlegroupText" + middleGroupText);
				newGroup = new OpponentGroup(OpponentGroup.MIDDLE_4_GROUP);
				newGroup.setGroupRanking(4);
				newGroup.setGroupLength(4);
				newGroup.setGroupTypeText(middleGroupText);
			} else {
				String middleGroupText = getMiddleGroupText(dx, dy, 3);
				newGroup = new OpponentGroup(OpponentGroup.MIDDLE_3_GROUP);
				newGroup.setGroupRanking(3);
				newGroup.setGroupLength(3);
				newGroup.setGroupTypeText(middleGroupText);
			}

			newGroup.setInMiddleGroup(true);
			newGroup.setInMiddleGroupSquare(theGameBoard[row][col]);
			this.addNewGrouptoGroupLists(newGroup);		
		}	
	}
	//New added May2 for in Middle Checks
	public void doInMiddleCheck( int groupSize ){
		for(int row = 0; row < boardWidthSquares; ++row){
			for(int col = 0; col < boardWidthSquares; ++col){
				if(theGameBoard[row][col].getState() == PenteMain.Empty){
					checkForBlockInMiddle(row, col, groupSize);
				}
			}
		}
	}
	//block a win 
private Square blockIt(ArrayList <OpponentGroup> whatGroup) {

		Square nextMove = null;
		System.out.println("In block a four grousp4 is " + groups4.size());
		if(groups4.size()> 0){
			boolean done = false;
			int groupIndex = 0;

			while(!done && groupIndex < groups4.size()){
				Square e1 = groups4.get(groupIndex).getEnd1Square();
				Square e2 = groups4.get(groupIndex).getEnd2Square();

				if(e1.getState() == PenteMain.Empty && e2.getState() == PenteMain.Empty){
					int r =(int) (Math.random()*100);
					if( r < 50){
						nextMove = e1;

					}else{
						nextMove = e2;
					}
					done = true;
				}
				
				else{
					if(e1.getState() == PenteMain.Empty){
						nextMove = e1;
						done = true;
					}else{
					if (e2.getState()== PenteMain.Empty){
						nextMove = e2;
						done = true;
					}
						
					}
				groupIndex++;
				}	
			}
return nextMove;
		}

		return null;
	}

	public Square blockItEverybody(ArrayList<OpponentGroup> whatGroup, int whatGroupSize){
		
		Square nextMove = null;
		//Your code here
		System.out.println("In BlockIt for this group the size is " + whatGroup.size() );
		
		if(whatGroup.size() > 0){
			
			boolean done = false;
			int groupIndex = 0;
			
			while(!done && groupIndex < whatGroup.size()){
				 OpponentGroup currentGroup = whatGroup.get(groupIndex);
				 Square e1 = whatGroup.get(groupIndex).getEnd1Square();
				 Square e2 = whatGroup.get(groupIndex).getEnd2Square();
			
				 System.out.println("At top of loop in BlockIt and groupIndex is " + groupIndex);
				 
				 groupIndex++;
		
				 
				 if(currentGroup.getInMiddleGroupStatus() == true){
					 nextMove = currentGroup.getInMiddleGroupSquare();
					 
				 } else {
					  
					 // So here we now know that e1 is empty...
					 if((e1 != null && e1.getState() == PenteMain.Empty ) && (e2 != null&& e2.getState() == PenteMain.Empty )){
						 int r = (int)(Math.random() * 100);
						 if(r > 50){
							 nextMove = e1;
						 } else {
							 nextMove = e2;
						 }
						 done=true;
					 }else{
						 if(whatGroupSize == 4){
						 if (e1 != null && e1.getState() == PenteMain.Empty) {
							 nextMove = e1;
							 done = true;
							 
						 } else {
							 if(e2 != null && e2.getState() == PenteMain.Empty){
								 nextMove = e2;
								 done = true;
							 }
						 }
						 
					 }
				 }
			}
		}
		
		}
		return nextMove;
		
	}
public Square specialProcessingForThree(OpponentGroup g) {
		Square squareToReturn = null;
		
		
		
		
		
		return squareToReturn; 
		
		
		
	}

public Square captureATwo(ArrayList<OpponentGroup> whatGroup, int whatGroupSize){
	Square nextMove = null;
	
	if(groups2.size()>0){
		boolean done = false;
		int groupIndex = 0;
		
		while(!done && groupIndex < groups2.size()){
			OpponentGroup currentGroup = groups2.get(groupIndex);
			Square e1 = groups2.get(groupIndex).getEnd1Square();
			Square e2 = groups2.get(groupIndex).getEnd2Square();
		System.out.println("In captureATwo" + groupIndex);
		
		groupIndex++;
		
		//Your code here
		
		//if groups2.size 
		 if(currentGroup.getInMiddleGroupStatus() == true){
			 nextMove = currentGroup.getInMiddleGroupSquare();
			 
		 } else {
			 if((e1 != null && e1.getState() == PenteMain.Empty ) && (e2 != null&& e2.getState() == PenteMain.Empty )){
				 int r = (int)(Math.random() * 100);
				 if(r > 50){
					 nextMove = e1;
				 } else {
					 nextMove = e2;
				 }
				 done=true;
			 }else{
				 if(whatGroupSize == 2){
				 if (e1 != null && e1.getState() == PenteMain.Empty) {
					 nextMove = e1;
					 done = true;
					 
				 } else {
					 if(e2 != null && e2.getState() == PenteMain.Empty){
						 nextMove = e2;
						 done = true;
					 }
				 }
				 
			 }
		 }
	}
		 }
		
		}
	return nextMove;
}
	//To capture
	private Square getACapture(int lastMoveRow, int lastMoveCol) {
		
		
		
		return null;
	}
	// This is to set up for a capture
	public Square setupForWin(ArrayList<OpponentGroup> whatGroup, int whatGroupSize) {
		Square nextMove = null;
		if(groups4.size()==4){
			System.out.println("WE HAVE A WINNER");
		}
		return nextMove;
	}
	//Random Move
	public Square makeARandomMove(){
		
		int newMoveRow, newMoveCol; 
		do{
			newMoveRow = (int) (Math.random()*boardWidthSquares);
			newMoveCol = (int) (Math.random()*boardWidthSquares);
		} while(theGameBoard[newMoveRow][newMoveCol].getState() != PenteMain.Empty);
		return (theGameBoard[newMoveRow][newMoveCol]);	
	}

	
	
	public ArrayList<OpponentGroup> getVanellopeGroups4(){
		return groups4;
	}
	public ArrayList<OpponentGroup> getVanellopeGroups3(){
		return groups3;
	}
	public ArrayList<OpponentGroup> getVanellopeGroups2(){
		return groups2;
	}
	
}
