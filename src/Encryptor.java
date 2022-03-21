import java.util.ArrayList;
public class Encryptor
{
  /** A two-dimensional array of single-character strings, instantiated in the constructor */
  private String[][] letterBlock;

  /** The number of rows of letterBlock, set by the constructor */
  private int numRows;

  /** The number of columns of letterBlock, set by the constructor */
  private int numCols;

  /** Constructor*/
  public Encryptor(int r, int c)
  {
    letterBlock = new String[r][c];
    numRows = r;
    numCols = c;
  }
  
  public String[][] getLetterBlock()
  {
    return letterBlock;
  }



  /** Places a string into letterBlock in row-major order.
   *
   *   @param str  the string to be processed
   *
   *   Postcondition:
   *     if str.length() < numRows * numCols, "A" in each unfilled cell
   *     if str.length() > numRows * numCols, trailing characters are ignored
   */
  public void fillBlock(String str)
  {
    int tally = 0;
    for (int i = 0; i < letterBlock.length; i++) {
      for (int n = 0; n < letterBlock[i].length; n++) {
        if (tally > str.length()-1) {
          letterBlock[i][n] = "A";
        } else {
          letterBlock[i][n] = str.substring(tally, tally+1);
        }
        tally++;
      }
    }
  }

  /** Extracts encrypted string from letterBlock in column-major order.
   *
   *   Precondition: letterBlock has been filled
   *
   *   @return the encrypted string from letterBlock
   */
  public String encryptBlock()
  {
    String output = "";
    for (int n = 0; n < letterBlock[0].length; n++) {
      for (int i = 0; i < letterBlock.length; i++) {
        output += letterBlock[i][n];
      }
    }
    return output;
  }

  //spirals clockwise around the 2d array like a cinnamon bun, until it has collected every element
  public String myEncryptBlock() {
    String output = "";
    ArrayList<Integer> fullRows = new ArrayList<Integer>();
    ArrayList<Integer> fullCols = new ArrayList<Integer>();
    int direction = 0;
    int x = 0;
    int y = 0;


    while (output.length() < (numRows * numCols)) {

        if (direction == 0) { // if set to move right (positively traversing columns within a row)
          if (x+1 < numRows && !fullCols.contains(x+1)) { // if column ahead is in bounds and unfilled
            x+=1; //move one to the right
          } else {
            direction +=1; //turn 90 degrees right
            fullRows.add(y);
          }
        }


      else if (direction == 1) { // if set to move down (positively traversing rows within a column)
        if (y+1 < numCols && !fullRows.contains(y+1)) { // if row ahead is in bounds and unfilled
          y+=1; //move one down
        } else {
          direction +=1; //turn 90 degrees right0
          fullCols.add(x);
        }
      }

        else if (direction == 2) { // if set to move left (negatively traversing columns within a row)
          if (x-1 > -1 && !fullCols.contains(x-1)) { // if row ahead is in bounds and unfilled
            x-=1; //move one down
          } else {
            direction +=1; //turn 90 degrees right
            fullRows.add(y);
          }
        }

        else if (direction == 3) { // if set to move down (negatively traversing rows within a column)
          if (y-1 < numCols && !fullRows.contains(y-1)) { // if row ahead is in bounds and unfilled
            y-=1; //move one down
          } else {
            direction = 0; //turn 90 degrees right
            fullCols.add(x);
          }
        }

      output += letterBlock[x][y];
    }


    //stars in upper left (0,0)
    //moves right if possible
    return output;
  }

  /** Encrypts a message.
   *
   *  @param message the string to be encrypted
   *
   *  @return the encrypted message; if message is the empty string, returns the empty string
   */
  public String encryptMessage(String message)
  {
    fillBlock(message);
    return encryptBlock();
  }
  
  /**  Decrypts an encrypted message. All filler 'A's that may have been
   *   added during encryption will be removed, so this assumes that the
   *   original message (BEFORE it was encrypted) did NOT end in a capital A!
   *
   *   NOTE! When you are decrypting an encrypted message,
   *         be sure that you have initialized your Encryptor object
   *         with the same row/column used to encrypted the message! (i.e. 
   *         the “encryption key” that is necessary for successful decryption)
   *         This is outlined in the precondition below.
   *
   *   Precondition: the Encryptor object being used for decryption has been
   *                 initialized with the same number of rows and columns
   *                 as was used for the Encryptor object used for encryption. 
   *  
   *   @param encryptedMessage  the encrypted message to decrypt
   *
   *   @return  the decrypted, original message (which had been encrypted)
   *
   *   TIP: You are encouraged to create other helper methods as you see fit
   *        (e.g. a method to decrypt each section of the decrypted message,
   *         similar to how encryptBlock was used)
   */
  public String decryptMessage(String encryptedMessage)
  {
    fillBlock(encryptedMessage);
    String output = "";
    for (int n = 0; n < letterBlock[0].length; n++) {
      for (int i = 0; i < letterBlock.length; i++) {
        output+=letterBlock[i][n];
      }
    }
    for (int i = output.length()-1; i > 0; i--) {
      if (!output.substring(i, i+1).equals("A")) {
        return output;
      } else {
        output = output.substring(0, i);
      }
    }
    return "";
  }
}