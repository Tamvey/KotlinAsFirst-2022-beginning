package lesson11.task1

import java.lang.StringBuilder
/**
 * Класс "беззнаковое большое целое число".
 *
 * Общая сложность задания -- очень сложная, общая ценность в баллах -- 32.
 * Объект класса содержит целое число без знака произвольного размера
 * и поддерживает основные операции над такими числами, а именно:
 * сложение, вычитание (при вычитании большего числа из меньшего бросается исключение),
 * умножение, деление, остаток от деления,
 * преобразование в строку/из строки, преобразование в целое/из целого,
 * сравнение на равенство и неравенство
 */
class UnsignedBigInteger : Comparable<UnsignedBigInteger> {

    /**
     * Конструктор из строки
     */
    var nums: List<Int>

    constructor(s: String) {
        nums = s.toMutableList().map { it.toString().toInt() }
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        nums = i.toString().toMutableList().map { it.toString().toInt() }
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        var new = mutableListOf<Int>()
        var add = 0
        for (i in 0..maxOf(nums.size - 1, other.nums.size - 1)) {
            var nowSum = add
            if (i < nums.size) nowSum += nums[nums.size - 1 - i]
            if (i < other.nums.size) nowSum += other.nums[other.nums.size - 1 - i]
            new.add(nowSum % 10)
            add = nowSum / 10
        }
        if (add != 0) new.add(add)
        var st = ""; for (i in new.reversed()) st += i.toString()
        return UnsignedBigInteger(st)
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger): UnsignedBigInteger = TODO()

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnsignedBigInteger) return false
        if (this.nums.size != other.nums.size) return false
        for (i in 0 until this.nums.size) {
            if (this.nums[i] != other.nums[i]) return false
        }
        return true
    }

    /**
     * Сравнение на больше/меньше (по контракту Comparable.compareTo)
     */
    override fun compareTo(other: UnsignedBigInteger): Int = TODO()

    /**
     * Преобразование в строку
     */
    override fun toString(): String = TODO()

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = TODO()

}