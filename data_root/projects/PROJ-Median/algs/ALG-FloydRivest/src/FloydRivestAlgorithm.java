import java.lang.Math;
import java.util.Arrays;

/**
 *
 * @author tadej
 */
public class FloydRivestAlgorithm extends MedianAbsAlgorithm {

  @Override
  protected MedianOutput execute(MedianInput testCase) {
    MedianOutput result = new MedianOutput();

    int median = select(testCase.arrayToSelectFrom, 0, testCase.arrayToSelectFrom.length-1,
        (int)Math.floor(testCase.arrayToSelectFrom.length / 2));

    result.median = median;
    return result;
  }

  int sign(int x) {
    return ((x > 0) ? 1 : ((x < 0) ? -1 : 0));
  }

  public void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  int select(int arr[], int left, int right, int k) {
    int i;

    while (right > left)
    {
        // use select recursively to sample a smaller set of size s
        // the arbitrary constants 600 and 0.5 are used in the original
        // version to minimize execution time

        if (right - left > right)
        {
            int n = right - left + 1;
            i = k - left + 1;
            int s = (2 * n / 3);
            int sd = (n * s * (n - s) / n) * sign(i - n / 2);
            int ll = Math.max(left, k - i * s / n + sd);
            int rr = Math.min(right, k + (n - i) * s / n + sd);
            select(arr, ll, rr, k);
        }
        // partition the elements between left and right around t
        int t = arr[k];
        i = left;
        int j = right;

        swap(arr,left,k);

        if (arr[right] > t)
        {
            swap(arr,right,left);
        }

        while (i < j)
        {
            swap(arr,i,j);
            i++;
            j--;

            while (arr[i] < t)
            {
                i++;
            }

            while (arr[j] > t)
            {
                j--;
            }
        }

        if (arr[left] == t)
        {
            swap(arr,left, j);
        }             
        else
        {
            j++;
            swap(arr,j,right);
        }

        // adjust left and right towards the boundaries of the subset
        // containing the (k - left + 1)th smallest element
        if (j <= k)
        {
            left = j + 1;
        }
        if (k <= j) 
        {
            right = j - 1;
        }

    }
    return arr[k];
  }
}
