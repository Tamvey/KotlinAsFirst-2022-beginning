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
    internal val elements = Array<Any?>(capacity) { UNINITIALIZED }

    /**
     * Число элементов в хеш-таблице
     */
    var size: Int = 0
        private set

    /**
     * Признак пустоты
     */
    fun isEmpty() = this.size == 0

    /**
     * Добавление элемента.
     * Вернуть true, если элемент был успешно добавлен,
     * или false, если такой элемент уже был в таблице, или превышена вместимость таблицы.
     */
    fun add(element: T): Boolean {
        if (this.size == this.capacity) return false
        val index = element.hashCode() % elements.size
        var counter = 0
        do {
            val newIndex = (index + counter) % elements.size
            if (elements[newIndex] == element) return false
            if (elements[newIndex] == UNINITIALIZED) {
                elements[newIndex] = element
                this.size += 1
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
        if (this.size == 0) return false
        val index = element.hashCode() % elements.size
        var counter = 0
        do {
            if (elements[(index + counter) % elements.size] == element) return true
            counter++
        } while (elements.size != counter)
        return false
    }

    override fun hashCode() = this.size.hashCode() +
            this.elements.map { i -> if (i != UNINITIALIZED) i.hashCode() else 0 }.sumOf { it }

    /**
     * Таблицы равны, если в них одинаковое количество элементов,
     * и любой элемент из второй таблицы входит также и в первую
     */


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != this.javaClass) return false
        other as OpenHashSet<Any?>
        if (this.size != other.size) return false
        for (i in this.elements) if (i != UNINITIALIZED && !other.contains(i)) return false
        return true
    }

    companion object {
        private val UNINITIALIZED = Any()
    }
}
