@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import kotlin.math.*
import lesson3.task1.getNumber
import java.lang.StringBuilder

// Урок 4: списки
// Максимальное количество баллов = 12
// Рекомендуемое количество баллов = 8
// Вместе с предыдущими уроками = 24/33

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.lowercase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая (2 балла)
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>) = sqrt(v.sumOf { it * it })

/**
 * Простая (2 балла)
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>) = when {
    (list.isNotEmpty()) -> list.sumOf { it } / list.size
    else -> 0.0
}

/**
 * Средняя (3 балла)
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    val meann = mean(list)
    for (i in list.indices) list[i] -= meann
    return list
}

/**
 * Средняя (3 балла)
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    if (a.size * b.size == 0) return 0
    var res = 0
    for (i in 0..a.size - 1) res += a[i] * b[i]
    return res
}

/**
 * Средняя (3 балла)
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun raiseToSomeDegree(number: Int, times: Int): Int {
    var res = 1
    for (i in 0..times - 1) {
        res *= number
    }
    return res
}


fun polynom(p: List<Int>, x: Int) = p.mapIndexed { index, vall -> raiseToSomeDegree(x, index) * vall }.sumOf { it }

/**
 * Средняя (3 балла)
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    var summ = 0
    for (i in list.indices) {
        summ += list[i]
        list[i] = summ
    }
    return list
}

/**
 * Средняя (3 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    val mas = mutableListOf<Int>()
    var n1 = n
    for (i in 2..n - 1) {
        while (n1 % i == 0) {
            mas += i
            n1 /= i
        }
    }
    if (mas.isEmpty()) mas += n
    return mas
}

/**
 * Сложная (4 балла)
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String {
    val mas = factorize(n)
    val st = buildString {
        for (i in 0 until mas.size) {
            append("${mas[i]}")
            if (i != mas.size - 1) append('*')
        }
    }
    return st
}

/**
 * Средняя (3 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    if (n == 0) return mutableListOf(0)
    val res = mutableListOf<Int>()
    var n1 = n
    while (n1 != 0) {
        res += n1 % base
        n1 /= base
    }
    return res.reversed()
}

/**
 * Сложная (4 балла)
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    val alph = "abcdefghijklmnopqrstuvwxyz"
    val converted = convert(n, base)
    val res = buildString {
        for (i in converted) {
            if (i > 9) append("${alph[i % base + 10 * (i / base - 1)]}")
            else append("$i")
        }
    }
    return res
}

/**
 * Средняя (3 балла)
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var res = 0
    val dig = digits.reversed()
    for (i in dig.indices) {
        res += raiseToSomeDegree(base, i) * dig[i]
    }
    return res
}

/**
 * Сложная (4 балла)
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    var res = 0
    val st = str.reversed()
    val alph = "abcdefghijklmnopqrstuvwxyz"
    for (i in 0..st.length - 1) {
        if (st[i] in alph) {
            res += (alph.indexOf(st[i]) + 10) * raiseToSomeDegree(base, i)
        } else {
            res += st[i].digitToInt() * raiseToSomeDegree(base, i)
        }
    }
    return res
}

/**
 * Сложная (5 баллов)
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */

fun roman(n: Int): String {
    val one = "IXCM"
    val n1 = n.toString().reversed()
    var now = 0
    val ed = listOf("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX")
    val des = listOf("X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC")
    val sot = listOf("C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM")
    val raz = listOf(ed, des, sot)
    var res = StringBuilder()
    for (i in n1) {
        val number = i.digitToInt()
        if (number == 0) {
            now++
            continue
        } else if (now >= 3) res.insert(0, one[3].toString().repeat(raiseToSomeDegree(10, now - 3) * number).toString())
        else if (number == 1) res.insert(0, one[now])
        else res.insert(0, raz[now][number - 1])
        now++
    }
    return res.toString()
}

/**
 * Очень сложная (7 баллов)
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */


fun firstThree(n: Int): String {
    var res = ""
    val ed = listOf("ноль", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val special = listOf(
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    )
    val des =
        listOf("двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val sot = listOf("сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
    if (n % 100 in 10..19) {
        if (n / 100 != 0) res = "${sot[n / 100 - 1]} "
        res += special[n % 10]
    } else {
        if (n % 10 != 0) res += ed[n % 10]
        if (n / 10 != 0 && n / 10 % 10 != 0) res = "${des[n / 10 % 10 - 2]} $res"
        if (n / 100 != 0) res = "${sot[n / 100 - 1]} $res"
    }
    return res
}

fun secondThree(n: Int): String {
    var res = ""
    val ed = listOf("одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val special = listOf(
        "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
        "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"
    )
    val des =
        listOf("двадцать", "тридцать", "сорок", "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val sot = listOf("сто", "двести", "триста", "четыреста", "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
    if (n % 100 in 10..19) {
        if (n / 100 != 0) res = "${sot[n / 100 - 1]} "
        res += special[n % 10]
        res += " тысяч"
    } else {
        if (n % 10 != 0) res += ed[n % 10 - 1]
        if (n / 10 % 10 != 0) res = "${des[n / 10 % 10 - 2]} $res"
        if (n / 100 != 0) res = "${sot[n / 100 - 1]} $res"

        if (n % 10 in 2..4) res += " тысячи"
        if (n % 10 == 1) res += " тысяча"
        if (n % 10 > 4 || n % 10 == 0) res += " тысяч"
    }
    return res
}

fun russian(n: Int): String {
    val f = secondThree(n / 1000)
    val s = firstThree(n % 1000)
    var res = ""
    if (n / 1000 == 0) res = s
    else res = f + ' ' + s
    res = res.replace("  ", " ")
    res = res.trim()
    return res
}