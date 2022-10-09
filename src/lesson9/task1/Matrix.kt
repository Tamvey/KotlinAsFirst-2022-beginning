@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson9.task1

import java.lang.IllegalArgumentException

// Урок 9: проектирование классов
// Максимальное количество баллов = 40 (без очень трудных задач = 15)

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /** Все элементы */
    var allCells: MutableList<MutablePair<Cell, E>>

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

data class MutablePair<E, T>(var first: E, var second: T)

/**
 * Простая (2 балла)
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> = MatrixImpl<E>(height, width, e)

/**
 * Средняя сложность (считается двумя задачами в 3 балла каждая)
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(val heightToSet: Int, val widthToSet: Int, val e: E) : Matrix<E> {
    override var allCells = mutableListOf<MutablePair<Cell, E>>()

    init {
        for (i in 0 until heightToSet) {
            for (j in 0 until widthToSet) {
                allCells.add(MutablePair(Cell(i, j), e))
            }
        }
    }

    override val width: Int = allCells[allCells.size - 1].first.column + 1

    override val height: Int = allCells[allCells.size - 1].first.row + 1

    override fun get(row: Int, column: Int): E {
        for (i in allCells) {
            if (i.first.row == row && i.first.column == column)
                return i.second
        }
        throw IllegalArgumentException()
    }

    override fun get(cell: Cell): E {
        for (i in allCells) {
            if (i.first == cell)
                return i.second
        }
        throw IllegalArgumentException()
    }

    override fun set(row: Int, column: Int, value: E) {
        for (i in allCells) {
            if (i.first.row == row && i.first.column == column) {
                i.second = value
                return
            }
        }
        throw IllegalArgumentException()
    }

    override fun set(cell: Cell, value: E) {
        for (i in allCells) {
            if (i.first == cell) {
                i.second = value
                return
            }
        }
        throw IllegalArgumentException()
    }

    // Взял реализацию из теории к уроку
    override fun equals(other: Any?) = other is MatrixImpl<*> && height == other.height && width == other.width

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

}

