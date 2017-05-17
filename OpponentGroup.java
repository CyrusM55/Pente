package Pente;
import java.util.ArrayList;
public class OpponentGroup {
	
//Adding Literal Codes for each type of stone group we can have
public static final int HORIZONTAL_GROUP = 1;
public static final int Vertical_GROUP = 2;
public static final int Diag_Right_GROUP = 3;
public static final int Diag_Left_GROUP = 4;


ArrayList <Square> groupList;
int groupLength = 0;
int groupRanking = 0;
Square end1Square = null;
Square end2Square = null;

int groupType;
String groupTypeText;

boolean currentMoveIsInGroup = false;
int currentMoveArrayListLoaction = -1;

public OpponentGroup (int gt) {
		groupList = new ArrayList<Square>();
		System.out.println("In Opponment Group -- just made a new group");
		groupType = gt;
		this.setGroupTypeToString();
	}
	
public void addSquareToGroup( Square whichSquare){
		groupList.add(whichSquare);
		groupLength++;
		groupRanking++;
	}
public void setEnd1Square(Square whatSquare){
		end1Square=whatSquare;
	}
public void setEnd2Square(Square whatSquare){
		end2Square = whatSquare;
	}
public ArrayList<Square>getGroupList(){
		  return groupList;
	}
public Square getEnd1Square(){
		return end1Square;
	}
public Square getEnd2Square(){
		return end2Square;
	}
public int getGroupLength(){
		return groupLength;
	}
public int getGroupRanking(){
		return groupRanking;
	}
public void setGroupRanking(int newRanking){
		groupRanking = newRanking;
	}
public int getOpponentGroupType(){
		return groupType;}
public int currentMoveArrayListLocation(){
		return groupLength;
	}




// important
public String getGroupTypeText(){
	return groupTypeText;
}
//
public void setGroupTypeText(String groupType){
	groupType = groupTypeText;
}

public void setGroupLength( int number){
	groupLength = number;
}
public void setCurrentMoveIsInThisGroup (boolean setting){
		currentMoveIsInGroup = true;
	}
public boolean getCurrentMoveIsInGroup(){
		return currentMoveIsInGroup;
	}
public void setCurrentMoveArrayListLocation (int arrayListIndex){
		currentMoveArrayListLoaction = arrayListIndex;
	}
public int getArrayListSizeFromArray(){
		return groupList.size();
	}
public void setGroupTypeToString(){
		switch(groupType){
		case MIDDLE_3_GROUP:
			groupTypeText = "Middle - 3";
			break;
		case MIDDLE_4_GROUP:
			groupTypeText = "Middle - 4";
			break;
		case HORIZONTAL_GROUP:
			groupTypeText = "Horizontal";
			break;
		case Vertical_GROUP:
			groupTypeText = "Vertical";
			break;
		case Diag_Right_GROUP : 
			groupTypeText = "Diagonal Right";
			break;
		case Diag_Left_GROUP : 
			groupTypeText = "Diagonal Left";
			break;
		default:
			groupTypeText = "Something is messed up";
			break;
			} 
	
	}
public void setInMiddleGroupSquare(Square whatSquare){
	inMiddleSquare = whatSquare ; 
}
public boolean getInMiddleGroupStatus(){
	return inMiddleGroup;
}

public void setInMiddleGroup(boolean value){
	inMiddleGroup = value;
}
public Square getInMiddleGroupSquare(){
	return inMiddleSquare;
}
private boolean inMiddleGroup = false;
private Square inMiddleSquare = null;
public static final int MIDDLE_4_GROUP = -4;
public static final int MIDDLE_3_GROUP= -3;


}
	
	
	
