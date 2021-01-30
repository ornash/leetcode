package leetcode.java;

import java.util.*;

/**
 * Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 *
 *
 * Example 1:
 *
 * Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 * Example 2:
 *
 * Input: intervals = [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 *
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 104
 * intervals[i].length == 2
 * 0 <= starti <= endi <= 104
 */
public class MergeIntervals {
    class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public List<Interval> merge(List<Interval> intervals) {
        if(intervals == null || intervals.isEmpty()) {
            return Collections.emptyList();
        }

        intervals.sort(new Comparator<Interval>() {

            @Override
            public int compare(Interval o1, Interval o2) {
                int diff = o1.start - o2.start;
                return diff == 0 ? o1.end - o2.end : diff;

            }
        });

        List<Interval> result = new ArrayList<>();
        Iterator<Interval> intervalIter = intervals.iterator();
        Interval currentInterval = intervalIter.next();
        currentInterval = new Interval(currentInterval.start, currentInterval.end);

        while(intervalIter.hasNext()) {
            Interval nextInterval = intervalIter.next();
            if(isOverlap(currentInterval, nextInterval)) {
                currentInterval.end = Math.max(nextInterval.end, currentInterval.end); //merge
            } else {
                result.add(currentInterval);
                currentInterval = new Interval(nextInterval.start, nextInterval.end);
            }
        }

        result.add(currentInterval);
        return result;
    }

    boolean isOverlap(Interval earlier, Interval later) {
        return earlier.end >= later.start;
    }

}
