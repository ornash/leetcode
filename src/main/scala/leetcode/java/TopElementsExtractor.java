package leetcode.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TopElementsExtractor {
    private static void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }

    public static class QuickSelectApproach {
        /**
         * Partition by moving all greater elements to the left of pivot
         */
        private int partition(int[] nums, int left, int right, int pivotIndex) {
            int pivot = nums[pivotIndex];
            // 1. move pivot to end
            swap(nums, pivotIndex, right);
            int storeIndex = left;

            // 2. move all greater elements to the left of pivot
            for (int i = left; i <= right; i++) {
                if (nums[i] > pivot) {
                    swap(nums, storeIndex, i);
                    storeIndex++;
                }
            }

            // 3. move pivot to its final place
            swap(nums, storeIndex, right);

            return storeIndex;
        }

        /**
         * Returns the k-th largest element of list within left..right of nums.
         */
        private int quickSelect(int[] nums, int left, int right, int kLargest) {
            if (left == right) { // If the list contains only one element,
                return nums[left];  // return that element
            }

            // select a random pivot_index
            int pivotIndex = left + new Random().nextInt(right - left);
            pivotIndex = partition(nums, left, right, pivotIndex);

            // the pivot is on (N - k)th smallest position
            if (kLargest == pivotIndex) {
                return nums[pivotIndex];
            } else if (kLargest < pivotIndex) {  // go left side
                return quickSelect(nums, left, pivotIndex - 1, kLargest);
            }
            // go right side
            return quickSelect(nums,pivotIndex + 1, right, kLargest);
        }

        public int findKthLargest(int[] nums, int k) {
            int size = nums.length;
            return quickSelect(nums,0, size - 1, k);
        }

        public List<int[]> maxElementsWindow(int[] input, int windowSize, int topN) {
            long startTime = System.currentTimeMillis();
            List<int[]> results = new ArrayList<>();

            for (int i = 0; i < input.length; i++) {
                int windowEnd = i + windowSize; //exclusive
                if (windowEnd <= input.length) {
                    int[] window = Arrays.copyOfRange(input, i, windowEnd);
                    findKthLargest(window, topN-1);
                    int[] topNElements = Arrays.copyOfRange(window, 0 , topN);
                    results.add(topNElements);
                } else {
                    break;
                }
            }
            System.out.println("QuickSelect total time: " + (System.currentTimeMillis() - startTime));
            return results;
        }
    }

    public static class SlidingWindowApproach {
        private int[] sortedWindow; //ascending order

        private int binarySearch(int l, int r, int x) {
            if (r >= l) {
                int mid = l + (r - l) / 2;

                // If the element is present at the middle itself
                if (sortedWindow[mid] == x)
                    return mid;

                // If element is smaller than mid, then it can only be present in left subarray
                if (sortedWindow[mid] > x)
                    return binarySearch(l, mid - 1, x);

                // Else the element can only be present in right subarray
                return binarySearch(mid + 1, r, x);
            }

            // not possible because element is guaranteed to be present in array
            throw new RuntimeException("not possible");
        }

        private void removeAndAddElement(int removeElement, int addElement) {
            //1. find index of removeElement
            int removeIndex = binarySearch(0, sortedWindow.length - 1, removeElement);
            //2. replace removeElement with addElement
            sortedWindow[removeIndex] = addElement;

            //3. Move newly added "addElement" at "atIndex" to its correct place in the sortedWindow.
            int atIndex = removeIndex;
            //keep moving the element at "atIndex" to left until it is greater than its left
            for(int i = atIndex; i > 0; i--) {
                if(sortedWindow[i] > sortedWindow[i-1]) {
                    break;
                }
                swap(sortedWindow, i, i-1);
            }

            //keep moving the element at "atIndex" to right until it is smaller than its right
            for(int i = atIndex; i < sortedWindow.length - 1; i++) {
                if(sortedWindow[i] < sortedWindow[i + 1]) {
                    break;
                }
                swap(sortedWindow, i, i+1);
            }
        }

        private int[] getTop(int m) {
            int end = sortedWindow.length;
            int begin = sortedWindow.length - m;
            return Arrays.copyOfRange(sortedWindow, begin, end);
        }

        public List<int[]> maxElementsWindow(int[] input, int windowSize, int topN) {
            long startTime = System.currentTimeMillis();
            List<int[]> results = new ArrayList<>();

            //sort the first window
            this.sortedWindow = Arrays.copyOfRange(input, 0, windowSize);
            Arrays.sort(sortedWindow);
            results.add(getTop(topN));

            for (int i = 1; i < input.length; i++) {
                int windowEnd = i + windowSize; //exclusive
                if (windowEnd <= input.length) {
                    removeAndAddElement(input[i - 1], input[windowEnd - 1]); //remove one element and add one element.
                    results.add(getTop(topN));
                }  else {
                    break;
                }
            }

            System.out.println("Sliding window total time: " + (System.currentTimeMillis() - startTime));
            return results;
        }
    }

    public static int[] generateRandomArray(int min, int max, int size) {
        int[] randomArray = new int[size];
        int range = max - min;

        for(int i = 0; i < size; i++) {
            randomArray[i] = (int)(Math.random() * range);
        }
        return randomArray;
    }

    public static void compareResults(List<int[]> quickSelectApproach, List<int[]> slidingWindowApproach) {
        System.out.println("Comparing results from QuickSelect and SlidingWindow approach.");
        for(int i = 0; i < slidingWindowApproach.size(); i++) {
            int[] quickSelectTopN = quickSelectApproach.get(i);
            Arrays.sort(quickSelectTopN);
            int[] slidingWindowTopN = slidingWindowApproach.get(i);
            Arrays.sort(slidingWindowTopN);

            if(!Arrays.equals(quickSelectTopN, slidingWindowTopN)) {
                System.out.println("Results don't match for i: "  + i);
                System.out.println("QuickSelect result: " + Arrays.toString(quickSelectTopN));
                System.out.println("SlidingWindow result: " + Arrays.toString(slidingWindowTopN));

                throw new RuntimeException("Results don't match for i: " + i);
            }
        }
    }

    public static void inteviewTest() {
        System.out.println("Interview test.");
        int[] quickSelectInput = {8, 7, 3, 5, 3, 6, 19, 2};
        int[] slidingWindowInput = Arrays.copyOf(quickSelectInput, quickSelectInput.length);
        int windowSize = 5;
        int topN = 3;

        //O(windowSize * lg(windowSize)) + O(input * (windowSize + lg(windowSize)) + topN) //cache friendly, simple, no random access
        List<int[]> slidingWindowApproach = new SlidingWindowApproach().maxElementsWindow(slidingWindowInput, windowSize, topN);
        //O(input * (2*windowSize))
        List<int[]> quickSelectApproach = new QuickSelectApproach().maxElementsWindow(quickSelectInput, windowSize, topN);

        compareResults(quickSelectApproach, slidingWindowApproach);
    }

    /**
     * @param orderOfArray
     * 0 -> test with ascending order
     * 1 -> test with descending order
     * n -> test with random order
     */
    public static void testAnyArray(int orderOfArray) {
        int windowSize = 10000;
        int topN = 1000;
        int[] quickSelectInput = generateRandomArray(0, 10000000, 1000000);
        if(orderOfArray == 0) {
            System.out.println("\nAscending ordered array test.");
            Arrays.sort(quickSelectInput);
        } else if (orderOfArray == 1) {
            System.out.println("\nDescending ordered array test.");
            Arrays.sort(quickSelectInput);
            int start = 0;
            int end = quickSelectInput.length -1;
            while (start < end) {//reverse the array
                swap(quickSelectInput, start, end);
                start++; end--;
            }
        } else {
            System.out.println("\nRandomly ordered array test.");
        }

        int[] slidingWindowInput = Arrays.copyOf(quickSelectInput, quickSelectInput.length);

        //O(windowSize * lg(windowSize)) + O(input * (windowSize + lg(windowSize) + topN)) //cache friendly, simple, no random access
        List<int[]> slidingWindowApproach = new SlidingWindowApproach().maxElementsWindow(slidingWindowInput, windowSize, topN);
        //O(input * (2*windowSize + topN))
        List<int[]> quickSelectApproach = new QuickSelectApproach().maxElementsWindow(quickSelectInput, windowSize, topN);

        compareResults(quickSelectApproach, slidingWindowApproach);
    }

    public static void main(String[] args) {
        inteviewTest();
        testAnyArray(3); //random order
        testAnyArray(0); //ascending order
        testAnyArray(1); //descending order
    }
}