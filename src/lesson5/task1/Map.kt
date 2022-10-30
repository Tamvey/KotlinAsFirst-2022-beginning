@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1


import kotlin.math.*
import lesson9.task1.MutablePair

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
    for (i in a) {
        if (b[i.key] != i.value) return false
    }
    return true
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
        if (a[j.key] == j.value) a.remove(j.key)
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
    val res = mapA.toMutableMap()
    for (i in mapB) {
        if (i.key !in res) {
            res[i.key] = i.value
        } else {
            if (i.value != res[i.key]) res[i.key] = res[i.key] + ", " + i.value
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
    val res = mutableMapOf<String, MutableList<Double>>()
    for ((a, b) in stockPrices) {
        if (a !in res.keys) {
            res.put(a, mutableListOf(b, 1.0))
        } else {
            res.getValue(a)[0] += b
            res.getValue(a)[1] += 1.0
        }
    }
    val res1 = mutableMapOf<String, Double>()
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
            if (i.value.second < amount || !been) {
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
    // Множество букв слова в нижнем регистре
    val mas = word.map { it.lowercaseChar() }.toMutableSet()
    // Множество данных букв
    // Из них можно составить слово если mas2 содержит mas
    val mas2 = chars.map { it.lowercaseChar() }.toMutableSet()
    return mas2.containsAll(mas)
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
            res[i] = 0
        }
        res[i] = res.getValue(i) + 1
    }
    return res.filterValues { it > 1 }
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

fun hasAnagrams(words: List<String>): Boolean {
    // Можно сортировать строки и кидать их в сет, если размер сет уменьшится, тоесть хотя бы одна анаграмма есть
    val new = mutableSetOf<String>()
    for (i in words) new.add(i.toCharArray().sorted().joinToString(""))
    return (words.size - new.size != 0)
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
        if (new.isEmpty()) break
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
    val bufer = mutableMapOf<Int, Int>()
    for (i in list.indices) {
        if (bufer.containsKey(number - list[i])) {
            return (bufer.getValue(number - list[i]) to i)
        } else {
            bufer.put(list[i], i)
        }
    }
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
