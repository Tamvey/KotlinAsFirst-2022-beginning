@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)
data class MutablePair<E, T>(var first: E, var second: T)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int


    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}


/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> =
    if (height > 0 && width > 0) MatrixImpl<E>(height, width, e) else throw IllegalArgumentException()

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int, e: E) : Matrix<E> {
    var allCells = arrayListOf<MutablePair<Cell, E>>()

    init {
        for (i in 0 until height) {
            for (j in 0 until width) {
                allCells.add(MutablePair(Cell(i, j), e))
            }
        }
    }


    override fun get(row: Int, column: Int): E {
        if (row in 0 until height && column in 0 until width) return allCells[width * row + column].second;
        throw IllegalArgumentException()
    }

    override fun get(cell: Cell) = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        if (row in 0 until height && column in 0 until width) {
            allCells[row * width + column].second = value;
            return;
        }
        throw IllegalArgumentException()
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)

    override fun equals(other: Any?): Boolean {
        if (other === this) return true;
        if (other?.javaClass != this.javaClass) return false
        other as MatrixImpl<Any?>
        return this.allCells == other.allCells
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("[")
        for (row in 0..height - 1) {
            sb.append("[")
            for (column in 0..width - 1) {
                sb.append(this[row, column])
            }
            sb.append("]")
        }
        sb.append("]")
        return "$sb"
    }

    override fun hashCode() = allCells.hashCode()
}

