Binomial Heap implementation in java using three classes. Detailed documentation provided as pdf.

HeapNode:

  Fields:

    1. Item
    2. Child
    3. Next
    4. Parent
    5. Rank
    
  Methods (all at O(1)):
  
    1. getters: key, info, item, child, next, parent and rank
    2. setters: key, info, item, child, next, parent and rank

HeapItem:

  Fields:

    1. Key
    2. Info
    3. Node

  Methods (all at O(1)):
  
    1. getters: ItemKey, ItemInfo, and node
    2. setters: node
  

BinomialHeap:

  Fields:

    1. subTreeArray
    2. Min key
    3. Last
    4. Size
    5. Treescount

  Methods:
  
    1.  public void decreaseKey(HeapItem item, int diff)             | O(logn)
    2.  public HeapItem insert(int key,String info)                  | O(logn)
    3.  public void deleteMin()                                      | O(logn)
    4.  public void updateLast()                                     | O(1)
    5.  public void updateMin()                                      | O(logn)
    6.  public HeapItem findMin()                                    | O(1)
    7.  public void delete(HeapItem item)                            | O(logn)
    8.  public void meld(BinomialHeap heap2)                         | O(logn)
    9.  public void singleMeld(HeapNode node, int indexStart)        | O(logn)
    10. public Boolean isIndexLegal(int index)                       | O(1)
    11. public void uniteNodes(HeapNode minNode, HeapNode otherNode) | O(1)
    12. public int size()                                            | O(1)
    13. public Boolean empty()                                       | O(1)
    14. public int numTrees()                                        | O(1)
