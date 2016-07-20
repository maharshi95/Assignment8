package pkg2;

/**
 * Created by maharshigor on 20/07/16.
 */
public class Main {
	public static void main(String[] args) {
		SortBox sortBox = new SortBox ();
		Integer[] input = new Integer[]{4,3,5,77,2,44,111,65,33,77,66,46,44,21,90,33,554,23};
		Integer[] output = sortBox.sort (input);
		for(Integer i : output){
			System.out.print("" + i + " ");
		}
	}
}
