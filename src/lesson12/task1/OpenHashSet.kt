@file:Suppress("UNUSED_PARAMETER")

package lesson12.task1

/**
 * Класс "хеш-таблица с открытой адресацией"
 *
 * Общая сложность задания -- сложная, общая ценность в баллах -- 20.
 * Объект класса хранит данные типа T в виде хеш-таблицы.
 * Хеш-таблица не может содержать равные по equals элементы.
 * Подробности по организации см. статью википедии "Хеш-таблица", раздел "Открытая адресация".
 * Методы: добавление элемента, проверка вхождения элемента, сравнение двух таблиц на равенство.
 * В этом задании не разрешается использовать библиотечные классы HashSet, HashMap и им подобные,
 * а также любые функции, создающие множества (mutableSetOf и пр.).
 *
 * В конструктор хеш-таблицы передаётся её вместимость (максимальное количество элементов)
 */
class OpenHashSet<T>(val capacity: Int) {

    /**
     * Массив для хранения элементов хеш-таблицы
     */
    internal val elements = Array<Any?>(capacity) { Any() }
    private var amount = 0

    /**
     * Число элементов в хеш-таблице
     */
    val size: Int get() = this.amount

    /**
     * Признак пустоты
     */
    fun isEmpty() = this.amount == 0

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (this.amount == this.capacity) return false
        val index = element.hashCode() % elements.size
        var counter = 0
        do {
            val newIndex = (index + counter) % elements.size
            if (elements[newIndex] == element) return false
            if (elements[newIndex]?.javaClass == Any().javaClass) {
                elements[newIndex] = element
                this.amount += 1
                return true
            }
            counter++
        } while (elements.size != counter)
        return false
    }

    /**
     * Проверка, входит ли заданный элемент в хеш-таблицу
     */
    operator fun contains(element: T): Boolean {
        if (this.amount == 0) return false
        val index = element.hashCode() % elements.size
        var counter = 0
        do {
            if (elements[(index + counter) % elements.size] == element) return true
            counter++
        } while (elements.size != counter)
        return false
    }

    override fun hashCode() = this.size.hashCode() +
            this.elements.map { i -> if (i?.javaClass != Any().javaClass) i.hashCode() else 0 }.sumOf { it }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != this.javaClass) return false
        if (this.hashCode() != other.hashCode()) return false
        other as OpenHashSet<Any?>
        for (i in this.elements) if (i?.javaClass != Any().javaClass) if (!other.contains(i)) return false
        return true
    }
}
