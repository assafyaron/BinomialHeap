import java.util.ArrayList;

/**
 * BinomialHeap
 *
 * An implementation of binomial heap over non-negative integers.
 * Based on exercise from previous semester.
 */
public class BinomialHeap
{
	public int size;
	public HeapNode last;
	public HeapNode min;
	public ArrayList<HeapNode> subTreesArray;

	// Constructor
	public BinomialHeap() {
		this.size = 0;
		this.last = null;
		this.min = null;
		this.subTreesArray = new ArrayList<HeapNode>();
	}

	/**
	 * 
	 * pre: key > 0
	 *
	 * Insert (key,info) into the heap and return the newly generated HeapItem.
	 *
	 */
	public HeapItem insert(int key, String info) {

		// First create a new Item and HeapNode using arguments.
		// Next, check if the heap is empty or the B0 slot is available. If so, just enter the new node into its place,
		// and update size, last and min (if needed). Else, call singleMeld helper funtction to initate a meld chain. 

		// RUNTIME: O()

		HeapItem newHeapItem = new HeapItem(key, info);
		HeapNode newNode = new HeapNode(newHeapItem);

		// If a tree of rank 0 doesn't exist, or the Heap is empty - just insert a single node (tree of rank 0)
		if ((this.empty()) || (this.subTreesArray.get(0) == null)) {

			if (this.size() == 0) { // First node in our heap
				this.subTreesArray.add(0, newNode); // Add a new rank to array
				this.last = newNode;
			}
			else { // Else, only update the current rank
				this.subTreesArray.set(0, newNode);
			}
		}

		// Else, call a meld chain to connect each two trees of the same rank
		else {
			singleMeld(newNode, 0);
		}

		// Update size
		this.size++;

		// Update minimum if needed
		if ((this.min == null) || (this.min.getKey() > key)) {
			this.min = newNode;
		}

		return newHeapItem;
	}

	/**
	 * 
	 * Delete the minimal item
	 *
	 */
	public void deleteMin() {

		//

		// RUNTIME: 

		// iniate a subTree arrayList to create a new heap holding all of the min's children
		ArrayList<HeapNode> newHeapSubTreeArray = new ArrayList<>();
		HeapNode currChild = this.min.getChild();
		while (currChild != null) {
			newHeapSubTreeArray.add(0, currChild);

			// move up the chain of brothers of the root
			currChild = currChild.getNext();
		}

		// create a new binomial heap using the newHeapSubTreeArray
		BinomialHeap newHeap = new BinomialHeap();
		double temp = Math.pow(2, newHeapSubTreeArray.size());
		newHeap.size = (int) temp - 1;
		newHeap.subTreesArray = newHeapSubTreeArray;

		// update our heap's size
		this.size -= newHeap.size()+1;

		// zero the minimum's sub tree in our heap
		int minIdx = this.min.getRank();

		// if the minimum is the biggest rank tree in our heap, delete its index from the list
		if (minIdx == this.subTreesArray.size()-1) {
			this.subTreesArray.remove(minIdx);
		}
		// else, just set the root to null
		else {
			this.subTreesArray.set(minIdx, null);
		}

		// update our heap's minimum and last if needed
		this.updateMin();
		this.updateLast();
		

		// meld the new heap with the original one
		this.meld(newHeap);
	}

	/**
	 * 
	 * Update last after removing minimum
	 */
	public void updateLast() {

		// iterate from the currRoot down the ranks until we find a non-null root

		// RUNTIME: O(log(n))
		
		// we deleted the only node in the heap thus now last is null
		if (this.empty()) {
			this.last = null;
		}

		else {
			this.last = this.subTreesArray.get(this.subTreesArray.size()-1);
		}
	}


	/**
	 * 
	 * Update minimum after removing one
	 */
	public void updateMin() {

		// iterate over all roots and check who is the smallest

		// RUNTIME: O(log(n))
		
		// Edge case - heap is empty
		if (this.empty()) {
			this.min = null;
		}

		else {

			// initalizing variables
			double minKey = Double.POSITIVE_INFINITY;
			HeapNode currRoot;

			for (int i=0; i<this.subTreesArray.size();i++) {
				
				currRoot = this.subTreesArray.get(i);

				if (currRoot != null) {

					if (currRoot.getKey() < minKey) {
						this.min = currRoot;
						minKey = currRoot.getKey();
					}
				}
			}
		}
	}

	/**
	 * 
	 * Return the minimal HeapItem
	 *
	 */
	public HeapItem findMin()
	{
		return this.min.getItem(); // should be replaced by student code
	} 

	/**
	 * 
	 * pre: 0 < diff < item.key
	 * 
	 * Decrease the key of item by diff and fix the heap. 
	 * 
	 */
	public void decreaseKey(HeapItem item, int diff) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Delete the item from the heap.
	 *
	 */
	public void delete(HeapItem item) 
	{    
		return; // should be replaced by student code
	}

	/**
	 * 
	 * Meld the heap with heap2
	 *
	 */
	public void meld(BinomialHeap heap2) {

		// 1. if heap2 is empty - do nothing
		// 2. if heap2 is one node - insert it to our heap using insert func
		// 3. if heap2.size() >= 2 - use a chain of single melds to add each rank's root in heap2 to our heap

		// RUNTIME: O()

		// heap2 is empty
		if (heap2.empty()) {
			
			// do nothing
		}
		// We are melding between single node heap and a binomial heap
		else if (heap2.size() == 1) {
			HeapNode joiningNode = heap2.subTreesArray.get(0);
			insert(joiningNode.getKey(),joiningNode.getInfo());
		}

		// heap2.size() >= 2
		else {
			HeapNode currRoot;

			// call single meld on the root of each rank tree if it exists in heap2
			for (int i=0; i<heap2.subTreesArray.size();i++) {
				currRoot = heap2.subTreesArray.get(i);
				if (currRoot != null) {
					singleMeld(currRoot, i);

					// update minimum if needed
					if ((this.min == null) || (currRoot.getKey() < this.min.getKey())) {
						this.min = currRoot;
					}
				}
			}

			// update size
			this.size += heap2.size();
		}

		// update last if neeeded
		this.updateLast();
	}

	/**
	 * 
	 *
	 */
	public void singleMeld(HeapNode newNode, int indexStart) {

		// This is a helper function for insert and meld functions.
		// It will be called on when:
		// 1. inserting a new node will cause a chain meld reaction.
		// 2. we are melding our heap with another heap with a size >= 2.
		// Iterate up the subTrees array from given index until we find an empty slot while melding trees on the way using helper function uniteNodes.
		// After melding two trees, zero there previous rank and continue up the ranks.
		// Finally, make sure to update last root if we've excceded the previous rank of the heap.

		// RUNTIME: O()

		// Starting meld chain:
		// 1. Start index counter and pointers to initial nodes
		// 2. Iterate until we reach a null rank, meaning we have no more trees to meld in chain
		int indexCnt = indexStart;
		HeapNode prevNode = newNode;
		HeapNode currNode = this.subTreesArray.get(indexCnt);

		while (currNode != null) {

			// Prev node is minimal, thus becomes the new root
			if (prevNode.getKey() < currNode.getKey()) {
				uniteNodes(prevNode, currNode);
			}

			// Curr node is minimal, thus we add the prev one as it's child
			else if (prevNode.getKey() >= currNode.getKey()){
				uniteNodes(currNode, prevNode);

				// Update our iterating node
				prevNode = currNode;
			}

			// Zero prev rank
			this.subTreesArray.set(indexCnt, null);

			// Increment indexCnt
			indexCnt++;

			// Update pointers to keep iterating up the ranks of the heap
			if (isIndexLegal(indexCnt)) { // Making sure we have a tree of this rank
				currNode = this.subTreesArray.get(indexCnt);
			}
			
			else { // Else, we have reached the end of our heap
				currNode = null; 
			}
		} 

		// Insert the new tree into subTreesArray
		if (isIndexLegal(indexCnt)) { // Making sure we have a tree of this rank
			this.subTreesArray.set(indexCnt, prevNode); // If we have one update it
		}
		else {
			this.subTreesArray.add(indexCnt, prevNode); // Else add a new one
			this.last = prevNode;
		}
	}

	/**
	 * 
	 * Helper function to determine if the current index doens't excced the subTrees array size
	 * 
	 */
	public boolean isIndexLegal(int index) {

		// RUNTIME: O(1)

		return index < this.subTreesArray.size();
	}

	/**
	 * 
	 * Helper function which connects pointers between two nodes when melding heaps
	 * 
	 */
	public void uniteNodes(HeapNode minNode, HeapNode otherNode) {

		// Update the 3 pointers needed in order to set the new order then update rank

		// RUNTIME: O(1)

		// Update pointers of both nodes
		otherNode.setNext(minNode.getChild());
		otherNode.setParent(minNode);
		minNode.setChild(otherNode);

		// Update rank of the minNode
		minNode.setRank(minNode.getRank()+1);

	}

	/**
	 * 
	 * Return the number of elements in the heap
	 *   
	 */
	public int size()
	{
		return this.size;
	}

	/**
	 * 
	 * The method returns true if and only if the heap
	 * is empty.
	 *   
	 */
	public boolean empty()
	{
		return this.size() == 0;
	}

	/**
	 * 
	 * Return the number of trees in the heap.
	 * 
	 */
	public int numTrees() {

	// Iterate over subTrees array and check how many roots different than null exist

	// RUNTIME: O(log(n))

		int cnt = 0;
		for (int i=0; i<this.subTreesArray.size();i++) {
			if (this.subTreesArray.get(i) != null) {
				cnt++;
			}
		}
		return cnt; 
	}

	public HeapNode getLast() {
		return this.last;
	}

	/**
	 * Class implementing a node in a Binomial Heap.
	 *  
	 */
	public class HeapNode{
		public HeapItem item;
		public HeapNode child;
		public HeapNode next;
		public HeapNode parent;
		public int rank;

		// Constructor
		public HeapNode(HeapItem item){
			this.item = item;
			this.child = null;
			this.next = null;
			this.parent = null;
			this.rank = 0;
			this.item.setNode(this);
		}

		/* All getters and setters for HeapNode class */

		public int getKey() {
			return this.item.getItemKey();
		}

		public String getInfo() {
			return this.item.getItemInfo();
		}

		public HeapItem getItem() {
			return this.item;
		}

		public HeapNode getChild() {
			return this.child;
		}

		public HeapNode getNext() {
			return this.next;
		}

		public HeapNode getParent() {
			return this.parent;
		}

		public int getRank() {
			return this.rank;
		}

		public void setItem(HeapItem myItem) {
			this.item = myItem;
		}

		public void setChild(HeapNode myChild) {
			this.child = myChild;
		}

		public void setNext(HeapNode myNext) {
			this.next = myNext;
		}

		public void setParent(HeapNode myParent) {
			this.parent = myParent;
		}

		public void setRank(int myRank) {
			this.rank = myRank;
		}
	}

	/**
	 * Class implementing an item in a Binomial Heap.
	 *  
	 */
	public class HeapItem{
		public HeapNode node;
		public int key;
		public String info;

		// Constructor
		public HeapItem(int key,String info) {
			this.key = key;
			this.info = info;
		}

		// getter for key and info of item

		public int getItemKey() {
			return this.key;
		}

		public String getItemInfo() {
			return this.info;
		}

		// setter for a node pointer

		public void setNode(HeapNode myNode) {
			this.node = myNode;
		}
	}


	/*******************************************/
	/************* MAIN FUNCTION **************/
	/*****************************************/

public static void main(String[] args) {

	// Create Heap
	BinomialHeap myHeap = new BinomialHeap();
	BinomialHeap heap2 = new BinomialHeap();


	// Insert Nodes
	myHeap.insert(1,"1");
	myHeap.insert(2,"2");
	myHeap.insert(3,"3");
	myHeap.insert(1,"1.1");
	myHeap.insert(4,"4");
	myHeap.insert(5,"5");
	myHeap.insert(7,"7");
	myHeap.insert(0,"0");
	myHeap.insert(-1,"-1");
	myHeap.insert(20,"20");
	myHeap.insert(8,"8");
	myHeap.insert(-10,"-10");
	myHeap.insert(-9,"-9");
	myHeap.insert(33,"33");
	myHeap.insert(-45,"-45");

	heap2.insert(8,"8.1");
	heap2.insert(1,"1.2");
	heap2.insert(2,"2.1");
	heap2.insert(44,"44");
	heap2.insert(45,"45");
	heap2.insert(-33,"33");
	heap2.insert(0,"0.1");
	heap2.insert(0,"0.1");
	heap2.insert(22,"0.1");
	heap2.insert(11,"0.1");
	heap2.insert(33,"0.1");
	heap2.insert(44,"0.1");
	heap2.insert(55,"0.1");
	heap2.insert(-90,"0.1");

	// Delete min
	myHeap.deleteMin();


	HeapNode currRoot = null;
	
	// B0
	System.out.println("ROOT OF B0:");
	if (myHeap.subTreesArray.get(0) == null) {
		System.out.println("null");
	}
	else {
		currRoot = myHeap.subTreesArray.get(0);
		System.out.println(currRoot.getKey());
	}
	System.out.println("NODES OF B0:");
	while ((currRoot != null) && (currRoot.getChild() != null)) {
		System.out.println(currRoot.getChild().getKey());
		currRoot = currRoot.getChild();
	}
	System.out.println();

	// B1
	System.out.println("ROOT OF B1:");
	if (myHeap.subTreesArray.get(1) == null) {
		System.out.println("null");
	}
	else {
		currRoot = myHeap.subTreesArray.get(1);
		System.out.println(currRoot.getKey());
	}
	System.out.println("NODES OF B1:");
	while ((currRoot != null) && (currRoot.getChild() != null)) {
		System.out.println(currRoot.getChild().getKey());
		currRoot = currRoot.getChild();
	}
	System.out.println();

	// B2
	System.out.println("ROOT OF B2:");
	if (myHeap.subTreesArray.get(2) == null) {
		System.out.println("null");
	}
	else {
		currRoot = myHeap.subTreesArray.get(2);
		System.out.println(currRoot.getKey());
	}
	System.out.println("NODES OF B2:");
	while ((currRoot != null) && (currRoot.getChild() != null)) {
		System.out.println(currRoot.getChild().getKey());
		currRoot = currRoot.getChild();
	}
	System.out.println();

	// B3
	System.out.println("ROOT OF B3:");
	if (myHeap.subTreesArray.get(3) == null) {
		System.out.println("null");
	}
	else {
		currRoot = myHeap.subTreesArray.get(3);
		System.out.println(currRoot.getKey());
	}
	System.out.println("NODES OF B3:");
	while ((currRoot != null) && (currRoot.getChild() != null)) {
		System.out.println(currRoot.getChild().getKey());
		currRoot = currRoot.getChild();
	}
	System.out.println();

	// B4
	// System.out.println("ROOT OF B4:");
	// if (myHeap.subTreesArray.get(4) == null) {
	// 	System.out.println("null");
	// }
	// else {
	// 	currRoot = myHeap.subTreesArray.get(4);
	// 	System.out.println(currRoot.getKey());
	// }
	// System.out.println("NODES OF B4:");
	// while ((currRoot != null) && (currRoot.getChild() != null)) {
	// 	System.out.println(currRoot.getChild().getKey());
	// 	currRoot = currRoot.getChild();
	// }

	System.out.println();
	System.out.println("SPECIAL's:");
	System.out.println();
	System.out.println("MINIMUM:");
	System.out.println(myHeap.min.getKey());
	System.out.println("LAST ROOT:");
	System.out.println(myHeap.getLast().getKey());
	System.out.println("NUM OF TREES:");
	System.out.println(myHeap.subTreesArray.size());
	System.out.println("NUM OF FULL TREES:");
	System.out.println(myHeap.numTrees());
	System.out.println("NUM OF NODES:");
	System.out.println(myHeap.size());

	// // Pointer Check
	// System.out.println("POINTER CHECK");
	// System.out.println("I AM NODE: ");
	// System.out.println(myHeap.subTreesArray.get(1).getKey());
	// System.out.println("MY CHILD IS: ");
	// System.out.println(myHeap.subTreesArray.get(1).getChild().getKey());
	// System.out.println("I AM THE KID MY FATHER IS: ");
	// System.out.println(myHeap.subTreesArray.get(1).getChild().getParent().getKey());
	// System.out.println("I AM NODE: ");
	// System.out.println(myHeap.subTreesArray.get(3).getChild().getKey());
	// System.out.println("MY NEXT IS: ");
	// System.out.println(myHeap.subTreesArray.get(3).getChild().getNext().getKey());



	}

}
