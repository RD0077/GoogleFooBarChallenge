package rd.google.foobar.challange.level3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/*
 * 
 * Prepare the Bunnies' Escape
===========================

You're awfully close to destroying the LAMBCHOP doomsday device and freeing Commander Lambda's bunny workers, but once they're free of the work duties the bunnies are going to need to escape Lambda's space station via the escape pods as quickly as possible. Unfortunately, the halls of the space station are a maze of corridors and dead ends that will be a deathtrap for the escaping bunnies. Fortunately, Commander Lambda has put you in charge of a remodeling project that will give you the opportunity to make things a little easier for the bunnies. Unfortunately (again), you can't just remove all obstacles between the bunnies and the escape pods - at most you can remove one wall per escape pod path, both to maintain structural integrity of the station and to avoid arousing Commander Lambda's suspicions. 

You have maps of parts of the space station, each starting at a work area exit and ending at the door to an escape pod. The map is represented as a matrix of 0s and 1s, where 0s are passable space and 1s are impassable walls. The door out of the station is at the top left (0,0) and the door into an escape pod is at the bottom right (w-1,h-1). 

Write a function solution(map) that generates the length of the shortest path from the station door to the escape pod, where you are allowed to remove one wall as part of your remodeling plans. The path length is the total number of nodes you pass through, counting both the entrance and exit nodes. The starting and ending positions are always passable (0). The map will always be solvable, though you may or may not need to remove a wall. The height and width of the map can be from 2 to 20. Moves can only be made in cardinal directions; no diagonal moves are allowed.

Languages
=========

To provide a Python solution, edit solution.py
To provide a Java solution, edit Solution.java

Test cases
==========
Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

-- Python cases --
Input:
solution.solution([[0, 1, 1, 0], [0, 0, 0, 1], [1, 1, 0, 0], [1, 1, 1, 0]])
Output:
    7

Input:
solution.solution([[0, 0, 0, 0, 0, 0], [1, 1, 1, 1, 1, 0], [0, 0, 0, 0, 0, 0], [0, 1, 1, 1, 1, 1], [0, 1, 1, 1, 1, 1], [0, 0, 0, 0, 0, 0]])
Output:
    11

-- Java cases --
Input:
Solution.solution({{0, 1, 1, 0}, {0, 0, 0, 1}, {1, 1, 0, 0}, {1, 1, 1, 0}})
Output:
    7

Input:
Solution.solution({{0, 0, 0, 0, 0, 0}, {1, 1, 1, 1, 1, 0}, {0, 0, 0, 0, 0, 0}, {0, 1, 1, 1, 1, 1}, {0, 1, 1, 1, 1, 1}, {0, 0, 0, 0, 0, 0}})
Output:
    11
 */

class Pair {
	public int getFirst() {
		return first;
	}

	public void setFirst(int first) {
		this.first = first;
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		this.second = second;
	}

	public Pair(int first, int second) {
		this.first = first;
		this.second = second;
	}

	int first, second;

}

public class PrepareTheBunniesEscape {

	public static int[] X = { 0, 0, 1, -1 };
	public static int[] Y = { 1, -1, 0, 0 };

	public static int solution(int[][] map) {
		int row = map.length;
		int col = map[0].length;
		int ans = 1000;
		int[][] forward = bfs(map, row, col, 0, 0);
		int[][] backward = bfs(map, row, col, row - 1, col - 1);

		for (int i = 0; i < row; ++i) {
			for (int j = 0; j < col; ++j) {
				if (forward[i][j] > 0 && backward[i][j] > 0) {
					ans = Math.min(ans, forward[i][j] + backward[i][j] - 1);
				}
			}
		}
		return ans;

	}

	private static int[][] bfs(int[][] map, int row, int col, int xx, int yy) {
		int[][] dis = new int[row][col];
		Queue<Pair> Q = new LinkedList<>();
		Q.add(new Pair(xx, yy));
		for (int k = 0; k < row; ++k)
			Arrays.fill(dis[k], 0);

		dis[xx][yy] = 1;

		while (!Q.isEmpty()) {
			Pair first_ele = Q.poll();
			int x = first_ele.getFirst();
			int y = first_ele.getSecond();

			for (int i = 0; i < 4; ++i) {
				int new_x = x + X[i], new_y = y + Y[i];
				if (new_x >= 0 && new_y >= 0 && new_x < row && new_y < col && dis[new_x][new_y] == 0) {

					dis[new_x][new_y] = dis[x][y] + 1;
					if (map[new_x][new_y] == 1)
						continue;
					Q.add(new Pair(new_x, new_y));

				}
			}
		}
		return dis;
	}

	public static void main(String[] args) {
		int[][] arr = { { 0, 0, 0, 0, 0, 0 }, { 1, 1, 1, 1, 1, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 1, 1, 1, 1, 1 },
				{ 0, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0 } };
		// int [][] arr = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
		System.out.println(solution(arr));

	}
}