import java.util.Arrays;

/**
 *  Uses the parallelPrefix function provided by Java to find the prefix sum
 *
 */
public class ParallelPrefixInternal implements PrefixInterface {

    public void run(String inputFileName, int bufferSize) {

        int[] myTreeValArray = new int[bufferSize];
        //int myMinSplit = bufferSize/4;

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

                Arrays.parallelPrefix(inReadInst.treeValArray, Integer::sum);

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

    }

    public static void main (String [] args) {
        new ParallelPrefixInternal().run("in.txt", 1000);
    }

}
