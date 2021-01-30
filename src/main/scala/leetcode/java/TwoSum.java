package leetcode.java;

import java.util.*;

/**
 * Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * You can return the answer in any order.
 *
 *
 *
 * Example 1:
 *
 * Input: nums = [2,7,11,15], target = 9
 * Output: [0,1]
 * Output: Because nums[0] + nums[1] == 9, we return [0, 1].
 * Example 2:
 *
 * Input: nums = [3,2,4], target = 6
 * Output: [1,2]
 * Example 3:
 *
 * Input: nums = [3,3], target = 6
 * Output: [0,1]
 *
 *
 * Constraints:
 *
 * 2 <= nums.length <= 103
 * -109 <= nums[i] <= 109
 * -109 <= target <= 109
 * Only one valid answer exists.
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] ids = new int[2];

        /*for(int i = 0; i < nums.length; i++) {
            int expect = target - nums[i];
            for(int j = 0; j < nums.length; j++) {
                if(j != i && expect == nums[j]) {
                    ids[0] = i;
                    ids[1] = j;
                    return ids;
                }
            }
        }*/

       /* Map<Integer, Integer> diff = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            diff.put(target - nums[i], i);

        }

        for(int i = 0; i < nums.length; i++) {
            Integer buddyIdx = diff.get(nums[i]);
            if(buddyIdx != null && buddyIdx != i) {
                    ids[0] = i;
                    ids[1] = buddyIdx;
                    return ids;
            }

        }*/

        Map<Integer, Integer> diff = new HashMap<>();
        for(int i = 0; i < nums.length; i++) {
            Integer buddyIdx = diff.get(nums[i]);
            if(buddyIdx != null) {
                ids[0] = buddyIdx;
                ids[1] = i;
                return ids;
            }
            diff.put(target - nums[i], i);
        }

        return ids;
    }
}
