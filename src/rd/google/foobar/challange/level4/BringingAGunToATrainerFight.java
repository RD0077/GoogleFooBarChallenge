package rd.google.foobar.challange.level4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Bringing a Gun to a Trainer Fight
=================================

Uh-oh -- you've been cornered by one of Commander Lambdas elite bunny trainers! Fortunately, you grabbed a beam weapon from an abandoned storeroom while you were running through the station, so you have a chance to fight your way out. But the beam weapon is potentially dangerous to you as well as to the bunny trainers: its beams reflect off walls, meaning you'll have to be very careful where you shoot to avoid bouncing a shot toward yourself!

Luckily, the beams can only travel a certain maximum distance before becoming too weak to cause damage. You also know that if a beam hits a corner, it will bounce back in exactly the same direction. And of course, if the beam hits either you or the bunny trainer, it will stop immediately (albeit painfully). 

Write a function solution(dimensions, your_position, trainer_position, distance) that gives an array of 2 integers of the width and height of the room, an array of 2 integers of your x and y coordinates in the room, an array of 2 integers of the trainer's x and y coordinates in the room, and returns an integer of the number of distinct directions that you can fire to hit the elite trainer, given the maximum distance that the beam can travel.

The room has integer dimensions [1 < x_dim <= 1250, 1 < y_dim <= 1250]. You and the elite trainer are both positioned on the integer lattice at different distinct positions (x, y) inside the room such that [0 < x < x_dim, 0 < y < y_dim]. Finally, the maximum distance that the beam can travel before becoming harmless will be given as an integer 1 < distance <= 10000.

For example, if you and the elite trainer were positioned in a room with dimensions [3, 2], your_position [1, 1], trainer_position [2, 1], and a maximum shot distance of 4, you could shoot in seven different directions to hit the elite trainer (given as vector bearings from your location): [1, 0], [1, 2], [1, -2], [3, 2], [3, -2], [-3, 2], and [-3, -2]. As specific examples, the shot at bearing [1, 0] is the straight line horizontal shot of distance 1, the shot at bearing [-3, -2] bounces off the left wall and then the bottom wall before hitting the elite trainer with a total shot distance of sqrt(13), and the shot at bearing [1, 2] bounces off just the top wall before hitting the elite trainer with a total shot distance of sqrt(5).

Languages
=========

To provide a Java solution, edit Solution.java
To provide a Python solution, edit solution.py

Test cases
==========
Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

-- Java cases --
Input:
Solution.solution([3,2], [1,1], [2,1], 4)
Output:
    7

Input:
Solution.solution([300,275], [150,150], [185,100], 500)
Output:
    9

-- Python cases --
Input:
solution.solution([3,2], [1,1], [2,1], 4)
Output:
    7

Input:
solution.solution([300,275], [150,150], [185,100], 500)
Output:
    9
 */
public class BringingAGunToATrainerFight {

	public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {
		Map<Double, Double> hitYourself = getAngles(dimensions, your_position, your_position, distance);
		Map<Double, Double> hitGuard = getAngles(dimensions, your_position, trainer_position, distance);
		int solutions = 0;

		for (Double key : hitGuard.keySet()) {
			if (hitYourself.containsKey(key)) {
				if (hitGuard.get(key) < hitYourself.get(key)) {
					solutions++;
				}
			} else {
				solutions++;
			}
		}
		return solutions;
	}

	public static Double distance(int[] p1, int[] p2) {
		return Math.sqrt(((p2[0] - p1[0]) * (p2[0] - p1[0])) + ((p2[1] - p1[1]) * (p2[1] - p1[1])));
	}

	public static Double angle(int[] p1, int[] p2) {
		return Math.atan2(p2[0] - p1[0], p2[1] - p1[1]);
	}

	public static ArrayList<int[]> cartesianProduct(ArrayList<ArrayList<Integer>> series) {
		ArrayList<int[]> points = new ArrayList<>();

		for (int i = 0; i < series.get(0).size(); i++) {
			for (int j = 0; j < series.get(1).size(); j++) {
				int[] temp = { series.get(0).get(i), series.get(1).get(j) };
				points.add(temp);
			}
		}
		return points;
	}

	public static ArrayList<Integer> generateSeries(int[] dimensions, int[] your_position, int[] guard_position,
			int distance, int i) {
		int minimum = your_position[i] - distance;
		int maximum = your_position[i] + distance;

		int[] segment = { dimensions[i] - guard_position[i], guard_position[i] };
		ArrayList<Integer> series = new ArrayList<>();

		int current = guard_position[i];
		int segmentIndex = 0;
		while (current <= maximum) {
			series.add(current);
			current += 2 * segment[segmentIndex];
			if (segmentIndex == 1)
				segmentIndex = 0;
			else
				segmentIndex = 1;
		}

		current = guard_position[i];
		segmentIndex = 1;
		while (current >= minimum) {
			series.add(current);
			current -= 2 * segment[segmentIndex];
			if (segmentIndex == 1)
				segmentIndex = 0;
			else
				segmentIndex = 1;
		}

		return series;
	}

	public static ArrayList<ArrayList<Integer>> generateAllSeries(int[] dimensions, int[] your_position,
			int[] guard_position, int distance) {
		ArrayList<ArrayList<Integer>> series = new ArrayList<>();

		for (int i = 0; i < 2; i++) {
			series.add(generateSeries(dimensions, your_position, guard_position, distance, i));
		}

		return series;
	}

	public static Map<Double, Double> getAngles(int[] dimensions, int[] your_position, int[] guard_position,
			int distance) {
		Map<Double, Double> polarMap = new HashMap<>();

		for (int[] point : cartesianProduct(generateAllSeries(dimensions, your_position, guard_position, distance))) {
			Double r = distance(point, your_position);
			Double angle = angle(point, your_position);

			if ((point[0] != your_position[0] || point[1] != your_position[1]) && (r <= distance)) {
				if (!polarMap.containsKey(angle)) {
					polarMap.put(angle, r);
				} else {
					polarMap.put(angle, Math.min(polarMap.get(angle), r));
				}
			}
		}

		return polarMap;
	}

	public static void main(String[] args) {
		int[] dimenssion = { 3, 2 };
		int[] your_position = { 1, 1 };
		int[] guard_position = { 2, 1 };
		int distance = 4;
		System.out.println(solution(dimenssion, your_position, guard_position, distance));
	}
}
