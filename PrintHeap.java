

public class PrintHeap {
	static String arrowUp;
	static String arrowEndOfLayer;
	static String arrowStartOfLayer;

	public static void printHeap(BinomialHeap heap, boolean unicode) {
		if ( unicode ) {
			arrowUp = "\u2191\n";
			arrowEndOfLayer = "\u21b6\n";
			arrowStartOfLayer = "\u21ba\n";
		} else {
			arrowUp = "^\n";
			arrowEndOfLayer = "~\n";
			arrowStartOfLayer = "*\n";
		}
		if (heap.empty()) {
			System.out.println("Heap is empty");
			return;
		}
		BinomialHeap.HeapNode first = heap.last.next;

		System.out.println("Last: " + heap.last.item.key);
		printTree(first, 0, first); // Print the tree rooted at current root
	}

	private static void printTree(BinomialHeap.HeapNode node, int depth, BinomialHeap.HeapNode initialRoot) {
		if (node.next != initialRoot) {
			printTree(node.next, depth, initialRoot); // Print sibling recursively until we reach the initial root
			printAtDepth(depth, arrowUp); // arrow up pointing to next, if next is not circular
		} else if ( node.next == initialRoot ) {
		   printAtDepth(depth, arrowEndOfLayer);	// arrow looping back
												  	// indicating we've reached end
												  	// of next nodes in layer
		}

		printNode(node, depth);
		
		if ( node.next == initialRoot.next ) {
			printAtDepth(depth, arrowStartOfLayer);
		}

		if (node.child != null) {
			printTree(node.child, depth + 1, node.child); // Print child recursively
		}

	}
	private static void printNode(BinomialHeap.HeapNode node, int depth) {
		String keyString = String.valueOf(node.item.key);
		String rankString = String.valueOf(node.rank);
		printAtDepth(depth, keyString + " [" + rankString + "]\n");
	}
	
	private static void printAtDepth(int depth, String format) {
		for (int i = 0; i < depth; i++) {
			System.out.printf("  ");
		}
		System.out.printf(format);
	}
}