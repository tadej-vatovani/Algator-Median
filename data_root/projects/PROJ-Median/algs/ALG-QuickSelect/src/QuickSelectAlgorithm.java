import java.util.Arrays;

/**
 *
 * @author tadej
 */
public class QuickSelectAlgorithm extends MedianAbsAlgorithm {

  @Override
  protected MedianOutput execute(MedianInput testCase) {
    MedianOutput result = new MedianOutput();

    int median = select(testCase.arrayToSelectFrom, 0, testCase.arrayToSelectFrom.length - 1,
      (int)Math.floor(testCase.arrayToSelectFrom.length / 2) + 1);
    result.median = median;
    return result;
  }

  public void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public int partition(int[] arr, int l, int r) {
    int pivotValue = arr[r];
    int storeIndex = l;
    for (int i = l; i <= r - 1; i++) {
      if (arr[i] <= pivotValue) {
        swap(arr, storeIndex, i);
        storeIndex++;
      }
    }
    swap(arr, storeIndex, r);
    return storeIndex;
  }

  int select(int arr[], int l, int r, int k) {
    if (k > 0 && k <= r - l + 1) {
      int pos = partition(arr, l, r);
      if (pos - l == k - 1) {
        return arr[pos];
      }
      if (pos - l > k - 1) {
        return select(arr, l, pos - 1, k);
      }
      return select(arr, pos + 1, r, k - pos + l - 1);
    }
    return Integer.MAX_VALUE;
  }
}
