package lesson2.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

class Tests {
    @Test
    @Tag("Example")
    fun quadraticRootNumber() {
        assertEquals(1, lesson2.task1.quadraticRootNumber(1.0, 2.0, 1.0))
        assertEquals(2, lesson2.task1.quadraticRootNumber(1.0, 3.0, 2.0))
        assertEquals(0, lesson2.task1.quadraticRootNumber(1.0, 4.0, 10.0))
    }

    @Test
    @Tag("Example")
    fun gradeNotation() {
        assertEquals("отлично", lesson2.task1.gradeNotation(5))
        assertEquals("удовлетворительно", lesson2.task1.gradeNotation(3))
    }

    @Test
    @Tag("Example")
    fun minBiRoot() {
        assertEquals(Double.NaN, lesson2.task1.minBiRoot(0.0, 0.0, 1.0), 1e-2)
        assertEquals(Double.NaN, lesson2.task1.minBiRoot(0.0, 1.0, 2.0), 1e-2)
        assertEquals(-2.0, lesson2.task1.minBiRoot(0.0, 1.0, -4.0), 1e-10)
        assertEquals(Double.NaN, lesson2.task1.minBiRoot(1.0, -2.0, 4.0), 1e-2)
        assertEquals(Double.NaN, lesson2.task1.minBiRoot(1.0, 3.0, 2.0), 1e-2)
        assertEquals(-1.41, lesson2.task1.minBiRoot(1.0, -3.0, 2.0), 1e-2)
    }

    @Test
    @Tag("2")
    fun ageDescription() {
        assertEquals("1 год", lesson2.task1.ageDescription(1))
        assertEquals("21 год", lesson2.task1.ageDescription(21))
        assertEquals("132 года", lesson2.task1.ageDescription(132))
        assertEquals("12 лет", lesson2.task1.ageDescription(12))
        assertEquals("111 лет", lesson2.task1.ageDescription(111))
        assertEquals("199 лет", lesson2.task1.ageDescription(199))
    }

    @Test
    @Tag("2")
    fun timeForHalfWay() {
        assertEquals(2.5, lesson2.task1.timeForHalfWay(1.0, 5.0, 2.0, 4.0, 3.0, 3.0), 1e-2)
        assertEquals(3.67, lesson2.task1.timeForHalfWay(4.0, 3.0, 1.0, 4.0, 1.0, 6.0), 1e-2)
        assertEquals(4.4, lesson2.task1.timeForHalfWay(3.0, 0.0, 1.0, 6.0, 2.0, 5.0), 1e-2)
    }

    @Test
    @Tag("2")
    fun whichRookThreatens() {
        assertEquals(0, lesson2.task1.whichRookThreatens(1, 2, 3, 4, 5, 6))
        assertEquals(1, lesson2.task1.whichRookThreatens(5, 3, 7, 3, 4, 8))
        assertEquals(2, lesson2.task1.whichRookThreatens(6, 8, 8, 6, 6, 3))
        assertEquals(3, lesson2.task1.whichRookThreatens(3, 7, 8, 7, 3, 5))
    }

    @Test
    @Tag("2")
    fun rookOrBishopThreatens() {
        assertEquals(0, lesson2.task1.rookOrBishopThreatens(4, 5, 5, 7, 8, 8))
        assertEquals(1, lesson2.task1.rookOrBishopThreatens(2, 8, 6, 8, 1, 6))
        assertEquals(2, lesson2.task1.rookOrBishopThreatens(5, 4, 3, 7, 1, 8))
        assertEquals(3, lesson2.task1.rookOrBishopThreatens(1, 6, 7, 6, 3, 8))
    }

    @Test
    @Tag("2")
    fun triangleKind() {
        assertEquals(-1, lesson2.task1.triangleKind(3.0, 7.5, 4.0))
        assertEquals(1, lesson2.task1.triangleKind(5.0, 3.0, 4.0))
        assertEquals(2, lesson2.task1.triangleKind(4.0, 6.0, 8.0))
        assertEquals(0, lesson2.task1.triangleKind(1.0, 1.5, 1.5))
    }

    @Test
    @Tag("3")
    fun segmentLength() {
        assertEquals(-1, lesson2.task1.segmentLength(1, 2, 3, 4))
        assertEquals(-1, lesson2.task1.segmentLength(5, 7, 1, 3))
        assertEquals(0, lesson2.task1.segmentLength(1, 2, 2, 4))
        assertEquals(3, lesson2.task1.segmentLength(3, 6, 0, 9))
        assertEquals(2, lesson2.task1.segmentLength(2, 5, 3, 9))
        assertEquals(1, lesson2.task1.segmentLength(3, 6, 1, 4))
        assertEquals(4, lesson2.task1.segmentLength(1, 15, 10, 14))
    }
}