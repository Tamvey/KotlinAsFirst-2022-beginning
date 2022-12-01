@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.IndexOutOfBoundsException
import java.lang.StringBuilder

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}
fun count(initial: String): Int {
    var res = 0
    for (i in initial) {
        if (i == '\n') res++
    }
    return res
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    if (!str.matches(Regex("""[0-9]+.[а-я]+.[0-9]+"""))) return ""
    val res = str.split(" ").toMutableList()
    val months = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )
    var ans = ""
    val res0ToInt = res[0].toInt()
    val res2ToInt = res[2].toInt()

    if (res[1] !in months || res0ToInt !in 1..31 || res.size > 3) {
        return ans
    }
    if (daysInMonth(months.indexOf(res[1]) + 1, res2ToInt) < res0ToInt) return ans
    ans += twoDigitStr(res[0].toInt()) + "."
    ans += twoDigitStr(months.indexOf(res[1]) + 1)
    ans += "." + res[2]

    return ans
}

/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    if (!digital.matches(Regex("""[0-9]+.[0-9]+.[0-9]+"""))) return ""
    var ans = ""
    val res = digital.split(".")
    val months = listOf(
        "января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря"
    )

    if (res[1].toInt() !in 1..12 || res[0].toInt() !in 1..31 || res.size > 3) {
        return ans
    }
    if (daysInMonth(res[1].toInt(), res[2].toInt()) < res[0].toInt()) return ans
    ans = "${res[0].toInt()} ${months[res[1].toInt() - 1]} ${res[2].toInt()}"
    return ans
}

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun countSymbols(n: String, c: Char): Int {
    var res = 0
    for (i in n) {
        if (i == c) res++
    }
    return res
}

fun flattenPhoneNumber(phone: String): String {
    var new = phone
    if (new.matches(Regex("""\+[0-9\(\) \-]+||[0-9\(\) \-]+""")) && !(new.contains(Regex("""\(\)""")))) {
        new = Regex("""[\(\)\- ]""").replace(new, "")
        return new
    } else return ""
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var bufer = jumps.toString()
    bufer = Regex("""[%-]""").replace(bufer, "")
    if (Regex("""[^0-9 ]""").find(bufer) != null) return -1
    var res = -1
    for (i in bufer.split(" ")) {
        if (i == "") continue
        if (res == 0) res = i.toInt()
        res = maxOf(i.toInt(), res)
    }
    return res
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    var ans = -1
    if (!jumps.matches(Regex("""[\+\-\%0-9 ]+"""))) return ans
    for (i in Regex("""[0-9]+ \+""").findAll(jumps, 0).map { it.value }.toList()) {
        ans = maxOf(ans, i.split(" ")[0].toInt())
    }
    return ans
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (Regex("""[0-9]+[ ]+[\+\-]+[ ]?""").replace("$expression +", "").length != 0) throw IllegalArgumentException()
    val res = expression.split(" ")
    var ans = res[0].toInt()
    for (i in 2..res.size - 1 step 2) {
        if (res[i - 1] == "+") ans += res[i].toInt()
        if (res[i - 1] == "-") ans -= res[i].toInt()
    }
    return ans
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val res = str.lowercase().split(" ")
    var ans = -1
    for (i in 0 until res.size - 1) {
        if (res[i] == res[i + 1]) {
            for (k in 0 until i) {
                ans += res[k].length
                ans++
            }
            ans += 1
            return ans
        }
    }
    return ans
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    val res = description.split(";")
    var nameOfMax = ""
    var valueOfMax = -1.0
    for (i in res) {
        val now = i.split(" ").filter { it != "" && it != " " }
        if (now.size < 2) return nameOfMax
        try {
            if (now[1].toDouble() > valueOfMax) {
                valueOfMax = now[1].toDouble()
                nameOfMax = now[0]
            }
        } catch (e: Exception) {
            return ""
        }
    }
    return nameOfMax
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int = TODO() /*{
    for (i in roman) if (i !in "IXVLCDM") return -1
    val ed = listOf("I", "II", "III", "IV", " ", "VI", "VII", "VIII", "IX").reversed()
    val des = listOf("X", "XX", "XXX", "XL", " ", "LX", "LXX", "LXXX", "XC").reversed()
    val sot = listOf("C", "CC", "CCC", "CD", " ", "DC", "DCC", "DCCC", "CM").reversed()
    var res = 0
    var copy = roman
    for (i in ed.indices) {
        if (ed[i] in copy) {
            res += (9 - i)
            copy = copy.replace(ed[i], "")
        }
    }
    if ('V' in copy) {
        res += 5; copy = copy.replace("V", "")
    }
    for (i in ed.indices) {
        if (des[i] in copy) {
            res += (9 - i) * 10
            copy = copy.replace(des[i], "")
        }
    }
    if ('L' in copy) {
        res += 50; copy = copy.replace("L", "")
    }
    for (i in ed.indices) {
        if (sot[i] in copy) {
            res += (9 - i) * 100
            copy = copy.replace(sot[i], "")
        }
    }
    if ('D' in copy) {
        res += 500; copy = copy.replace("D", "")
    }
    for (i in copy) {
        if (i == 'M') res += 1000
        else return -1
    }
    if (res == 0) return -1
    return res
}*/

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
// Find place of ]
fun getOut(st: String, now: Int): Int {
    var res = -1
    var stack = 0

    for (i in st.indices) {
        if (st[i] == '[') {
            stack += 1
            if (i == now) res = stack
        }
        if (st[i] == ']') {
            if (stack == res) return i
            stack -= 1
        }
    }
    return res
}

// Find place of [
fun getIn(st: String, now: Int): Int {
    var stack = 0
    var places = mutableListOf<Int>()
    for (i in st.indices) {
        if (st[i] == '[') {
            stack += 1
            places += i
        }
        if (st[i] == ']') {
            if (i == now) {
                return places[places.size - 1]
            }
            stack -= 1
            places.removeAt(places.size - 1)
        }
    }
    return -1
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val res = MutableList<Int>(cells) { 0 }
    // Check symbols
    val possibleSymbols = "><+-[] "
    for (j in commands) {
        if (j !in possibleSymbols) throw IllegalArgumentException()
    }
    // Check if '[' and ']' situated correctly
    val stack = mutableListOf<Char>()
    for (i in commands) {
        if (i == '[') stack += '['
        if (i == ']') {
            if (stack.size == 0) throw IllegalArgumentException()
            else stack -= '['
        }
    }
    if (stack.size != 0) throw IllegalArgumentException()
    // Solution
    var nowPlace = cells / 2
    var commandsHasBeenDone = 0
    var placeInCommands = 0
    while (true) {
        if (commandsHasBeenDone >= limit || placeInCommands >= commands.length) break

        if (commands[placeInCommands] == ' ') {
        } else if (commands[placeInCommands] == '-') {
            res[nowPlace] -= 1
        } else if (commands[placeInCommands] == '+') {
            res[nowPlace] += 1
        } else if (commands[placeInCommands] == '<') {
            nowPlace -= 1
            if (nowPlace >= cells || nowPlace < 0) throw IllegalStateException()
        } else if (commands[placeInCommands] == '>') {
            nowPlace += 1
            if (nowPlace >= cells || nowPlace < 0) throw IllegalStateException()
        } else if (commands[placeInCommands] == '[') {
            if (res[nowPlace] == 0) placeInCommands = getOut(commands, placeInCommands)
        } else if (commands[placeInCommands] == ']') {
            if (res[nowPlace] != 0) placeInCommands = getIn(commands, placeInCommands)
        }
        placeInCommands++
        commandsHasBeenDone++
    }
    return res
}
