package leetcode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

/**
 * We are given a list schedule of employees, which represents the working time for each employee.
 *
 * Each employee has a list of non-overlapping Intervals, and these intervals are in sorted order.
 *
 * Return the list of finite intervals representing common, positive-length free time for all employees, also in sorted order.
 *
 * (Even though we are representing Intervals in the form [x, y], the objects inside are Intervals, not lists or arrays. For example, schedule[0][0].start = 1, schedule[0][0].end = 2, and schedule[0][0][0] is not defined).  Also, we wouldn't include intervals like [5, 5] in our answer, as they have zero length.
 *
 *
 *
 * Example 1:
 * Input: schedule = [[[1,2],[5,6]],[[1,3]],[[4,10]]]
 * Output: [[3,4]]
 * Explanation: There are a total of three employees, and all common
 * free time intervals would be [-inf, 1], [3, 4], [10, inf].
 * We discard any intervals that contain inf as they aren't finite.
 *
 * Example 2:
 * Input: schedule = [[[1,3],[6,7]],[[2,4]],[[2,5],[9,12]]]
 * Output: [[5,6],[7,9]]
 *
 *
 * Constraints:
 *
 * 1 <= schedule.length , schedule[i].length <= 50
 * 0 <= schedule[i].start < schedule[i].end <= 10^8
 **/
object EmployeeFreeTime extends App {
  case class Interval(start: Int, end: Int) {
    def isOverlap(other: Interval): Boolean = {
      if(other.start <= end) {
        true
      } else {
        false
      }
    }
  }

  def employeeFreeTimeOptimal(schedule: List[List[Interval]]): List[Interval] = {
    val minHeap = mutable.PriorityQueue[Interval]()(Ordering.by(-_.start))
    val ans = ListBuffer[Interval]()

    schedule.foreach(x => x.foreach(y => minHeap.enqueue(y)))

    println(minHeap)
    var lastEnd = -1
    while(minHeap.nonEmpty) {
      val current = minHeap.dequeue
      if(lastEnd != -1 && current.start > lastEnd)
        ans.addOne(Interval(lastEnd, current.start))
      lastEnd = Math.max(lastEnd, current.end)
    }
    ans.toList
  }

  def employeeFreeTime(schedule: List[List[Interval]]): List[Interval] = {
    if(schedule == null)
      Seq.empty[Interval]

    //flatmap into single List[Interval]
    //sort
    //merge
    //find gaps as free time

    val singleSchedule = schedule.flatMap(employeeSchedule => employeeSchedule)
    println(singleSchedule)

    val sortedSchedule = singleSchedule.sortWith((left, right) => {
      if(left.start == right.start){
        left.end < right.end
      } else {
        left.start < right.start
      }
    })
    println(sortedSchedule)

    val mergedSchedule = merge(sortedSchedule)
    println(mergedSchedule)

    if(mergedSchedule.size == 1) {
      List.empty[Interval]
    } else {
      mergedSchedule.sliding(2).map(pair => {
        Interval(pair.head.end, pair.tail.head.start)
      }).toList
    }
  }

  def merge(schedule: List[Interval]): List[Interval] = {
    if(schedule.size <= 1){
      return schedule
    }

    val first = schedule.head
    val second = schedule.tail.head
    if(second.start <= first.end) {
      merge(Interval(first.start, math.max(first.end, second.end)) +: schedule.tail.tail)
    } else {
      first +: merge(schedule.tail)
    }
  }

    //[[[1,2],[5,6]],[[1,3]],[[4,10]]]
  val employee1 = List(Interval(1,3), Interval(6,7))
  val employee2 = List(Interval(2,4))
  val employee3 = List(Interval(2,5), Interval(9,12))

  val employeeSchedule = List(employee1, employee2, employee3)
  println(employeeFreeTime(employeeSchedule))

  println(employeeFreeTimeOptimal(employeeSchedule))
}
