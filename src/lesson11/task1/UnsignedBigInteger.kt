package lesson11.task1
//import kotlin.math.*
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
    val nums = ArrayList<Int>()
    val base = Math.pow(10.0, 9.0).toInt()
    private var ubistr = ""

    constructor(s: String) {
        ubistr = Regex("""[^0-9]""").replace(s, "")
        for (i in (ubistr.length % 9)..ubistr.length step 9) {
            val substr = ubistr.substring(maxOf(0, i - 9), i)
            if (substr.isNotEmpty()) nums.add(substr.toInt() + base)
        }
        nums.reverse()
    }

    /**
     * Конструктор из целого
     */
    constructor(i: Int) {
        ubistr = i.toString()
        if (i / base > 0) nums.add(i / base + base)
        nums.add(i % base + base)
        nums.reverse()
    }

    fun formString(new: MutableList<Int>): String {
        var res = ""
        for (i in new.reversed()) {
            if (i % base == 0 && res.isEmpty()) continue
            else if (res.isEmpty()) res += ((i % base).toString())
            else res += (i.toString().substring(1, i.toString().length))
        }
        return res
    }

    /**
     * Сложение
     */
    operator fun plus(other: UnsignedBigInteger): UnsignedBigInteger {
        val new = mutableListOf<Int>()
        var add = 0
        for (i in 0..maxOf(nums.size, other.nums.size)) {
            var nowSum = add
            if (i < nums.size) nowSum += nums[i] % base
            if (i < other.nums.size) nowSum += other.nums[i] % base
            new.add(nowSum % base + base)
            add = nowSum / base
        }
        if (add != 0) new.add(add)
        return UnsignedBigInteger(formString(new))
    }

    /**
     * Вычитание (бросить ArithmeticException, если this < other)
     */
    operator fun minus(other: UnsignedBigInteger): UnsignedBigInteger {
        if (this.compareTo(other) == -1) throw ArithmeticException()
        if (this.compareTo(other) == 0) return UnsignedBigInteger("0")
        val new = mutableListOf<Int>()
        var toSubstract = 0
        for (i in 0 until nums.size) {
            var varr = toSubstract
            if (other.nums.size > i) varr += other.nums[i]
            if (nums[i] < varr) {
                new.add(nums[i] - varr + 2 * base)
                toSubstract = 1
            } else {
                new.add(base + nums[i] - varr)
                toSubstract = 0
            }
        }
        return UnsignedBigInteger(formString(new))
    }

    /**
     * Умножение
     */
    operator fun times(other: UnsignedBigInteger): UnsignedBigInteger {
        var result = UnsignedBigInteger("0")
        nums.mapIndexed { idx, value ->
            other.nums.mapIndexed { idx2, value2 ->
                val f = (value - base).toString()
                val s = (value2 - base).toString()
                for (i in 0 until f.length) {
                    for (j in 0 until s.length) {
                        result = result.plus(
                            UnsignedBigInteger(
                                ((f[i].code - 48) * (s[j].code - 48)).toString() + "0"
                                    .repeat(((idx * 9) + f.length - i - 1) + ((idx2 * 9) + s.length - j - 1))
                            )
                        )
                    }
                }
            }
        }
        return result
    }

    /**
     * Деление
     */
    operator fun div(other: UnsignedBigInteger): UnsignedBigInteger {
        var bufer = UnsignedBigInteger("1")
        var copy = UnsignedBigInteger(ubistr)
        var res = UnsignedBigInteger("0")
        while (copy.compareTo(bufer.times(other)) != -1) {
            bufer = bufer.times(UnsignedBigInteger("10"))
        }
        if (bufer.ubistr != "1") bufer = UnsignedBigInteger(bufer.ubistr.substring(0, bufer.ubistr.length - 1))
        while (bufer.ubistr.isNotEmpty()) {
            while (copy.compareTo(bufer.times(other)) != -1) {
                copy = copy.minus(bufer.times(other))
                res = res.plus(bufer)
            }
            bufer = UnsignedBigInteger(bufer.ubistr.substring(0, bufer.ubistr.length - 1))
        }
        return res
    }

    /**
     * Взятие остатка
     */
    operator fun rem(other: UnsignedBigInteger) = this.minus(this.div(other).times(other))

    /**
     * Сравнение на равенство (по контракту Any.equals)
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UnsignedBigInteger) return false
        return this.nums == other.nums
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
    override fun toString(): String = ubistr

    /**
     * Преобразование в целое
     * Если число не влезает в диапазон Int, бросить ArithmeticException
     */
    fun toInt(): Int {
        try {
            return ubistr.toInt()
        } catch (e: Exception) {
            throw ArithmeticException()
        }
    }

}
