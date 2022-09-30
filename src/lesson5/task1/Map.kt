@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import ru.spbstu.wheels.defaultCopy
import kotlin.math.*

// Урок 5: ассоциативные массивы и множества
// Максимальное количество баллов = 14
// Рекомендуемое количество баллов = 9
// Вместе с предыдущими уроками = 33/47

/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая (2 балла)
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val res = mutableMapOf<Int, MutableList<String>>()
    for (i in grades) {
        if (i.value !in res.keys) res.put(i.value, mutableListOf())
        res.getValue(i.value).add(i.key)
    }
    return res
}

/**
 * Простая (2 балла)
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    if (a.isEmpty()) return true // if a is empty then a is submap of b
    for (i in a) {
        if (b.containsKey(i.key) && b[i.key] == i.value) return true
    }
    return false
}

/**
 * Простая (2 балла)
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for (j in b) {
        if (j.key in a && b[j.key] == a[j.key]) a.remove(j.key)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>) = a.intersect(b).toList()

/**
 * Средняя (3 балла)
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val res: MutableMap<String, String> = mutableMapOf()
    for (i in mapA) res.put(i.key, i.value)
    for (i in mapB) {
        if (i.key !in res) {
            res.put(i.key, i.value)
        } else {
            if (i.value != res[i.key]) res.put(i.key, res.getValue(i.key) + ", " + i.value)
        }
    }
    return res
}

/**
 * Средняя (4 балла)
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val res: MutableMap<String, MutableList<Double>> = mutableMapOf()
    for ((a, b) in stockPrices) {
        if (a !in res.keys) {
            res.put(a, mutableListOf(b, 1.0))
        } else {
            res.getValue(a)[0] += b
            res.getValue(a)[1] += 1.0
        }
    }
    val res1: MutableMap<String, Double> = mutableMapOf()
    for (i in res) {
        res1.put(i.key, i.value[0] / i.value[1])
    }
    return res1
}

/**
 * Средняя (4 балла)
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var name = ""
    var amount = 0.0
    var been = false
    for (i in stuff) {
        if (i.value.first == kind) {
            if (i.value.second < amount || been == false) {
                been = true
                amount = i.value.second
                name = i.key
            }
        }
    }
    if (been) return name
    return null
}

/**
 * Средняя (3 балла)
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    var mas = mutableSetOf<Char>()
    for (i in word) mas.add(i)
    mas = mas.map { it.lowercaseChar() }.toMutableSet()
    if (mas == chars.toSet() || mas.isEmpty()) return true
    return false
}

/**
 * Средняя (4 балла)
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val res: MutableMap<String, Int> = mutableMapOf()
    for (i in list) {
        if (i !in res.keys) {
            res.put(i, 0)
        }
        res.put(i, res.getValue(i) + 1)
    }
    val toDelete = mutableListOf<String>()
    for (j in res) if (j.value == 1) toDelete += j.key
    for (j in toDelete) res.remove(j)
    return res
}

/**
 * Средняя (3 балла)
 *
 * Для заданного списка слов определить, содержит ли он анаграммы.
 * Два слова здесь считаются анаграммами, если они имеют одинаковую длину
 * и одно можно составить из второго перестановкой его букв.
 * Скажем, тор и рот или роза и азор это анаграммы,
 * а поле и полено -- нет.
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun giveAmountOfLetters(word: String): Map<String, Int> {
    val res: MutableMap<String, Int> = mutableMapOf()
    for (i in word) {
        var counter = 0
        for (j in word) {
            if (j == i) counter++
        }
        if (!res.containsKey(word)) res.put(i.toString(), counter)
    }
    return res
}

fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0..words.size - 2) {
        val nowWord = giveAmountOfLetters(words[i])
        for (j in i + 1..words.size - 1) {
            if (words[j].length == words[i].length &&
                nowWord == giveAmountOfLetters(words[j])
            )
                return true
        }
    }
    return false
}

/**
 * Сложная (5 баллов)
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 *
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Оставлять пустой список знакомых для людей, которые их не имеют (см. EvilGnome ниже),
 * в том числе для случая, когда данного человека нет в ключах, но он есть в значениях
 * (см. GoodGnome ниже).
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta"),
 *       "Friend" to setOf("GoodGnome"),
 *       "EvilGnome" to setOf()
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat"),
 *          "Friend" to setOf("GoodGnome"),
 *          "EvilGnome" to setOf(),
 *          "GoodGnome" to setOf()
 *        )
 */
fun getAllFriends(name: String, friends: Map<String, Set<String>>): Set<String> {
    val res: MutableSet<String> = mutableSetOf()
    var now: MutableSet<String> = mutableSetOf()
    if (friends.containsKey(name)) now = friends.getValue(name).toMutableSet()
    while (true) {
        val new: MutableSet<String> = mutableSetOf()
        for (j in now) {
            if (j !in res) {
                if (j != name) res.add(j)
                if (friends.containsKey(j) == true) {
                    new += friends.getValue(j)
                }
            }
        }
        if (new.size == 0) break
        now = new
    }
    return res
}

fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    val res: MutableMap<String, Set<String>> = mutableMapOf()
    // Определение всех людей
    for (i in friends) {
        if (i.key !in res) res.put(i.key, mutableSetOf())
        for (j in i.value) {
            if (j !in res) res.put(j, mutableSetOf())
        }
    }
    // Определение друзей для каждого
    for (j in res.keys) {
        res.put(j, getAllFriends(j, friends))
    }
    return res
}

/**
 * Сложная (6 баллов)
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    // Будем хранить в мапе в качестве ключей 0..n/2 - слагаемые
    // в качестве значений - расположение первого слагаемого (0..n/2) и второго ( (n+1)//2 .. n-1)
    val bufer = mutableMapOf<Int, MutableList<Int>>()
    for (i in list.indices) {
        if (list[i] > number / 2) {
            if (number - list[i] !in bufer.keys) bufer.put(number - list[i], mutableListOf(-1, -1))
            bufer.getValue(number - list[i]).set(1, i)
        } else {
            if (list[i] !in bufer.keys) bufer.put(list[i], mutableListOf(-1, -1))
            // Обработка случая когда слагаемые равны
            if (bufer.getValue(list[i])[0] != -1 && list[i] + list[bufer.getValue(list[i])[0]] == number)
                return (minOf(bufer.getValue(list[i])[0], i) to maxOf(bufer.getValue(list[i])[0], i))
            else bufer.getValue(list[i]).set(0, i)
        }
    }
    for (i in bufer.values) if (i[0] >= 0 && i[1] >= 0) return (minOf(i[0], i[1]) to maxOf(i[0], i[1]))
    return (-1 to -1)
}

/**
 * Очень сложная (8 баллов)
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> = TODO()
/*{
    val all: MutableList<MutableSet<String>> = mutableListOf()
    val vals: MutableList<Int> = mutableListOf()
    val mas: MutableList<Int> = mutableListOf()
    for (i in 0..capacity) {
        all.add(mutableSetOf<String>()); mas.add(0); vals.add(0)
    }
    for (i in 0..capacity) {
        // Поиск самого выгодного единичного элемента
        for (j in treasures.keys) {
            val nowEl = treasures.getValue(j)
            if (nowEl.second > vals[i] && nowEl.first <= i) {
                vals[i] = nowEl.second
                mas[i] = nowEl.first
                all[i] = mutableSetOf(j)
            }
            if (nowEl.second == vals[i] && nowEl.first < mas[i]) {
                vals[i] = nowEl.second
                mas[i] = nowEl.first
                all[i] = mutableSetOf(j)
            }
        }
        // Основной алгоритм
        for (n in i downTo 1) {

            for (some in treasures.keys) {
                val nowEl = treasures.getValue(some)
                if (vals[n] + nowEl.second > vals[i] && mas[n] + nowEl.first <= i && some !in all[n]) {
                    mas[i] = mas[n] + nowEl.first
                    vals[i] = vals[n] + nowEl.second
                    all[i] = all[n]
                    all[i].add(some)
                }
                if (vals[n] + nowEl.second == vals[i] && mas[n] + nowEl.first < mas[i] && some !in all[n]) {
                    mas[i] = mas[n] + nowEl.first
                    vals[i] = vals[n] + nowEl.second
                    all[i] = all[n]
                    all[i].add(some)
                }
            }

        }

    }
    return all[capacity]
}*/
