package lesson2.task2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun pointInsideCircle() {
        // (1, 1) inside circle: center = (0, 0), r = 2
        assertTrue(lesson2.task2.pointInsideCircle(1.0, 1.0, 0.0, 0.0, 2.0))
        // (2, 2) NOT inside circle: center = (0, 0), r = 2
        assertFalse(lesson2.task2.pointInsideCircle(2.0, 2.0, 0.0, 0.0, 2.0))
    }

    @Test
    @Tag("2")
    fun isNumberHappy() {
        assertTrue(lesson2.task2.isNumberHappy(1533))
        assertTrue(lesson2.task2.isNumberHappy(9009))
        assertFalse(lesson2.task2.isNumberHappy(3644))
    }

    @Test
    @Tag("2")
    fun queenThreatens() {
        assertTrue(lesson2.task2.queenThreatens(3, 6, 7, 6))
        assertTrue(lesson2.task2.queenThreatens(8, 1, 1, 8))
        assertFalse(lesson2.task2.queenThreatens(7, 6, 5, 7))
    }

    @Test
    @Tag("2")
    fun daysInMonth() {
        assertEquals(31, lesson2.task2.daysInMonth(1, 1990))
        assertEquals(28, lesson2.task2.daysInMonth(2, 1990))
        assertEquals(31, lesson2.task2.daysInMonth(3, 1990))
        assertEquals(30, lesson2.task2.daysInMonth(4, 1990))
        assertEquals(31, lesson2.task2.daysInMonth(5, 1990))
        assertEquals(30, lesson2.task2.daysInMonth(6, 1990))
        assertEquals(31, lesson2.task2.daysInMonth(7, 1990))
        assertEquals(31, lesson2.task2.daysInMonth(8, 1990))
        assertEquals(29, lesson2.task2.daysInMonth(2, 1992))
        assertEquals(29, lesson2.task2.daysInMonth(2, 1996))
        assertEquals(28, lesson2.task2.daysInMonth(2, 1900))
        assertEquals(29, lesson2.task2.daysInMonth(2, 2000))
    }

    @Test
    @Tag("2")
    fun circleInside() {
        assertFalse(lesson2.task2.circleInside(0.0, 0.0, 6.0, 0.0, 0.0, 5.0))
        assertFalse(lesson2.task2.circleInside(0.0, 0.0, 1.0, 10.0, 10.0, 9.0))
        assertTrue(lesson2.task2.circleInside(2.0, 2.0, 2.0, 2.0, 2.0, 2.0))
        assertTrue(lesson2.task2.circleInside(-2.0, 3.0, 2.0, -2.0, 0.0, 5.0))
        assertFalse(lesson2.task2.circleInside(1.0, 2.0, 3.0, 4.0, 5.0, 6.0))
    }

    @Test
    @Tag("3")
    fun brickPasses() {
        assertTrue(lesson2.task2.brickPasses(2, 10, 5, 6, 3))
        assertTrue(lesson2.task2.brickPasses(4, 4, 4, 4, 4))
        assertFalse(lesson2.task2.brickPasses(6, 5, 4, 3, 6))
        assertTrue(lesson2.task2.brickPasses(3, 2, 1, 1, 2))
    }
}