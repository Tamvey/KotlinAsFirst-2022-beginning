package lesson11.task1

import java.lang.ArithmeticException
import java.lang.Exception
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
    var nums: MutableList<Int>

    constructor(s: String) {
        nums = Regex("""[^0-9]""").replace(s, "").toMutableList().map { it.toString().toInt() }.toMutableList()
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        nums = Regex("""[^0-9]""").replace(i.toString(), "").toMutableList().map { it.toString().toInt() }.toMutableList()
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
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this.compareTo(other) == -1) throw ArithmeticException()
        if (this.compareTo(other) == 0) return UnsignedBigInteger("0")
        var new = mutableListOf<Int>()
        for (i in 0 until this.nums.size) {
            if (i >= other.nums.size) {
                new.add(this.nums[this.nums.size - 1 - i])
            } else {
                if (this.nums[this.nums.size - 1 - i] < other.nums[other.nums.size - 1 - i]) {
                    this.nums[this.nums.size - i - 2] = this.nums[this.nums.size - i - 2] - 1
                    new.add((10 + this.nums[this.nums.size - 1 - i] - other.nums[other.nums.size - 1 - i]) % 10)
                } else if (this.nums[this.nums.size - 1 - i] == other.nums[other.nums.size - 1 - i]) {
                    new.add((10 + this.nums[this.nums.size - 1 - i] - other.nums[other.nums.size - 1 - i]) % 10)
                } else {
                    new.add(this.nums[this.nums.size - 1 - i] - other.nums[other.nums.size - 1 - i])
                }
            }
        }
        var st = ""; for (i in new.reversed()) st += i.toString()
        return UnsignedBigInteger(st)
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        var new = mutableListOf<Int>()
        for (i in 0 until this.nums.size) {
            for (j in 0 until other.nums.size) {
                var got = other.nums[other.nums.size - 1 - j] * this.nums[this.nums.size - 1 - i]
                if (i + j >= new.size) new.add(0)
                new[i + j] += got % 10
                if (got / 10 != 0) {
                    if (i + j + 1 >= new.size) new.add(0)
                    new[i + j + 1] += got / 10
                }
            }
        }
        for (i in 0 until new.size - 1) {
            if (new[i] / 10 > 0) {
                new[i + 1] += new[i] / 10
                new[i] = new[i] % 10
            }
        }
        var last = (new[new.size - 1] / 10).toString()
        new[new.size - 1] = new[new.size - 1] % 10
        for (j in 0 until last.length) {
            new.add(last[j].toString().toInt())
        }
        var st = ""; for (i in new.reversed()) st += i.toString()
        st = st.removePrefix("0")
        return UnsignedBigInteger(st)
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger = TODO () /*{
        var pointer = 0
        var res = "0"
        var now = ""
        while (pointer < this.nums.size) {
            while (UnsignedBigInteger(now).compareTo(other) == -1 || pointer < this.nums.size) {
                now += this.nums[pointer].toString()
                pointer += 1
            }
            if (UnsignedBigInteger(now).compareTo(other) == -1) return UnsignedBigInteger(res)
            var number = UnsignedBigInteger("0")
            while (number.times(other).compareTo(UnsignedBigInteger(now)) < 1) number.plus(UnsignedBigInteger("1"))
            number = number.minus(UnsignedBigInteger("1"))
            res += number.nums.toString()
            now = this.minus(number.times(other)).toString()
        }
        return UnsignedBigInteger(res.removePrefix("0"))
    }*/

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
    override fun compareTo(other: UnsignedBigInteger): Int {
        if (this.nums.size > other.nums.size) return 1
        if (this.nums.size < other.nums.size) return -1
        for (i in other.nums.size - 1 downTo 0) {
            if (this.nums[i] > other.nums[i]) return 1
            if (this.nums[i] < other.nums[i]) return -1
        }
        return 0
    }

    /**
     * Преобразование в строку
     */
    override fun toString(): String {
        var st = ""
        for (i in this.nums) st += i
        return st
    }

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int = TODO() /*{
        try {
            var st = ""; for (i in this.nums.reversed()) st += i.toString()
            return st.toInt()
        } catch (e: Exception) {
            throw ArithmeticException()
        }
    }*/

}