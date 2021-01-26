package leetcode.java;

import java.util.*;

/**
 * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [100,4,200,1,3,2]
 * Output: 4
 * Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
 * Example 2:
 *
 * Input: nums = [0,3,7,2,5,8,4,6,0,1]
 * Output: 9
 *
 *
 * Constraints:
 *
 * 0 <= nums.length <= 104
 * -109 <= nums[i] <= 109
 */
public class LongestConsecutiveSequence {

    /**
     * Copied from Solution tab.
     * My solution utilized one aspect of the input domain i.e. consecutive numbers lie at +1 or -1.
     * These guys go further and utilize the fact that if a consecutive list exist, it starts at some point
     * and everything after that is +1 the previous. So just find each starting point and count how long it
     * can continue. Use HashSet for O(1) lookups.
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num-1)) { //a new streak starts here because its previous does not exist in set.
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum+1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }

    /**
     * This still ends up being O(n^2) in some cases.
     */
    public int longestConsecutiveStillSlow(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;

        Map<Integer, Set<Integer>> consecutiveMap = new HashMap<>();
        Set<Integer> numsSet = new HashSet<>();
        for(int num: nums) {
            numsSet.add(num);
        }

        for(int num: numsSet) {
            Set<Integer> currList = consecutiveMap.getOrDefault(num, new HashSet<>());
            Set<Integer> nextList = null;
            Set<Integer> prevList = null;

            if(consecutiveMap.containsKey(num+1)) {
                nextList = consecutiveMap.get(num+1);
            }

            if(consecutiveMap.containsKey(num-1)) {
                prevList = consecutiveMap.get(num-1);
            }

            if(nextList != null && prevList != null) {
                Set<Integer> largerList = nextList.size() > prevList.size() ? nextList : prevList;
                Set<Integer> smallList = nextList == largerList ? prevList : nextList;
                largerList.addAll(smallList);
                for(int consecutiveNum : smallList) //this kills performance and makes it O(n^2) in some cases.
                    consecutiveMap.put(consecutiveNum, largerList);

                currList = largerList;
            } else if (nextList != null) {
                currList = nextList;
                consecutiveMap.put(num, currList);
            } else if (prevList != null) {
                currList = prevList;
                consecutiveMap.put(num, currList);
            } else {
                consecutiveMap.put(num, currList);
            }

            currList.add(num);
        }

        int longestLen = 1;

        for(Set<Integer> list : consecutiveMap.values()) {
            longestLen = Math.max(longestLen, list.size());
        }

        return longestLen;
    }

    /**
     * This still ends up being O(n^2) in some cases.
     */
    public int longestConsecutiveSlow(int[] nums) {
        if(nums == null || nums.length == 0)
            return 0;

        Map<Long, long[]> consecutiveMap = new HashMap<>();

        for(int numInt: nums) {
            long num = (long)numInt;
            long[] newConsecutiveValues = {Long.MAX_VALUE, Long.MAX_VALUE};
            long[] myConsecutiveValues = consecutiveMap.getOrDefault(num, newConsecutiveValues);
            if(consecutiveMap.containsKey(num+1)) {
                long[] nextConsecutiveValues = consecutiveMap.get(num+1);
                myConsecutiveValues[1] = num+1;
                nextConsecutiveValues[0] = num;
            }

            if(consecutiveMap.containsKey(num-1)) {
                long[] prevConsecutiveValues = consecutiveMap.get(num-1);
                myConsecutiveValues[0] = num-1;
                prevConsecutiveValues[1] = num;
            }
            consecutiveMap.put((long)num, myConsecutiveValues);
        }

        int longestLen = 1;
        while(consecutiveMap.size() > 0) {
            int len = 1;
            Iterator<Long> keyIterator = consecutiveMap.keySet().iterator();
            long first = keyIterator.next();
            long[] firstValue = consecutiveMap.remove(first);
            long prev = firstValue[0];
            while(prev != Long.MAX_VALUE) {
                len++;
                prev = consecutiveMap.remove(prev)[0];
            }

            long next = firstValue[1];
            while(next != Long.MAX_VALUE) {
                len++;
                next = consecutiveMap.remove(next)[1];
            }

            longestLen = Math.max(len, longestLen);
        }

        return longestLen;
    }

}
