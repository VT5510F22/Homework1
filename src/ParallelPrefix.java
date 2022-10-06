/**
 *  Finds the prefix sum using a parallel algorithm
 *
 */

public class ParallelPrefix implements IPrefix {

    public int SeqSize = 250;

    public void run(String inputFileName, int bufferSize) {

        int[] myTreeValArray = new int[bufferSize];
        int myMinSplit = bufferSize/4;//this.SeqSize;

        /**
         * Open a stream to read the "in.txt" and write to the "out.txt"
         */
        readInFile inReadInst = new readInFile(inputFileName,bufferSize);
        writeOutFile outWriteInst = new writeOutFile("out.txt",bufferSize);

        /* Try to open the "in.txt" and "out.txt" files. Print the exception if unable to. */
        try {
            inReadInst.OpenStream();
            outWriteInst.OpenStream();
        }catch (Exception ex){
            System.out.println("Issue opening the files: " + ex.toString());
        }

        inReadInst.treeValArray = myTreeValArray;


        /**
         * loop until the end of the file buffering the data. The data will be buffered in, the UpPass will be
         * performed on it, then the DownPass will be performed, and then the data will be written to the file.
         */
        int jj = 0;
        int myAddNum = 0;
        int curLoopHighVal;
        boolean NotAtEndOfFile = true;

        //TEST !!!!!!!!! //
        int numbersCal = 0;

        try{
            do  {
                /**
                 * Get the integer List from file "in.txt"
                 */
                NotAtEndOfFile = inReadInst.readIn();

                /*TEST PRINT STATEMENT !!!!!!!!!!!!!!!!!!!!!!!
                numbersCal += bufferSize;
                System.out.println("Read in buffer. numbersCal: " + Integer.toString(numbersCal));
                System.out.println("Read in buffer. NotAtEndOfFile " + Boolean.toString(NotAtEndOfFile) + "\n");
                */

                /**
                 * UpPass and create the tree in a recursive and multithreaded manner.
                 */
                UpPass myUpPassInst = new UpPass(0, (inReadInst.lengthNeeded - 1), myMinSplit);
                try {
                    myUpPassInst.start();
                    myUpPassInst.join();
                } catch (Exception ex) {
                    System.out.println("Error in the up-pass: " + ex.toString());
                }

                /**
                 * DownPass and calculate the prefix sum in a recursive and multithreaded manner.
                 */
                curLoopHighVal = inReadInst.treeValArray[inReadInst.lengthNeeded - 1];
                DownPass myDownPassInst = new DownPass(0, (inReadInst.lengthNeeded - 1),curLoopHighVal,myMinSplit);
                try {
                    myDownPassInst.start();
                    myDownPassInst.join();
                } catch (Exception ex) {
                    System.out.println("Error in the down-pass: " + ex.toString());
                }


                /**
                 * Write prefix sum to file "out.txt"
                 */
                myAddNum = outWriteInst.writeOut(myAddNum);
            }while(NotAtEndOfFile);
        }catch (Exception ex){
            System.out.println("There was an error when performing the PrefixSum: " + ex.toString());
        }finally{

            /*close the files that are open*/
            try{
                inReadInst.CloseStream();
                outWriteInst.CloseStream();
            }catch (Exception ex2){
                System.out.println("There was an error closing the files: " + ex2.toString());
            }

        }

    } //END public void run(..)

    public static void main(String[] args) {
        new ParallelPrefix().run("in.txt", 1000);
    }//END public static void main(..)
}//END public class ParallelPrefix ..

