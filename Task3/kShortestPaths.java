import java.util.PriorityQueue;

public class kShortestPaths {
	
	public static void main(String[] args) {
		// Will be an array of all the Paths found via Dijkstra's after user inputs pickup location
		int[] allPaths = {3, 46, 2, 56, 3, 38, 93, 45, 6, 787, 34, 76, 44, 6, 7, 86, 8, 44, 56};
		
		// k will be read in as input
		int[] result = getKShortestPaths(allPaths, 6);
		
		// This will ultimately return the k driver IDs who are shortest path away from the pickup location
		for (int i : result) {
			System.out.print(i + ",");
		}
	}

	public static int[] getKShortestPaths(int[] allPaths, int k) {
		// Instead of PriorityQuee of Integers, should be of Paths
		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();		
		for (int i = 0; i < allPaths.length; ++i) {
			int currentNum = allPaths[i];
		
			if (minHeap.size() < allPaths.length) {
				minHeap.add(currentNum);
			} else if (currentNum < minHeap.peek()) {
				minHeap.poll();
				minHeap.add(currentNum);
			}
	  	}
		
		int[] kpaths = new int[k]; 
		int index = 0;

		while (index < k) {
			kpaths[index++] = minHeap.poll();
		}
	
		return kpaths;
	}
}
