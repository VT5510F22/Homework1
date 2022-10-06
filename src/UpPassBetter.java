/** Import :
 * Import necessary libraries
 */

/** Define Class :
 *
 */
public class UpPassBetter extends RecurrBetter {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private int baseIndex;
    private int highIndex;

    /** Public Instance Variables :
     * These are the "return" or "output" instant variables.
     */
    //NONE

    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public UpPassBetter(int baseIndex, int highIndex) {
        this.baseIndex = baseIndex;
        this.highIndex = highIndex;
    }//END UpPassCL(..) Constructor



    /** Public Instance Methods :
     *
     */

    /* void run() DESCRIPTION :
     *  Override Thread class's void run() instance method. This is where the..
     *  ..thread functionality is executed.
     */
    public Integer compute() {
        /*Determine if base condition is met*/
        if ((highIndex - baseIndex) == 0) {
            // TEST CODE ******** // System.out.println("THIS IS WORKING!!!!!!!!!!!!!!!!");
        }
        /*Else, in a recursive manner, split the problem into new threads*/
        else {
            try {
                /* Average the two indexes*/
                int BaseHighAvgInt = (highIndex + baseIndex)/2;
                int IndexDifference = highIndex - baseIndex;

                /*Create a new left branch of the tree*/
                UpPassBetter UpPassLeftVar = new UpPassBetter(baseIndex,BaseHighAvgInt);

                /*Create a new right branch of the tree*/
                UpPassBetter UpPassRightVar = new UpPassBetter((BaseHighAvgInt+1), highIndex);

                /*Start and join the treads*/
                UpPassLeftVar.fork();
                int UpPassRightResult = UpPassRightVar.compute();
                UpPassLeftVar.join();
                //UpPassRightVar.join();

                /*Set the treeValArray at highIndex to the sum of the average index and the right branch result*/
                int treeResultInt = treeValArray[BaseHighAvgInt] + UpPassRightResult;
                treeValArray[highIndex] = treeResultInt;
                /*Set this value as the result of this thread*/
                return treeValArray[highIndex];

            }catch (Exception ex){
                System.out.println("Error in the multithreaded recursion in UpPassCL run(): " + ex.toString());
                return 0;
            }//END of try-catch
        }//END of if-else
        return treeValArray[highIndex];
    }//END Integer compute()

}//END UpPassCL Class

