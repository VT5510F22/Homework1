public class DownPass extends Recurr {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private int baseIndex;
    private int highIndex;
    private int localHighValList;
    private int minimumSplit;

    /** Public Instance Variables :
     * These are the "return" or "output" instant variables.
     */
    // NONE

    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public DownPass(int baseIndex, int highIndex, int localHighValList, int minimumSplit) {
        this.baseIndex = baseIndex;
        this.highIndex = highIndex;
        this.localHighValList = localHighValList;
        this.minimumSplit = minimumSplit;
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
        if ((highIndex - baseIndex) <= minimumSplit) {

            int treeValTemp = treeValArray[baseIndex]; //treeValList.get(baseIndex);
            if (baseIndex != 0) {
                // REMOVE LATER !!!!!!!!!!! // treeValTemp = treeValTemp + treeValList.get(baseIndex - 1);
                // REMOVE LATER !!!!!!!!!!! //treeValList.set(baseIndex, treeValTemp);
                treeValArray[baseIndex] = treeValArray[baseIndex] + treeValArray[baseIndex - 1];
            }

            for (int curIndex = (baseIndex + 1); curIndex < highIndex; curIndex++) {
                // REMOVE LATER !!!!!!!!!! // treeValTemp = treeValList.get(curIndex) + treeValList.get(curIndex - 1);
                // REMOVE LATER !!!!!!!!!! // treeValList.set(curIndex, treeValTemp);
                treeValArray[curIndex] = treeValArray[curIndex] + treeValArray[curIndex - 1];
            }
        }else {
                /* Average the two indexes */
                int AvgIndexInt = (highIndex + baseIndex)/2;
                /* Get value from List at the average index*/
                // REMOVE LATER !!!!!!!!!!!!! // int AvgIndexValInt = treeValList.get(AvgIndexInt);
                int AvgIndexValInt = treeValArray[AvgIndexInt];
                /* Get the difference of the highest value (locally) from the value at the average index*/
                int valDiffLocalHighInt = localHighValList - AvgIndexValInt;
                /* Assign the value of the List at the average index to the difference between..
                 * ..the value of the List at the highIndex and value from the List at the average index*/
                // REMOVE LATER !!!!!!!!!!!!!! // int valDiffAbsoluteHighInt = treeValList.get(highIndex) - valDiffLocalHighInt;
                int valDiffAbsoluteHighInt = treeValArray[highIndex] - valDiffLocalHighInt;
                // REMOVE LATER !!!!!!!!!!!!!! // treeValList.set(AvgIndexInt,valDiffAbsoluteHighInt);
                treeValArray[AvgIndexInt] = valDiffAbsoluteHighInt;

                try {
                    /*Calculate on left branch of the tree*/
                    DownPass DownPassLeftVar = new DownPass(baseIndex,AvgIndexInt,AvgIndexValInt, minimumSplit);
                    /*Calculate on the right branch of the tree*/
                    DownPass DownPassRightVar = new DownPass((AvgIndexInt+1),highIndex,valDiffLocalHighInt, minimumSplit);

                    /*Start and join the treads*/
                    DownPassLeftVar.start();
                    DownPassRightVar.start();
                    DownPassLeftVar.join();
                    DownPassRightVar.join();


                }catch (Exception ex){
                    System.out.println("Error in the multithreaded recursion in UpPassCL run(): " + ex.toString());
                }//END of try-catch



            }//END of if statement

    }//END void run()

}// END public DownPassCl class
