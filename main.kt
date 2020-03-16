package search
 
import java.io.File
import java.util.*
 
val scanner = Scanner(System.`in`)
 
fun main(args: Array<String>) {
    val manageSearchEngine = ManageSearchEngine()
    manageSearchEngine.start(args[1])
}
class ManageSearchEngine {
    val searchEngine = SearchEngine()
 
    fun start(file: String) {
        var go = true
        searchEngine.dataSet.loadData(file)
        while(go){
            println()
            menu()
            when (optionMenu()) {
                1 -> searchEngine.searchData()
                2 -> searchEngine.allData()
                0 -> {
                    print("Bye!")
                    go = false
                }
            }
        }
    }
 
    private fun menu(){
        println("=== Menu ===\n" +
                "1. Find a person\n" +
                "2. Print all people\n" +
                "0. Exit")
    }
 
    private fun optionMenu(): Int {
        var n = 0
        var test = true
        while (test) {
            n = scanner.nextInt()
            scanner.nextLine()
            if (n !in 0..2) {
                println("\nIncorrect option! Try again.\n")
                menu()
            } else {
                println()
                test = false
            }
        }
        return n
    }
}
 
class SearchEngine {
    val dataSet = DataSet()
    fun searchData(){
        println("Select a matching strategy: ALL, ANY, NONE")
        val opt = scanner.nextLine()
        println()
        when (opt){
            "ALL" -> ALL()
            "ANY" -> ANY()
            "NONE" -> NONE()
        }
        /*println("Enter a name or email to search all suitable people.")
        val word = scanner.nextLine()
        if(dataSet.mapData.keys.contains(word)) {
            println("${dataSet.mapData[word]?.size} persons found:")
            dataSet.mapData[word]?.forEach { index -> println(dataSet.listOfData[index])}
        } else {
            println("No matching people found.")
        }
        println()*/
    }
 
    private fun ALL() {
        var check = true
        val wordsToCheck = enter().split(" ")
        for(line in dataSet.listOfData){
            for (word in wordsToCheck)
                if (word.toLowerCase() !in line.toLowerCase())
                    check = false
            if (check)
                println(line)
            check = true
        }
    }
    private fun ANY() {
        var check = false
        val wordsToCheck = enter().split(" ")
        for(line in dataSet.listOfData){
            for (word in wordsToCheck)
                if (word.toLowerCase() in line.toLowerCase()){
                    check = true
                    break
                }
            if (check)
                println(line)
            check = false
        }
    }
 
    private fun NONE() {
        var check = false
        val wordsToCheck = enter().split(" ")
        for(line in dataSet.listOfData){
            for (word in wordsToCheck)
                if (word.toLowerCase() in line.toLowerCase()){
                    check = true
                }
            if (!check)
                println(line)
            check = false
        }
    }
 
    private fun enter(): String{
        println("Enter a name or email to search all suitable people.")
        return scanner.nextLine()
    }
    fun allData() {
        println("=== List of people ===")
        dataSet.listOfData.forEach { println(it) }
    }
}
 
class DataSet{
    val listOfData = mutableListOf<String>()
    val listOfWords = mutableListOf<String>()
    val mapData = mutableMapOf<String, MutableList<Int>>()
 
    fun loadData(src: String){
        val file = File(src)
        file.readLines().forEach { listOfData.add(it) }
        for (line in listOfData)
            for (word in line.split(" "))
                if ( !listOfWords.contains(word))
                    listOfWords.add(word)
 
        listOfWords.forEach {
            val listInt = mutableListOf<Int>()
            for (index in listOfData.indices){
                if (listOfData[index].contains(it))
                    listInt.add(index)
            }
            mapData[it] = listInt
        }
    }
}
