import java.util.Arrays;

/**
 *
 * @author tadej
 */
public class MedianOfMediansAlgorithm extends MedianAbsAlgorithm {

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

  public int partition(int[] arr, int left, int right, int val) {
    int i;
    for (i = left; i < right; i++) {
      if (arr[i] == val) {
        break;
      }
    }
    swap(arr, i, right);
    int pivotValue = arr[right];
    int storeIndex = left;
    for (i = left; i <= right; i++) {
      if (arr[i] < pivotValue) {
        swap(arr, storeIndex, i);
        storeIndex++;
      }
    }
    swap(arr, right, storeIndex);
    return storeIndex;
  }

  int findMedian(int arr[], int l, int len) {
    Arrays.sort(arr, l, l + len);
    return arr[l + len / 2];
  }

  int select(int arr[], int l, int r, int k) {
    if (k > 0 && k <= r - l + 1) {
      int n = r - l + 1, i;
      int median[] = new int[(n + 4) / 5];
      for (i = 0; i < n / 5; i++) {
        median[i] = findMedian(arr, l + i * 5, 5);
      }
      if (i * 5 < n) {
        median[i] = findMedian(arr, l + i * 5, n % 5);
        i++;
      }
      int medOfMed = (i == 1) ? median[i - 1] : select(median, 0, i - 1, i / 2);

      int pos = partition(arr, l, r, medOfMed);
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
