@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = File(outputName).bufferedWriter()
    var currentLineLength = 0
    fun append(word: String) {
        if (currentLineLength > 0) {
            if (word.length + currentLineLength >= lineLength) {
                writer.newLine()
                currentLineLength = 0
            } else {
                writer.write(" ")
                currentLineLength++
            }
        }
        writer.write(word)
        currentLineLength += word.length
    }
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            writer.newLine()
            if (currentLineLength > 0) {
                writer.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(Regex("\\s+"))) {
            append(word)
        }
    }
    writer.close()
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */

fun deleteMarked(inputName: String, outputName: String) {
    val ifile = File(inputName).readLines()
    File(outputName).bufferedWriter().use { ofile ->
        for (i in ifile) {
            if (!i.isEmpty()) {
                if (i[0] != '_') ofile.write(i + "\n")
            } else ofile.newLine()
        }
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countWords(text: String, word: String): Int {
    var res = 0
    var fromm = 0
    while (text.indexOf(word, fromm) != -1) {
        fromm = text.indexOf(word, fromm) + 1
        res += 1
    }
    return res
}

fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val res = mutableMapOf<String, Int>()
    val file = File(inputName).readText().lowercase()
    for (word in substrings.toSet()) {
        res[word] = countWords(file, word.lowercase())
    }
    return res
}

/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val letters1 = setOf('Ж', 'Ч', 'Ш', 'Щ', 'ж', 'ч', 'ш', 'щ')
    val letters2 = setOf('Ы', 'Я', 'Ю', 'ы', 'я', 'ю')
    val toChange = mapOf('Ы' to 'И', 'Я' to 'А', 'Ю' to 'У', 'ы' to 'и', 'я' to 'а', 'ю' to 'у')
    val ifile = File(inputName).readText()
    File(outputName).bufferedWriter().use { ofile ->
        if (ifile.isNotEmpty()) ofile.write(ifile[0].toString())
        for (i in 1 until minOf(ifile.length, ifile.length)) {
            if (ifile[i - 1] in letters1 && ifile[i] in letters2) {
                ofile.write(toChange.getValue(ifile[i]).toString())
            } else ofile.write(ifile[i].toString())
        }
    }
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use { ofile ->
        val ifile = File(inputName).readLines().map { it.trim() }
        var maxLength = 0
        for (i in ifile) {
            if (i.length > maxLength) maxLength = i.length
        }
        for (i in ifile) {
            for (j in 0 until (maxLength - i.length) / 2) ofile.write(" ")
            ofile.write(i)
            ofile.newLine()
        }
    }
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun divideSpaces(all: Int, am: Int): MutableList<Int> {
    val res: MutableList<Int> = mutableListOf()
    if (all % am == 0) {
        for (i in 1..am) res.add(all / am)
    } else {
        var left = all % am
        for (i in 1..am) {
            if (left != 0) {
                res.add(1 + all / am)
                left--
            } else res.add(all / am)
        }
    }
    return res
}


fun alignFileByWidth(inputName: String, outputName: String) {
    val ifile = File(inputName).readLines()
    File(outputName).bufferedWriter().use { ofile ->
        var maxLength = 0
        // Find the longest line with only one space between words
        for (i in ifile) {
            var length = 0
            var times = 0
            for (j in i.split(" ")) {
                length += j.length
                if (j.isNotEmpty()) times++
            }
            if (times > 0) times--
            length += times
            maxLength = maxOf(maxLength, length)
        }
        // Processing all lines
        for (i in ifile) {
            val now = i.trim()
            if (now.isBlank()) {
                ofile.newLine(); continue
            }

            var lettersForWords = 0
            var words = 0
            for (j in now.split(" ")) {
                lettersForWords += j.length
                if (j.isNotEmpty()) words++
            }
            val spaces = maxLength - lettersForWords
            var mas: List<Int> = listOf(0)
            if (words > 1) mas = divideSpaces(spaces, words - 1)
            val buffer = i.split(" ").filter { it -> it != "" }
            for (i in 0 until buffer.size) {
                if (i < buffer.size - 1) ofile.write(buffer[i] + " ".repeat(mas[i]))
                else ofile.write(buffer[i])
            }
            ofile.newLine()
        }
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
// Delete symbols except of letters

fun top20Words(inputName: String): Map<String, Int> {
    val ifile = File(inputName).readLines()
    val res: MutableMap<String, Int> = mutableMapOf()
    // Counting amount of each word -> res
    for (i in ifile) {
        val now = Regex("""\s+""").split(
            Regex("""[^а-яa-zё]""")
                .replace(i.lowercase(), " ")
        ).filter { it.isNotBlank() }
        for (word in now) {
            if (!res.containsKey(word)) {
                res.put(word, 1)
            } else res[word] = res.getValue(word) + 1
        }
    }
    // Sorting, reversion res and choosing 20 or more words
    val listOfPairs = res.toList().sortedBy { it -> it.second }.reversed()
    val res1: MutableMap<String, Int> = mutableMapOf()
    for (i in listOfPairs.indices) {
        if (i <= 19 || (i > 19 && listOfPairs[i].second == listOfPairs[i - 1].second)) res1.put(
            listOfPairs[i].first, listOfPairs[i].second
        )
        else break
    }
    return res1
}

/**
 * Средняя (14 баллов)
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
} /*{
    var ifile = File(inputName).readLines()
    var ofile = File(outputName).bufferedWriter().use { ofile ->
        for (j in ifile) {
            var st = j.lowercase()
            for (i in dictionary) {
                st = st.replace(i.key.toString().lowercase(), i.value.lowercase())
            }
            if (j[0].toString() == j[0].toString().uppercase())
                st = st[0].toString().uppercase() + st.substring(1, st.length)
            ofile.write(st)
            ofile.newLine()
        }
    }
}*/

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    TODO()
} /*{
    var bufer: MutableMap<Int, MutableList<String>> = mutableMapOf()
    val text = File(inputName).readLines()
    val ofile = File(outputName).bufferedWriter()
    var maxx = -1
    var resList: MutableList<String> = mutableListOf()
    for (i in text) {
        for (word in i.split(" ")) {
            if (word == "") continue
            // Set of letters to define amount of unique ones
            val setOfLetters: MutableSet<String> = mutableSetOf()
            for (j in word) if (j.toString().lowercase() !in setOfLetters) setOfLetters.add(j.toString().lowercase())
            // Processing maxx, resList
            if (setOfLetters.size > maxx) {
                maxx = setOfLetters.size
                resList = mutableListOf(word)
            } else if (setOfLetters.size == maxx) resList += word
        }
    }
    // Writing in file
    for (i in resList.indices) {
        if (i != resList.size - 1) ofile.write(resList[i] + ", ")
        else ofile.write(resList[i])
    }
    ofile.close()
}*/

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */


fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()/*
    var ifile = File(inputName).readLines()
    var ofile = File(outputName).bufferedWriter()
    ofile.write("<html><body><p>")
    var open = true
    val stack = Stack<String>()
    for (l in ifile.indices) {
        if (ifile[l].isEmpty()) {
            if (open) {
                ofile.write("</p>")
                open = false
            }
            if(!open) {
                ofile.write("<p>")
                open == true
            }
            ofile.write("\n")
            continue
        }
        var j = 0
        while (j < ifile[l].length) {
            if (ifile[l][j] == '*' && ifile[l].length > (j + 1) && ifile[l][j + 1] == '*') {
                if (!stack.isEmpty() && stack.peek() == "<b>") {
                    ofile.write("</b>")
                    stack.pop()
                } else {
                    stack.push("<b>")
                    ofile.write("<b>")
                }
                j += 1
            } else if (ifile[l][j] == '*') {
                if (!stack.isEmpty() && stack.peek() == "<i>") {
                    ofile.write("</i>")
                    stack.pop()
                } else {
                    stack.push("<i>")
                    ofile.write("<i>")
                }
            } else if (ifile[l][j] == '~' && ifile[l].length > (j + 1) && ifile[l][j + 1] == '~') {
                if (!stack.isEmpty() && stack.peek() == "<s>") {
                    ofile.write("</s>")
                    stack.pop()
                } else {
                    stack.push("<s>")
                    ofile.write("<s>")
                }
                j += 1
            } else {
                ofile.write(ifile[l][j].toString())
            }
            j++
        }
        ofile.write("\n")
    }
    ofile.write("</p></body></html>")
    ofile.close()*/
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */

fun writeLeft(str: String, from: Int) = str.filterIndexed { index, c -> index > from }
enum class Tag(val str: String, val lstr: String) {
    ul("<ul>", "</ul>"), ol("<ol>", "</ol>"), li("<li>", "</li>")
}

fun markdownToHtmlLists(inputName: String, outputName: String) {
    val stack = ArrayDeque<Tag>()
    var spaces = -4
    val ifile = File(inputName).readLines()
    File(outputName).bufferedWriter().use { ofile ->
        ofile.write("<html><body><p>")
        for (line in ifile) {
            // Counter of index
            var j = 0
            // Counter of spaces
            var nowSpaces = 0
            // Counting spaces
            while (line[j] == ' ') {
                nowSpaces++
                j++
            }
            if (line[j] != '*') j = line.indexOf('.')
            //
            if (nowSpaces - spaces == -4) {
                for (k in 0..1) {
                    val returned = stack.removeLast()
                    when (returned) {
                        Tag.ol -> ofile.write(Tag.ol.lstr)
                        Tag.ul -> ofile.write(Tag.ul.lstr)
                        else -> ofile.write(Tag.li.lstr)
                    }
                }
            }
            if (nowSpaces - spaces == 4) {
                if (line[j] == '.') {
                    ofile.write(Tag.ol.str)
                    ofile.write(Tag.li.str)
                    stack.addLast(Tag.ol)
                    stack.addLast(Tag.li)
                } else {
                    ofile.write(Tag.ul.str)
                    ofile.write(Tag.li.str)
                    stack.addLast(Tag.ul)
                    stack.addLast(Tag.li)
                }
            } else {
                ofile.write(Tag.li.lstr)
                ofile.write(Tag.li.str)
            }
            ofile.write(writeLeft(line, j))
            ofile.write("\n")
            spaces = nowSpaces
        }
        for (i in stack.reversed()) when {
            i == Tag.ol -> ofile.write(Tag.ol.lstr)
            i == Tag.ul -> ofile.write(Tag.ul.lstr)
            else -> ofile.write(Tag.li.lstr)
        }
        ofile.write("</p></body></html>")
    }
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
/*fun processLine(line: String): String {
    var res = StringBuilder()
    var j = 0
    val stack = Stack<String>()
    while (j < line.length) {
        try {
            if (line[j] == '*' && line[j + 1] == '*') {
                if (!stack.isEmpty() && stack.peek() == "<b>") {
                    res.append("</b>")
                    stack.pop()
                } else {
                    stack.push("<b>")
                    res.append("<b>")
                }
                j += 1
            } else if (line[j] == '*') {
                if (!stack.isEmpty() && stack.peek() == "<i>") {
                    res.append("</i>")
                    stack.pop()
                } else {
                    stack.push("<i>")
                    res.append("<i>")
                }
            } else if (line[j] == '~' && line[j + 1] == '~') {
                if (!stack.isEmpty() && stack.peek() == "<s>") {
                    res.append("</s>")
                    stack.pop()
                } else {
                    stack.push("<s>")
                    res.append("<s>")
                }
                j += 1
            } else {
                res.append(line[j].toString())
            }
            j += 1
        } catch (e: Exception) {
            res.append(line[j].toString())
            j += 1
        }
    }
    return res.toString()
}*/

fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

