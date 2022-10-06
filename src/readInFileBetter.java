import java.io.*;

public class readInFileBetter extends RecurrBetter {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private String inFileName;
    private int fileBuffSizeInt;
    private FileReader FileInStream;
    private BufferedReader FileInBuff;


    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public readInFileBetter () {
        inFileName = "in.txt";
        fileBuffSizeInt = 1000;
    }

    public readInFileBetter (int fileBuffer) {
        this.fileBuffSizeInt = fileBuffer;
        inFileName = "in.txt";
    }

    public readInFileBetter (String inFileName) {
        this.inFileName = inFileName;
        fileBuffSizeInt = 1000;
    }

    public readInFileBetter (String inFileName,int fileBuffer) {
        this.fileBuffSizeInt = fileBuffer;
        this.inFileName = inFileName;
    }

    /**
     *  Define the private members of this class
     **/

    /* void OpenStream () DESCRIPTION :
     *  Open a stream to the file that is being read
     */
    protected void OpenStream () throws IOException {

        FileInStream = new FileReader(inFileName);
        FileInBuff = new BufferedReader(FileInStream, fileBuffSizeInt);
    }

    /* void CloseStream () DESCRIPTION :
     * Close the stream to the file that is being read
     */
    protected void CloseStream () throws IOException {
        FileInBuff.close();
    }

    /* List<Integer> readIn () DESCRIPTION :
     * Get the input values from the value and put them in an ArrayList
     */
    public boolean readIn() {

        String numStr = null;

        try {
            /*Open stream to input file.*/
            // REMOVE !!!!!!!!!!!!!!!!!!! // this.OpenStream();

            /*Line-by-line, get and convert the input file.*/
            this.lengthNeeded = 0;
            while((lengthNeeded < fileBuffSizeInt) && ((numStr = FileInBuff.readLine()) != null)){

                try {
                    /*Convert the string into an integer*/
                    treeValArray[this.lengthNeeded] = Integer.parseInt(numStr); //This list is synchronized
                }catch(Exception a){
                    System.out.println("This is treeVal write: "+ a.toString());
                }//END try-catch

                this.lengthNeeded++;
            }//END while loop

            /* Catch all exceptions if they occur and print to the screen */
        } catch (Exception e) {
            System.out.println("readInFileThread run error: " + e.toString());
        } // END catch statement

        /*If we are at the end of the file, return false, else return true*/
        if (numStr == null){
            return false;
        }else{
            return true;
        }

    }// end public boolean readIn()

}
