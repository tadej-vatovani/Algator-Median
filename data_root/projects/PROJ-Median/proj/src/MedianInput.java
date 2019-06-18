import si.fri.algotest.execute.AbstractInput;

/**
 * @author tadej
 */
public class MedianInput extends AbstractInput {

  /**
   * An array of data in which we find the median
   */
  public int[] arrayToSelectFrom;

  public MedianInput(int[] data) {
    arrayToSelectFrom = data;
  }

  /**
   * 
   * A method to return the string represantation of the first 9 and last
   * element of the array
   */
  String ArrayToString(int[] array) {
    String s = "[ ";
    for (int i = 0; i < 10; i++) {
      if (i == 9)
        s += "... " + array[array.length - 1] + "]";
      else
        s += array[i] + ", ";
    }
    return s;
  }

  @Override
  public String toString() {
    return super.toString() + ", Data: " + ArrayToString(arrayToSelectFrom);
  }
}