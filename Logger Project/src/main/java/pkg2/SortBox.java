package pkg2;

/**
 * Created by maharshigor on 20/07/16.
 */
public class SortBox {

	private Integer[] input;

	public Integer[] sort(Integer[] input) {
		this.input = input.clone ();
		int n = this.input.length;
		recurse (0,n-1);
		return this.input;
	}

	private void merge(int l, int h) {
		int m = (l+h)/2;

		Integer[] temp = new Integer[h-l+1];

		int i = l;
		int j = m+1;
		int k = 0;

		while(i <= m && j <= h) {
			if(input[i] <= input[j]) {
				temp[k++] = input[i++];
			} else {
				temp[k++] = input[j++];
			}
		}

		while(i <= m) temp[k++] = input[i++];
		while(j <= h) temp[k++] = input[j++];

		for(k=l; k<=h; k++)  input[k] = temp[k-l];

	}

	private void recurse(int l, int h) {
		if(h > l) {
			int m = (l+h)/2;
			recurse (l,m);
			recurse (m+1,h);
			merge (l,h);
		}
	}
}
