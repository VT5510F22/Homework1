public class UpPass extends Recurr {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private int baseIndex;
    private int highIndex;
    private int minSplit;

    /** Public Instance Variables :
     * These are the "return" or "output" instant variables.
     */
    public int result;

    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public UpPass(int baseIndex, int highIndex, int minSplit) {
        //this.treeValList = treeValList;
        this.baseIndex = baseIndex;
        this.highIndex = highIndex;
        this.minSplit = minSplit;
    }//END UpPassCL(..) Constructor



    /** Public Instance Methods :
     *
     */

    /* void run() DESCRIPTION :
     *  Override Thread class's void run() instance method. This is where the..
     *  ..thread functionality is executed.
     */
    public void run() {
        /*Determine if base condition is met. Part of this code will be done sequentially as to not spin up so many ..
        *.. threads that the program crashes and does not work.*/
        if ((highIndex - baseIndex) <= minSplit) {
            int seqSum = 0;
            /*Sequentially perform the prefix sum calculation.*/
            for (int curIndex = baseIndex; curIndex <= highIndex; curIndex++){
                seqSum += treeValArray[curIndex];
            }
            treeValArray[highIndex] = seqSum;
            result = treeValArray[highIndex];
            // TEST CODE ******** // System.out.println("THIS IS WORKING!!!!!!!!!!!!!!!!");
        }
        /*Else, in a recursive manner, split the problem into new threads*/
        else {
            try {
                /* Average the two indexes*/
                int BaseHighAvgInt = (highIndex + baseIndex)/2;
                int IndexDifference = highIndex - baseIndex;
                // TEST CODE ******** // System.out.println("\nThis is the IndexDifference in UpPassCL " + Integer.toString(IndexDifference));
                /*Create a new left branch of the tree*/
                UpPass UpPassLeftVar = new UpPass(baseIndex,BaseHighAvgInt, minSplit);
                // TEST ******** // System.out.println("This is the UpPassLeftVar baseIndex: " + Integer.toString(baseIndex) + " and " + Integer.toString(BaseHighAvgInt));
                /*Create a new right branch of the tree*/
                UpPass UpPassRightVar = new UpPass((BaseHighAvgInt+1), highIndex, minSplit);
                // TEST ******** // System.out.println("This is the Next BaseHighAvgInt: " + Integer.toString((BaseHighAvgInt+1-baseIndex)/2));
                // TEST ******** // System.out.println("This is the UpPassLeftVar highIndex: " + Integer.toString(highIndex) + " and " + Integer.toString(BaseHighAvgInt+1));

                /*Start and join the treads*/
                UpPassLeftVar.start();
                UpPassRightVar.start();
                UpPassLeftVar.join();
                UpPassRightVar.join();

                /*Set the treeValList at highIndex to the sum of the average index and the right branch result*/
                int treeResultInt = treeValArray[BaseHighAvgInt] + UpPassRightVar.result;
                //treeValList.set(highIndex,treeResultInt);
                treeValArray[highIndex] = treeResultInt;
                /*Set this value as the result of this thread*/
                result = treeResultInt;

            }catch (Exception ex){
                System.out.println("Error in the multithreaded recursion in UpPassCL run(): " + ex.toString());
            }//END of try-catch
        }//END of if-else

    }//END void run()

}//END UpPassCL Class
