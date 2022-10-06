public class SequentialPrefix implements PrefixInterface {

    public void run(String inputFileName, int bufferSize) {

        int[] myTreeValArray = new int[bufferSize];

        /**
         * Open a stream to read the "in.txt" and write to the "out.txt"
         */
        readInFile inReadInst = new readInFile(inputFileName, bufferSize);
        writeOutFile outWriteInst = new writeOutFile("out.txt", bufferSize);

        /* Try to open the "in.txt" and "out.txt" files. Print the exception if unable to. */
        try {
            inReadInst.OpenStream();
            outWriteInst.OpenStream();
        } catch (Exception ex) {
            System.out.println("Issue opening the files: " + ex.toString());
        }

        inReadInst.treeValArray = myTreeValArray;
        boolean NotAtEndOfFile = true;
        int myAddNum = 0;

        try {
            do {
                /**
                 * Read in the buffered data from the file "in.txt"
                 */
                NotAtEndOfFile = inReadInst.readIn();

                /**
                 * Compute the prefix sum sequentially
                 */
                for (int curIndex = 1; curIndex < inReadInst.lengthNeeded; curIndex++) {
                    inReadInst.treeValArray[curIndex] = inReadInst.treeValArray[curIndex] + inReadInst.treeValArray[curIndex - 1];
                }

                /**
                 * Write prefix sum to file "out.txt"
                 */
                myAddNum = outWriteInst.writeOut(myAddNum);

            } while (NotAtEndOfFile); // END do-while loop

        } catch (Exception ex) {
            System.out.println("There was an error when performing the PrefixSum: " + ex.toString());
        } finally {

            /*close the files that are open*/
            try {
                inReadInst.CloseStream();
                outWriteInst.CloseStream();
            } catch (Exception ex2) {
                System.out.println("There was an error closing the files: " + ex2.toString());
            }// end try-catch statement


        }//END finally
    } // END public void run() METHOD

    public static void main (String [] args) {
        new SequentialPrefix().run("in.txt", 1000);
    }

}
