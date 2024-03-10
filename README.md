# To run
To execute from terminal, copy BinomialHeap.java to a folder named BinomialHeap located in the root of the project, and in git bash (Or just terminal for macOS/linux)
```bash
javac Utils/Tests.java Utils/PrintHeap.java BinomialHeap/BinomialHeap.java && java Utils.Tests
```

Alternatively copy all `.java` files to the same folder and after removing the `package` and `import` statements from them, run
```bash
javac Tests.java PrintHeap.java BinomialHeap.java && java Tests
```

## PrintHeap
Prints the heap.

Inside your java program:
```java
PrintHeap.printHeap(heap, true)
```

Where `heap` is the reference to an initialized BinomialHeap and true/false for whether to use pretty unicode arrows

# To contribute

- Fork the repository (fork button should be somewhere around top row)

- This creates a copy of this repository owned by you

- Clone the copy of the repository locally (the fork)

- Make changes locally

- Push the changes to your fork

- Once you're done with all changes go to your fork in github and press "Compare & Pull request". This makes a request to add your changes to the original repository.
