import si.fri.algotest.execute.AbstractOutput;
import si.fri.algotest.execute.AbstractTestCase;

/**
 * 
 * @author tadej
 */
public class MedianOutput extends AbstractOutput {

  public int median;

  public MedianOutput() {
  }

  public MedianOutput(int data) {
    median = data;
  }

  @Override
  public String toString() {
    return super.toString() + ", Data: " + median;
  }

  @Override
  protected Object getIndicatorValue(AbstractTestCase testCase, AbstractOutput algorithmOutput, String indicatorName) {
    MedianTestCase medianTestCase = (MedianTestCase) testCase;
    MedianOutput medianAlgorithmOutput = (MedianOutput) algorithmOutput;

    switch (indicatorName) {
    case "Check":
      boolean checkOK = medianAlgorithmOutput.median == medianTestCase.getExpectedOutput().median;
      return checkOK ? "OK": "NOK";
    }

    return null;
  }
}