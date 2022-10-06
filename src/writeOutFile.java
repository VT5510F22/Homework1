/*JAVA FILE********************************************************************
import java.io.*;


/** Define Class :
 *
 */
public class writeOutFile extends Recurr {

    /** Private Instance Variables :
     * These are the "parameter or "input" instance variables.
     */
    private String outFileName;
    private int fileBuffSizeInt;
    private FileWriter FileOutStream;
    private BufferedWriter FileOutBuff;


    /** Constructors :
     * Defines the initial state of the class when created.
     */
    public writeOutFile () {
        outFileName = "out.txt";
        fileBuffSizeInt = 1000;
    }

    public writeOutFile (int fileBuffer, int addNum) {
        this.fileBuffSizeInt = fileBuffer;
        outFileName = "out.txt";
    }

    public writeOutFile (String outFileName, int addNum) {
        this.outFileName = outFileName;
        fileBuffSizeInt = 1000;
    }

    public writeOutFile (String outFileName,int fileBuffer, int addNum) {
        this.fileBuffSizeInt = fileBuffer;
        this.outFileName = outFileName;
    }

    /**
     *  Define the private members of this class
     **/

    /* void OpenStream () DESCRIPTION :
     *  Open a stream to the file that is being written
     */
    protected void OpenStream () throws IOException {

        FileOutStream = new FileWriter(outFileName);
        FileOutBuff = new BufferedWriter(FileOutStream, fileBuffSizeInt);
    }

    /* void CloseStream () DESCRIPTION :
     * Close the stream to the file that is being written
     */
    protected void CloseStream () throws IOException {
        FileOutBuff.close();
    }

    /* List<Integer> readIn () DESCRIPTION :
     * Get the input values from the value and put them in an ArrayList
     */
    public int writeOut(int addNum) {

        String numStr;

        try {
            /*Open stream to input file.*/
            // REMOVE THIS !!!!!!!!!!!!!!!!!!!!! // this.OpenStream();

            /*Line-by-line, get and convert to output to the out file.*/
            for (int ii = 0; ii < lengthNeeded; ii++) {
                FileOutBuff.write(Integer.toString((treeValArray[ii] + addNum)) + "\n");
            }//END For

            /* Catch all exceptions if they occur and print to the screen */
        } catch (Exception e) {
            System.out.println("readInFileThread run error: " + e.toString());
        } //END catch statement

        return treeValArray[lengthNeeded - 1] + addNum;
    }// END void writeOut()

}// END public writeOutFile class

