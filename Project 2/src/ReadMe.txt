AVLTree.java
This AVLTree is fairly self explanatory, it will run and prompt the user to engage with it as they will.
This occurs via the tester class, which implements a switch statement in order to take user input.
However, it can be utilized to create an AVLTree outside of the tester class.
An AVLTree can be implemented by using the statement AVLTree name = new AVLTree();
name.isEmpty() will tell whether the tree is empty or not.
name.makeEmpty() will make the tree empty.
name.Insert(int) will take an integer and insert it into the tree.
name.search(int) will take an integer and check whether or not the tree contains the integer.
name.delete(int) will take an integer and delete it, if the tree contains the integer.
name.height(node) will take a node and tell the user its height.
name.rotatewithLeft(node) will take a node and rotate it with its left child.
name.rotatewithRight(node) will take a node and rotate it with its right child.
name.doubleRotateWithLeft(node) will take a node and rotate it with its left grandchild.
name.doubleRotateWithRight(node) will take a node and rotate it with its right grandchild.
name.print_tree() will recursively print the tree.
deleteassist is a helper method that stores values to be kept and then reconstructs the tree with those values.
height() is a method to return the overall height without making the height of the root public.


AVLNode.java
AVLNode is a very simple node implementation that can be used for basically any tree.
It contains two constructors, one for if you don't pass a parameter, one for if you do.
It contains variables for a left node, a right node, data, and height.
A node can be created with the call AVLNode name = new AVLNode() or AVLNode name = new AVLNode(int).  