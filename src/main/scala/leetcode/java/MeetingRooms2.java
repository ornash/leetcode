package leetcode.java;

import java.util.*;

/**
 * Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[0,30],[5,10],[15,20]]
 * Output: 2
 * Example 2:
 *
 * Input: intervals = [[7,10],[2,4]]
 * Output: 1
 *
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 104
 * 0 <= starti < endi <= 106
 */
public class MeetingRooms2 {
    class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public int minMeetingRooms(int[][] intervalsIn) {
        Interval[] intervals = new Interval[intervalsIn.length];

        for(int i = 0; i < intervalsIn.length; i++) {
            intervals[i] = new Interval(intervalsIn[i][0], intervalsIn[i][1]);
        }

        if(intervals.length == 0 || intervals.length == 1) {
            return intervals.length;
        }

        Arrays.sort(intervals, new Comparator<Interval>() {
            public int compare(Interval first, Interval second) {
                return first.start - second.start;
            }
        });

        PriorityQueue<Interval> minHeap = new PriorityQueue<Interval>(new Comparator<Interval> () {
            public int compare(Interval first, Interval second) {
                return first.end - second.end;
            }
        });

        minHeap.offer(intervals[0]);
        for(int i = 1; i < intervals.length ; i++) {
            Interval topInterval = minHeap.peek();
            Interval currentInterval = intervals[i];
            if(currentInterval.start >= topInterval.end) {
                //no overlap
                minHeap.poll();
            }
            minHeap.offer(currentInterval);
        }

        return minHeap.size();
    }
}
