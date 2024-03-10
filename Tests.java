

public class Tests extends TestHelper {
    public static void main(String[] args) {
        TestInsert();
    }

    /**
     * @pre existence of an empty constructor in BinomialHeap
     */
    public static void TestInsert() {
        BinomialHeap heap = new BinomialHeap();
        insertKeyArray(heap, new int[]{15, 35, 20, 31, 40, 43, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90});
        // BinomialHeap.HeapNode root = heap.last;
        // BinomialHeap.HeapNode child = assertPointers(heap, root, 40, 20, 35);
        // child = assertPointers(heap, child, 45, 43);

        //PrintHeap.printHeap(heap, true);
        // test for
        // - size
        // - rank
    }
}

abstract class TestHelper {
    static void insertKeyArray(BinomialHeap heap, int[] keys) {
        for (int key : keys) {
            heap.insert(key, String.valueOf(key));
        }
    }
}
