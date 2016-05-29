import java.util.*; //Bring in for scanner and map
public class AVLTree {
	private AVLNode root; //Make a private root node
	Map<Integer, ArrayList<Integer>> print = new HashMap<Integer,ArrayList<Integer>>();//Make a map to store all of the data
	public AVLTree(){//Constructor for AVLTree
		AVLNode root = null;//Allow for the root to be null
	}
	public boolean isEmpty(){//If the root is empty, return null
		return root == null;
	}
	public void makeEmpty(){//Make empty by making the root null
		root = null;
	}
	public void Insert(int datavalue){//Recursive helper for Insert
        root = Insert(datavalue, root);//Takes the passed value and passes it to the recursive fuction along with the root
	}
	public AVLNode Insert(int datavalue, AVLNode traverse){	//Recursive insert function
		if (traverse == null){//If the node passed is null
            traverse = new AVLNode(datavalue);//Make a new node at that location with the passed data
        }
		else if (datavalue < traverse.data)//If the data at the passed node is greater than the data passed
        {
            traverse.left = Insert(datavalue, traverse.left);//Go left and recurse with the left node
            if(height(traverse.left) - height(traverse.right) >= 2){//If the difference between the height of the left and the right is greater than or equal to 2 (should never actually be greater than 2)
                if(datavalue < traverse.left.data )//If the left data is greater than the passed data value
                    traverse = rotatewithLeft(traverse); //Make traverse equal to the result of rotating left
                else//Otherwise
                    traverse = doubleRotateWithLeft(traverse);//Make traverse equal to the result of double rotating left
            }
        }
        else if(datavalue > traverse.data){//If the data at the passed node is less than the data passed
           traverse.right = Insert(datavalue, traverse.right);//Go right and recurse with the right node
            if(height(traverse.right) - height(traverse.left) >= 2){//If the difference between the height of right and the left is greater than or equal to 2
                if(datavalue > traverse.right.data)//If the passed data is greater than the right data
                    traverse = rotatewithRight(traverse);//Make traverse equal to the result of rotating right
                else//Otherwise
                    traverse = doubleRotateWithRight(traverse);//Make traverse equal to the result of double rotating right
            }
        }
		traverse.height = Math.max(height(traverse.left), height(traverse.right)) + 1;//Make the height the maximum distance from the bottom
        
		return traverse;//Return the traverse node
	}
	public boolean search(int keyvalue){
		AVLNode traverse = root;//Points to the root
		while(traverse.left != null || traverse.right != null){//While you can go left or right
			if(traverse.data > keyvalue)//If it's smaller than the current value
				traverse = traverse.left;//Go left
			else if(traverse.data < keyvalue)//Otherwise
				traverse = traverse.right;//Go right
		}
		if(traverse.data == keyvalue)//If the value is found, return true
			return true;
		return false;//If it's not found, return false
	}

	public void delete(int datavalue){//Deletes by reconstructing the entire structure
		print.clear(); //Clear the data cache
		AVLNode seeker = root;//Point to the root
		deleteassist(seeker,0);//Use recursive function at root and level 0
		root = null;//Make the root empty
		for(ArrayList<Integer> iterate: print.values()){//Iterate over all of the arraylists
			for(Integer iterable: iterate){//Iterate over all of the integer
				if(iterable != datavalue)//If the value is not equal to the passed value
					root = Insert(iterable, root);//Add it to the new tree
			}
		}
	}
	public int height(){//Returns the height of the root
		return root.height;
	}

	public int height(AVLNode t){//Returns the height of any given node
		if(t == null)//If the root is null
			return -1;
		else //Otherwise, return the maximum of recurring left or right
			return 1 + Math.max(height(t.left), height(t.right));
	}
	public AVLNode rotatewithLeft(AVLNode traverse){//Rotates the passed node with the one to its left
        AVLNode traverse1 = traverse.left;//Make a placeholder node equal to the left of the passed node
        traverse.left = traverse1.right;//Make the left of the passed node equal to the right of the placeholder node
        traverse1.right = traverse;//Make the right of the placeholder node equal to the passed node;
        traverse.height = Math.max(height(traverse.left), height(traverse.right)) + 1; //Make the height of the passed node equal to its maximum distance from the bottom
        traverse1.height = Math.max(height(traverse1.left),traverse.height) + 1;//Make the height of the placeholder node equal to its maximum distance from the bottom
        return traverse1;//Return the placeholder node
	}
	public AVLNode rotatewithRight(AVLNode traverse){//Rotates the passed node with the one to its right
		AVLNode traverse1 = traverse.right;//Make a placeholder node equal to the right of the passed node
	    traverse.right = traverse1.left;//Make the right of the passed node equal to the left of the placeholder node
	    traverse1.left = traverse;//Make the left of the placeholder node equal to the passed node
        traverse.height = Math.max( height(traverse.left), height(traverse.right)) + 1;//Make the height of the passed node equal to its maximum distance from the bottom
        traverse1.height = Math.max(height(traverse1.right),traverse.height) + 1;//Make the height of the placeholder node equal to its maximum distance from the bottom
	    return traverse1;//Return the placeholder node
	}
	public AVLNode doubleRotateWithLeft(AVLNode traverse){//Rotates twice to the left
		traverse.left = rotatewithRight(traverse.left);//Make the left of the passed node equal to the result of rotating it to the right
		return rotatewithLeft(traverse);//Return the result of the placeholder being rotated left
	}
	public AVLNode doubleRotateWithRight(AVLNode traverse){//Rotates twice to the right
		traverse.right = rotatewithLeft(traverse.right);//Make the right of the passed node equal to the result of rotating it to the left
		return rotatewithRight(traverse);//Return the result of the placeholder being rotated right
	}
	public void print_tree(){//Recursive helper to print tree
		print_tree(root, 0);//Start by passing the root, and level 0
	}
	public void print_tree(AVLNode traverse, int level){//Recursive function that takes a node and its level to print the tree
		if(traverse == null)
			return;
		for(int i = 0; i < level; i++){//Prints 4 spaces for each level
			System.out.print("    ");
		}
		System.out.println(traverse.data);//Prints the data of the passed node
		if(traverse.left != null)//If the left is not null
			print_tree(traverse.left, level + 1);//Recurse on it
		if(traverse.right != null)//If the right is not null
			print_tree(traverse.right, level + 1);//Recurse on it
	}
	public void deleteassist(AVLNode proot, int level){ //Recursive storage function
		if(print.get(level) == null)//If nothing exists at that level yet
			print.put(level, new ArrayList<Integer>());//Make it a new level
		print.get(level).add(proot.data);//Add whatever element is there to the passed level
		if(proot.left != null)//If anything is to the left
			deleteassist(proot.left,level+1);//Recurse on the left at another level
		if(proot.right != null)//If anything is to the right
			deleteassist(proot.right,level+1);//Recurse on the right at another level
	}
}
class tester{
	public static void main(String[] args){

		Scanner in = new Scanner(System.in);//Creates a scanner for user input
		AVLTree trial = new AVLTree();//Creates an empty tree
		System.out.println("Choose one of the following operations:");//Prompts user to choose an option
		System.out.println("-insert (enter the number 1)");
		System.out.println("-search (enter the number 2)");
		System.out.println("-delete (enter the number 3)");
		System.out.println("-print (enter the number 4)");
		System.out.println("-height of nodes (enter the number 5)");
		System.out.println("-check empty (enter the number 6)");
		System.out.println("-clear tree (enter the number 7)");
		System.out.println("-Quit (enter the letter q)");
		char userInput = in.nextLine().charAt(0);//Notes the user's choice
		do{
			switch (userInput) {//Switch statement utilizing user input
				case '1': System.out.println("Enter the number you want to insert:");//Prompts user to choose number to insert
						int inserted = in.nextInt();
						trial.Insert(inserted);//Inserts whatever they chose to insert
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						in.nextLine();
						userInput = in.nextLine().charAt(0);
						break;
				case '2':System.out.println("Enter the number you want to search for:");//Prompts user to search for a number
						int searched = in.nextInt();
						System.out.println("The tree contains: " + searched + "? :" + trial.search(searched));//Tells them if the tree contains the searched number
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						in.nextLine();
						userInput = in.nextLine().charAt(0);
						break;
				case '3':System.out.println("Enter the number you want to delete:");//Prompts user to choose a number to delete
						int deletee = in.nextInt();
						trial.delete(deletee);//Attempts to delete whatever they asked to delte
						System.out.println("If your number was in the tree, it was deleted");
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						in.nextLine();
						userInput = in.nextLine().charAt(0);
						break;
				case '4': trial.print_tree();//Prints the tree
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case '5': System.out.println(trial.height()); //Returns the height of the tree
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case '6': System.out.println(trial.isEmpty()); //Tells whether the tree is empty or not
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case '7': trial.makeEmpty();//Makes the tree empty
						System.out.println("The tree was emptied");
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
				case 'q': System.out.println("quitting"); //Quit if the user hits the quit key
						break;
				default: System.out.println("Invalid choice"); //If they choose a different character, let them know that's not allowed, strictly speaking
						System.out.println("Make a choice from the menu");//Prompts another choice to be made
						userInput = in.nextLine().charAt(0);
						break;
			}
		}while(userInput != 'q');////As long as the user does not choose q
	}
}