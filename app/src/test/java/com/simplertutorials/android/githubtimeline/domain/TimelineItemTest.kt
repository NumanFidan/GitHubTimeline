package com.simplertutorials.android.githubtimeline.domain

import com.simplertutorials.android.githubtimeline.utils.TestUtils
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class TimelineItemTest {

    /*
        Compare two equal TimelineItem
        returns OBJECTS_EQUALS
     */
    @Test
    fun compareTimelineItem_twoEqualItem_returnObjectsEqual(){
        val item1 = TimelineItem("a","b",TestUtils.timeStamp_smaller)
        val item2 = TimelineItem("a","b",TestUtils.timeStamp_smaller)

        assertEquals(TimelineItem.OBJECTS_EQUAL, item1.compareTo(item2))
    }
    /*
        Compare two different TimelineItem
        first one smaller
        returns OBJECT_SMALLER
     */
    @Test
    fun compareTimelineItem_firstOneSmaller_returnObjectSmaller(){
        val item1 = TimelineItem("a","b",TestUtils.timeStamp_smaller)
        val item2 = TimelineItem("a","b",TestUtils.timeStamp_bigger)

        assertEquals(TimelineItem.OBJECT_SMALLER, item1.compareTo(item2))
    }
    /*
        Compare two different TimelineItem
        second one smaller
        returns OBJECT_BIGGER
     */
    @Test
    fun compareTimelineItem_secondOneSmaller_returnObjectBigger(){
        val item1 = TimelineItem("a","b",TestUtils.timeStamp_bigger)
        val item2 = TimelineItem("a","b",TestUtils.timeStamp_smaller)

        assertEquals(TimelineItem.OBJECT_BIGGER, item1.compareTo(item2))
    }

    /*
        Compare two different TimelineItem
        First one has unsuitable timeStamp
        returns OBJECT_SMALLER
     */
    @Test
    fun compareTimelineItem_firstWithUnsuitableTimeStamp_returnObjectSmaller(){
        val unsuitableTimeStamp = "unsuitableTimeStamp"
        val item1 = TimelineItem("a","b", unsuitableTimeStamp)
        val item2 = TimelineItem("a","b", TestUtils.timeStamp_bigger)

        assertEquals(TimelineItem.OBJECT_SMALLER, item1.compareTo(item2))
    }

    /*
        Compare two different TimelineItem
        Second one has unsuitable timeStamp
        returns OBJECT_BIGGER
     */
    @Test
    fun compareTimelineItem_secondWithUnsuitableTimeStamp_returnObjectBigger(){
        val unsuitableTimeStamp = "unsuitableTimeStamp"
        val item1 = TimelineItem("a","b", TestUtils.timeStamp_bigger)
        val item2 = TimelineItem("a","b", unsuitableTimeStamp)

        assertEquals(TimelineItem.OBJECT_BIGGER, item1.compareTo(item2))
    }

    /*
        Compare two different TimelineItem
        Both of them  has unsuitable timeStamp
        returns OBJECTS_EQUAL
     */
    @Test
    fun compareTimelineItem_bothWithUnsuitableTimeStamp_returnObjectEqual(){
        val unsuitableTimeStamp = "unsuitableTimeStamp"
        val item1 = TimelineItem("a","b", unsuitableTimeStamp)
        val item2 = TimelineItem("a","b", unsuitableTimeStamp)

        assertEquals(TimelineItem.OBJECTS_EQUAL, item1.compareTo(item2))
    }

}