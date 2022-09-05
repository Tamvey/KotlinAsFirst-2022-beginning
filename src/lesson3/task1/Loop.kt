@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlin.math.*

// Урок 3: циклы
// Максимальное количество баллов = 9
// Рекомендуемое количество баллов = 7
// Вместе с предыдущими уроками = 16/21

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
    when {
        n == m -> 1
        n < 10 -> 0
        else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
    }

/**
 * Простая (2 балла)
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    if(n == 0) return 1
    var amountOfNumbers = 0
    var counter: Long = 1
    while(n / counter != 0){
		amountOfNumbers++
		counter *= 10
    }
    return amountOfNumbers
}

/**
 * Простая (2 балла)
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    val allNumbers: Array<Int> = Array(n+1, {1})
    for (i in 3..n){
        allNumbers[i] = allNumbers[i - 1] + allNumbers[i - 2]
    }
    return allNumbers[n]
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var c: Int = 2
    while(n % c != 0){
        c++
    }
    return c
}

/**
 * Простая (2 балла)
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var c: Int = n - 1
    while(c != 0){
        if(n % c == 0) break
        c--
    }
    return c
}

/**
 * Простая (2 балла)
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
	var counter: Int = 0
    var x1 = x
    while(x1 != 1){
   		if(x1 % 2 == 0) x1 /= 2
        else x1 = 3 * x1 + 1
        counter++
    }
    return counter
}

/**
 * Средняя (3 балла)
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun totalMultiplier(x: Int, y: Int): Int{
	var res = 1
	var minn = min(x, y)
	var maxx = max(x, y)
    val to = floor(sqrt(minn*1.0))
	for(n in 1..to.toInt()){
		if(minn % n == 0 && maxx % n == 0) res *= n
        if (n == minn / n) break
		if( (maxx % (minn / n) == 0) && (minn % (minn / n) == 0) )  res *= (minn / n)
	}
	return res
}
fun lcm(m: Int, n: Int): Int{
	/* y - общий множитель
 	* m = x * y^i
 	* n = z * y^j
 	* k = x * z * y^max(i, j)
 	* */
    if(m == n) return m
	var res = 1	
    val inCommon = totalMultiplier(m, n)
    var m1 = m
    var n1 = n
   	while ((m1 % inCommon == 0 || n1 % inCommon == 0) && inCommon != 1){
    	if(m1 % inCommon == 0) {m1 /= inCommon}
        if(n1 % inCommon == 0) {n1 /= inCommon}
        res *= inCommon
    }
    //println(m1)
    //println(n1)
    //println(res)
    res *= m1 * n1
    return res
}

/**
 * Средняя (3 балла)
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    //println(totalMultiplier(m, n))
    return (totalMultiplier(m, n) == 1)
}

/**
 * Средняя (3 балла)
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun mypow(number: Int, times: Int) : Int{
    if(times == 0) return 1
    return number * mypow(number, times - 1)
}
fun getNumber(number: Int, place: Int) : Int{
    return number / mypow(10, place) % 10
}
fun getLength(number: Int) : Int{
	var length = 0
	while (number / mypow(10, length) != 0 && length < 10){length++}
	return length
}
fun revert(n: Int): Int {
    /*
     * 3 2 1 5 1
     * 
     * 4 3 2 1 0
     */
    var length = getLength(n)
    var res = 0
    for(i in 0..(length-1)/2){
        if(i == length - i - 1) res += mypow(10, i)*getNumber(n, i)
        else res += (mypow(10, length - i - 1)*getNumber(n, i) + mypow(10, i)*getNumber(n, length - i - 1))
    }
    return res
}

/**
 * Средняя (3 балла)
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean {
    var length = getLength(n)
    for(i in 0..(length-1)/2){
        if(getNumber(n, i) != getNumber(n, length-i-1)) return false
    }
    return true
}

/**
 * Средняя (3 балла)
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var length = getLength(n)
    var last = getNumber(n, 0)
    for(i in 0..length-1){
        if(getNumber(n, i) != last) return true
    }
    return false
}

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю.
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.sin и другие стандартные реализации функции синуса в этой задаче запрещается.
 */
fun sin(x: Double, eps: Double): Double = TODO()

/**
 * Средняя (4 балла)
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 * Подумайте, как добиться более быстрой сходимости ряда при больших значениях x.
 * Использовать kotlin.math.cos и другие стандартные реализации функции косинуса в этой задаче запрещается.
 */
fun cos(x: Double, eps: Double): Double = TODO()

/**
 * Сложная (4 балла)
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun squareSequenceDigit(n: Int): Int = TODO()

/**
 * Сложная (5 баллов)
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun fibSequenceDigit(n: Int): Int = TODO()
