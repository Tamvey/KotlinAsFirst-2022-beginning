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
    var v: MutableList<MutableList<String>> = mutableListOf()
    var k: MutableList<Int> = mutableListOf()
    for (i in grades) {
        if (i.value !in k) {k.add(i.value); v.add(mutableListOf(i.key))}
        else {v[k.indexOf(i.value)].add(i.key)}
    }
    var res: MutableMap<Int, List<String>> = mutableMapOf()
    for (i in 0..k.size - 1) {
        res.put(k[i], v[i].toList())
    }
    return res.toMap()
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
    if (a.size == 0 && b.size == 0) return true
    for(i in a){
        for(j in b){
            if (i.value == j.value && i.key == j.key) return true
        }
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
    for(j in b) {
        if (a.containsKey(j.key) && b[j.key] == a[j.key]) a.remove(j.key)
    }
}

/**
 * Простая (2 балла)
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяющихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> {
    var res: MutableSet<String> = mutableSetOf()
    for (i in a) {
        if (b.contains(i)) res.add(i)
    }
    return res.toList()
}

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
    for (a in mapA) {
        for (b in mapB) {
            if (a.key == b.key) {
                if (a.value != b.value) {
                    res.put(a.key, "${a.value}, ${b.value}")
                }
                else res.put(a.key, a.value)
            }
            else {
                if (a.key !in res) res.put(a.key, a.value)
                if (b.key !in res) res.put(b.key, b.value)
            }
        }
        if (a.key !in res) res.put(a.key, a.value)
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
        }
        else {
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
    var amount = Double.MAX_VALUE
    var been = false
    for (i in stuff) {
        if (i.value.first == kind) {
            been = true
            if (i.value.second < amount) {
                amount = i.value.second
                name = i.key
            }
        }
    }
    if (been == true) return  name
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
    for (i in word) {
        if (i !in chars) return false
    }
    return true
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
    var res: MutableMap<String, Int> = mutableMapOf()
    var alph = "abcdefghijklmnopqrstuvwxyz1234567890абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    for (i in alph) {
        var counter = 0
        for (j in list) {
            if (i.toString() == j) counter++
        }
        if (counter > 1) res.put(i.toString(), counter)
    }
    var counter = 0
    for (j in list) {
        if (j == "") counter++
    }
    if (counter > 1) res.put("", counter)
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
    val alph = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
    var res: MutableMap<String, Int> = mutableMapOf()
    for (i in alph) {
        var counter = 0
        for (j in word) {
            if (j == i) counter++
        }
        res.put(i.toString(), counter)
    }
    return res
}
fun hasAnagrams(words: List<String>): Boolean {
    for (i in 0..words.size - 2) {
        for (j in i + 1..words.size - 1) {
            if (words[j].length == words[i].length &&
                giveAmountOfLetters(words[j]) == giveAmountOfLetters(words[i])
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
    if(friends.containsKey(name)) now = friends.getValue(name).toMutableSet()
    while (true) {
        var new: MutableSet<String> = mutableSetOf()
        for (j in now) {
            if (j !in res) {
                if (j != name) res.add(j)
                if (friends.containsKey(j) == true) {new += friends.getValue(j)}
            }
        }
        if (new.size == 0) break
        now = new
    }
    return res
}
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    var res: MutableMap<String, Set<String>> = mutableMapOf()
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
    var sortedList = list.sorted()
    var res: Pair<Int, Int> = Pair(-1, -1)
    for (i in 0 .. list.size - 1) {
        if (sortedList[i] + sortedList[list.size - 1] < number) break
        for (j in list.size - 1 downTo i + 1) {
            if (sortedList[i] + sortedList[j] < number) break
            if (sortedList[i] + sortedList[j] == number) {
                res = Pair( min(list.indexOf(sortedList[i]), list.indexOf(sortedList[j])),
                            max(list.indexOf(sortedList[i]), list.indexOf(sortedList[j])))
                return res
            }
        }
    }
    return res
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
