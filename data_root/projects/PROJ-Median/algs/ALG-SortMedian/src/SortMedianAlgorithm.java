/**
 *
 * @author tadej
 */
public class SortMedianAlgorithm extends MedianAbsAlgorithm {

  @Override
  protected MedianOutput execute(MedianInput testCase) {
    MedianOutput result = new MedianOutput();

    quickSort(testCase.arrayToSelectFrom, 0, testCase.arrayToSelectFrom.length - 1);
    int median = testCase.arrayToSelectFrom[(int)Math.floor(testCase.arrayToSelectFrom.length / 2)];

    result.median = median;
    return result;
  }

  void quickSort(int[] arr, int left, int right) {
    int i = left, j = right, tmp;

    int pivot = arr[(left + right) / 2];

    /* partition */
    while (i <= j) {
      while (arr[i] < pivot) {
        i++;
      }

      while (arr[j] > pivot) {
        j--;
      }

      if (i <= j) {
        tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        i++;
        j--;
      }

    };

    /* recursion */
    if (left < j)
      quickSort(arr, left, j);

    if (i < right)
      quickSort(arr, i, right);
  }
}
