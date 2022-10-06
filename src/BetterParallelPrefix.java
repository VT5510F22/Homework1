/**
 *  Improved version of ParallelPrefix
 *
 */
import java.util.concurrent.ForkJoinPool;

public class BetterParallelPrefix implements PrefixInterface {

    public  int ParLevel = 25;//Runtime.getRuntime().availableProcessors();

    public void run(String inputFileName, int bufferSize) {

        ForkJoinPool PoolInst = new ForkJoinPool();
        int[] myTreeValArray = new int[bufferSize];


        /**
         * Open a stream to read the "in.txt" and write to the "out.txt"
         */
        readInFileBetter inReadInst = new readInFileBetter(inputFileName,bufferSize);
        writeOutFileBetter outWriteInst = new writeOutFileBetter("out.txt",bufferSize);

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
                UpPassBetter myUpPassBetterInst = new UpPassBetter(0, (inReadInst.lengthNeeded - 1));
                PoolInst.invoke(myUpPassBetterInst);

                /**
                 * DownPass and calculate the prefix sum in a recursive and multithreaded manner.
                 */
                curLoopHighVal = inReadInst.treeValArray[inReadInst.lengthNeeded - 1];
                DownPassBetter myDownPassBetterInst = new DownPassBetter(0, (inReadInst.lengthNeeded - 1),curLoopHighVal);
                PoolInst.invoke(myDownPassBetterInst);


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

    } //END void run()



    public static void main (String [] args) {
        new BetterParallelPrefix().run("in.txt", 1000);
    }

}
