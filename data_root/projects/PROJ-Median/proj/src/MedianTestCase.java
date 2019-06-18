
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Random;
import si.fri.algotest.entities.Variables;
import si.fri.algotest.execute.AbstractTestCase;

/**
 *
 * @author tadej
 */
public class MedianTestCase extends AbstractTestCase {

  @Override
  public MedianInput getInput() {
    return (MedianInput) super.getInput();
  }

  @Override
  public MedianOutput getExpectedOutput() {
    return (MedianOutput) super.getExpectedOutput();
  }

  @Override
  public MedianTestCase getTestCase(String testCaseDescriptionLine, String path) {
    // create a set of variables ...
    Variables inputParameters = new Variables();
    inputParameters.setVariable("Path", path);

    // ... using the testCaseDescriptionLine determine testcase
    // parameters and add them to the inputParameters set ...
    String[] fields = testCaseDescriptionLine.split(":");
    if (fields.length < 3) {
      return null;
    }

    // testName
    inputParameters.setVariable("Test", fields[0]);

    // probSize
    int probSize;
    try {
      probSize = Integer.parseInt(fields[1]);
    } catch (Exception e) {
      return null;
    }
    inputParameters.setVariable("N", probSize);

    // group
    String group = fields[2];
    inputParameters.setVariable("Group", group);

    // the maximum size of the numbers in the array
    if (group.equals("RND") && fields.length == 4) {
      inputParameters.setVariable("RndSize", fields[3]);
    }

    // inlined numbers
    if (group.equals("INLINE") && fields.length == 4) {
      inputParameters.setVariable("Inline", fields[3]);
    }

    // numbers from file
    if (group.equals("FILE") && fields.length == 5) {
      inputParameters.setVariable("Filename", fields[3]);
      inputParameters.setVariable("Offset", fields[4]);
    }

    // ... and finally, create a test case determined by these parameters
    return generateTestCase(inputParameters);
  }

  @Override
  public MedianTestCase generateTestCase(Variables inputParameters) {
    String path = inputParameters.getVariable("Path", "").getStringValue();

    int n = inputParameters.getVariable("N", 0).getIntValue();
    String group = inputParameters.getVariable("Group", "RND").getStringValue();

    // prepare an array of integers of a given size
    int[] array = new int[n];

    switch (group) {
    case "INLINE":
      String inline = inputParameters.getVariable("Inline", "").getStringValue();
      if (inline.isEmpty()) {
        return null;
      }
      String data[] = inline.split(" ");
      if (data.length != n) {
        return null;
      }
      try {
        for (int i = 0; i < n; i++) {
          array[i] = Integer.parseInt(data[i]);
        }
      } catch (Exception e) {
        return null;
      }
      break;
    case "RND":
      int rndSize = inputParameters.getVariable("RndSize", 0).getIntValue();
      if (rndSize <= 0) {
        rndSize = Integer.MAX_VALUE;
      }

      Random rnd = new Random(System.currentTimeMillis());

      for (int i = 0; i < n; i++) {
        array[i] = Math.abs(rnd.nextInt(rndSize));
      }
      break;
    case "SORTED":
      for (int i = 0; i < n; i++) {
        array[i] = i;
      }
      break;
    case "INVERSED":
      for (int i = 0; i < n; i++) {
        array[i] = n - i;
      }
      break;
    case "FILE": // first parameter is filemane, second the offset (from where numbers are read)
      try {
        String filename = inputParameters.getVariable("Filename", "").getStringValue();
        int offset = inputParameters.getVariable("Offset", 0).getIntValue();

        String testFile = path + File.separator + filename;

        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(testFile))));

        // skip the first "offset" numbers
        dis.skipBytes(offset * 4);

        int i = 0;
        while (i < n && dis.available() > 0) {
          array[i++] = dis.readInt();
        }
        if (i < n) {
          return null;
        }
        dis.close();
      } catch (Exception e) {
        return null;
      }
    }

    // create a test case and set ...
    MedianTestCase medianTestCase = new MedianTestCase();

    // ... the input
    medianTestCase.setInput(new MedianInput(array));

    // ... the input parameters
    medianTestCase.getInput().setParameters(inputParameters);

    // ... and the expected output
    int[] expectedResultArray = array.clone();
    Arrays.sort(expectedResultArray);

    int expectedResult = expectedResultArray[expectedResultArray.length / 2];

    medianTestCase.setExpectedOutput(new MedianOutput(expectedResult));

    return medianTestCase;

  }
}