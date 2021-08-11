package nicestring

val doesntContain: (String) -> Boolean = {
    val b = !it.contains("bu") && !it.contains("ba") && !it.contains("be")
    println("b $b")
    b
}

val threeVowels: (String) -> Boolean = {
    var counter = 0
    val vowels = "aeiou"//'a', 'e', 'i', 'o', 'u'
    val charArray = it.toCharArray()
    for (char in charArray) {
        if (vowels.contains(char)) {
            counter++
        }
    }
    println("threeVowels $counter")
    counter >= 3
}

val doubleLetter: (String) -> Boolean = {
    var counter = 0
    val charArray = it.toCharArray()
    for (char in charArray) {
        if (it.contains(char + "" + char)) {
            counter++
            break
        }
    }
    println("doubleLetter $counter")
    counter == 1
}

fun String.isNice(): Boolean {
    val doesntContain = doesntContain(this)
    val threeVowels = threeVowels(this)
    val doubleLetter = doubleLetter(this)
    return doesntContain && threeVowels ||
            doesntContain && doubleLetter ||
            threeVowels && doubleLetter
}