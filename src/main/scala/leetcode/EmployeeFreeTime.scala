package leetcode

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

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
