package leetcode

import com.typesafe.scalalogging.LazyLogging

/**
 * (This problem is an interactive problem.)
 *
 * A row-sorted binary matrix means that all elements are 0 or 1 and each row of the matrix is sorted in non-decreasing order.
 *
 * Given a row-sorted binary matrix binaryMatrix, return the index (0-indexed) of the leftmost column with a 1 in it. If such an index does not exist, return -1.
 *
 * You can't access the Binary Matrix directly. You may only access the matrix using a BinaryMatrix interface:
 *
 * BinaryMatrix.get(row, col) returns the element of the matrix at index (row, col) (0-indexed).
 * BinaryMatrix.dimensions() returns the dimensions of the matrix as a list of 2 elements [rows, cols], which means the matrix is rows x cols.
 * Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer. Also, any solutions that attempt to circumvent the judge will result in disqualification.
 *
 * For custom testing purposes, the input will be the entire binary matrix mat. You will not have access to the binary matrix directly.
 *
 * Example 1:
 * Input: mat = [[0,0],[1,1]]
 * Output: 0
 *
 * Example 2:
 * Input: mat = [[0,0],[0,1]]
 * Output: 1
 *
 * Example 3:
 * Input: mat = [[0,0],[0,0]]
 * Output: -1
 *
 * Example 4:
 * Input: mat = [[0,0,0,1],[0,0,1,1],[0,1,1,1]]
 * Output: 1
 *
 * Constraints:
 * rows == mat.length
 * cols == mat[i].length
 * 1 <= rows, cols <= 100
 * mat[i][j] is either 0 or 1.
 * mat[i] is sorted in non-decreasing order.
 */

/**
 * // This is the BinaryMatrix's API interface.
 * // You should not implement it, or speculate about its implementation
 * class BinaryMatrix {
 *     def get(row: Int, col: Int): Int = {}
 *     def dimensions(): Array[Int] = {}
 * }
 */

object LeftMostColumnWithOne extends App with LazyLogging {
  case class BinaryMatrix(mat: Array[Array[Int]]) {
    def get(row: Int, col: Int): Int = {
      mat(row)(col)
    }
    def dimensions(): Array[Int] = {
      Array(mat.length, mat(0).length)
    }
  }

  def leftMostColumnWithOne(binaryMatrix: BinaryMatrix): Int = {
    val columnCount = binaryMatrix.dimensions()(1)
    val leftMostCol = leftMostColWithOneInMatrix(binaryMatrix, 0, columnCount)
    if(leftMostCol == columnCount) {
      -1 //all rows processed and there was no 1 in any rows
    } else {
      leftMostCol
    }
  }

  def leftMostColWithOneInMatrix(binaryMatrix: BinaryMatrix, row: Int, maxCol: Int): Int = {
    if (row == binaryMatrix.dimensions()(0)) { //all rows processed
      maxCol
    } else if (maxCol == 0) { //found best possible answer, terminate search
      maxCol
    } else {
      val newMaxCol = leftMostOneInRow(binaryMatrix, row, 0, maxCol)
      leftMostColWithOneInMatrix(binaryMatrix, row + 1, math.min(newMaxCol, maxCol)) //try next row
    }
  }

  def leftMostOneInRow(binaryMatrix: BinaryMatrix, row: Int, low: Int, high: Int): Int = {
    val length = high - low
    if (length <= 1) { //just 1 element, make decision based on it
      if(binaryMatrix.get(row, high - 1) == 0) Int.MaxValue else low
    } else if (binaryMatrix.get(row, high - 1) == 0) { //last element is 0, no point in exploring this segment
      Int.MaxValue
    } else {
      val mid = (low + high) / 2
      if (binaryMatrix.get(row, mid) == 1) { //search lower half
        //if lower half didnt find a better answer, return mid
        math.min(mid, leftMostOneInRow(binaryMatrix, row, low, mid))
      } else { //search upper half
        leftMostOneInRow(binaryMatrix, row, mid+1, high)
      }
    }
  }

  //optimal solution from leetcode
  def leftMostColumnWithOneOptimal(binaryMatrix: BinaryMatrix) = {
    val rows = binaryMatrix.dimensions()(0)
    val cols = binaryMatrix.dimensions()(1)
    // Set pointers to the top-right corner.
    var currentRow = 0
    var currentCol = cols - 1
    // Repeat the search until it goes off the grid.
    while (currentRow < rows && currentCol >= 0) {
      if (binaryMatrix.get(currentRow, currentCol) == 0)
        currentRow += 1
      else
        currentCol -= 1
    }

    // If we never left the last column, this is because it was all 0's.
    if (currentCol == cols - 1)
      -1
    else
      currentCol + 1
  }

  val input = Array(Array(0,0), Array(0,1))
  println(leftMostColumnWithOne(BinaryMatrix(input)))
  println(leftMostColumnWithOneOptimal(BinaryMatrix(input)))
}