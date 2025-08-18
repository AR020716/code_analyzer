// package F;

import java.util.*;

public class F {
	
	public static void main(String[] args) {
		
		HashSet<Integer> mySet = new HashSet<>();
		mySet.add(9);
		mySet.add(10);
		mySet.add(11);
		
		String f = "";
		
		int n = 19;
		
		for (int i=1; i <=n; i++) {
			f = f + "*";
			if (i>15 || mySet.contains(i)) {
				f = f + "\n";
			}
		}
		
		System.out.println(f);
	}

}
