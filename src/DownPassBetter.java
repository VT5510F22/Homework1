public class DownPassBetter extends RecurrBetter {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private int baseIndex;
    private int highIndex;
    private int localHighValList;
    private int minSplit;

    /** Public Instance Variables :
     * These are the "return" or "output" instant variables.
     */
    // NONE

    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public DownPassBetter(int baseIndex, int highIndex, int localHighValList) {
        this.baseIndex = baseIndex;
        this.highIndex = highIndex;
        this.localHighValList = localHighValList;
    }//END DownPassBetterCL(..) Constructor

    /** Public Instance Methods :
     *
     */

    /* void run() DESCRIPTION :
     *  Override Thread class's void run() instance method. This is where the..
     *  ..thread functionality is executed.
     */
    public Integer compute() {

        /**
         * Determine if base condition is met. Part of this code will be done sequentially as to not spin up so many ..
         *.. threads that the program crashes and does not work.
         */
        if(highIndex - baseIndex != 0){
            /* Average the two indexes */
            int AvgIndexInt = (highIndex + baseIndex)/2;
            /* Get value from List at the average index*/
            // REMOVE !!!!!!!!!!!!!!! // int AvgIndexValInt = treeValList.get(AvgIndexInt);
            int AvgIndexValInt = treeValArray[AvgIndexInt];
            /* Get the difference of the highest value (locally) from the value at the average index*/
            int valDiffLocalHighInt = localHighValList - AvgIndexValInt;
            /* Assign the value of the List at the average index to the difference between..
             * ..the value of the List at the highIndex and value from the List at the average index*/
            // REMOVE !!!!!!!!!!!!!!! // int valDiffAbsoluteHighInt = treeValList.get(highIndex) - valDiffLocalHighInt;
            int valDiffAbsoluteHighInt = treeValArray[highIndex] - valDiffLocalHighInt;

            // REMOVE !!!!!!!!!!!!!!!!! // treeValList.set(AvgIndexInt,valDiffAbsoluteHighInt);
            treeValArray[AvgIndexInt] = valDiffAbsoluteHighInt;

            try {
                /*Calculate on left branch of the tree*/
                DownPassBetter DownPassBetterLeftVar = new DownPassBetter(baseIndex,AvgIndexInt,AvgIndexValInt);
                /*Calculate on the right branch of the tree*/
                DownPassBetter DownPassBetterRightVar = new DownPassBetter((AvgIndexInt+1),highIndex,valDiffLocalHighInt);

                /*Start and join the treads*/
                DownPassBetterLeftVar.fork();
                DownPassBetterRightVar.fork();
                DownPassBetterLeftVar.join();
                DownPassBetterRightVar.join();


            }catch (Exception ex){
                System.out.println("Error in the multithreaded recursion in UpPassCL run(): " + ex.toString());
            }//END of try-catch



        }//END of if statement
        return null;
    }//END void run()

}// END public DownPassCl class
