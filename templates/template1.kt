import java.io.*
import java.util.*

class Solution : Runnable {
    override fun run() {
        solve()
    }

    fun start() {
        Thread(null, Solution(), "whatever", (1 shl 26).toLong()).start()
    }
}

fun main(args: Array<String>) {
    Solution().start()
}


class IO{
    companion object {

        private val reader = InputReader(System.`in`)
        private val writer = OutputWriter(System.out)

        private fun readMultipleInts(count : Int) : List<Int>{
            val map = mutableListOf<Int>()
            repeat(count){
                map.add(reader.readInt())
            }
            return map
        }
        fun readInt() = reader.readInt()
        fun readTwoInts() = readMultipleInts(2)
        fun readThreeInts() = readMultipleInts(3)
        fun readFourInts() = readMultipleInts(4)
        fun readString() = reader.readString()
        fun readGraph(n: Int): MutableMap<Int, MutableSet<Int>> {
            val graph = mutableMapOf<Int, MutableSet<Int>>()
            repeat(n - 1) {
                val u = reader.readInt()
                val v = reader.readInt()
                if (!graph.containsKey(u)) graph[u] = mutableSetOf()
                if (!graph.containsKey(v)) graph[v] = mutableSetOf()
                graph[u]!!.add(v)
                graph[v]!!.add(u)
            }
            return graph
        }

        fun write(obj : Any){
            writer.printLine(obj)
        }
        
        fun flushOutput(){
            writer.flush()
        }
        fun closeOutput(){
            writer.close()
        }
    }
}


class MATH{
    companion object {

        val mod = 1000000007

        fun gcd(a : Int, b : Int) : Int{
            if (b == 0)
                return a
            return gcd(b, a % b)
        }

        fun gcd(a : Long, b : Long) : Long{
            if (b == 0L)
                return a
            return gcd(b, a % b)
        }

        fun inverseMod(a : Int) : Int{
            return powMod(a, mod - 2)
        }

        fun powMod(a : Int, b : Int) : Int{
            //calculate a to the power b mod m
            if(b == 0) return 1
            return if(b%2 == 1){
                prodMod(a, powMod(a, b-1))
            }else{
                val p = powMod(a, b/2)
                prodMod(p, p)
            }
        }

        fun prodMod(val1 : Int, val2 : Int) : Int{
            return ((val1.toLong()*val2)%mod).toInt()
        }

    }
}


fun solve() {

}

class InputReader(private val stream: InputStream) {
    private val buf = ByteArray(1024)
    private var curChar: Int = 0
    private var numChars: Int = 0
    private val filter: SpaceCharFilter? = null

    fun read(): Int {
        if (numChars == -1)
            throw InputMismatchException()
        if (curChar >= numChars) {
            curChar = 0
            try {
                numChars = stream.read(buf)
            } catch (e: IOException) {
                throw InputMismatchException()
            }

            if (numChars <= 0)
                return -1
        }
        return buf[curChar++].toInt()
    }

    fun readInt(): Int {
        var c = read()
        while (isSpaceChar(c))
            c = read()
        var sgn = 1
        if (c == '-'.toInt()) {
            sgn = -1
            c = read()
        }
        var res = 0
        do {
            if (c < '0'.toInt() || c > '9'.toInt())
                throw InputMismatchException()
            res *= 10
            res += c - '0'.toInt()
            c = read()
        } while (!isSpaceChar(c))
        return res * sgn
    }

    fun readString(): String {
        var c = read()
        while (isSpaceChar(c))
            c = read()
        val res = StringBuilder()
        do {
            res.appendCodePoint(c)
            c = read()
        } while (!isSpaceChar(c))
        return res.toString()
    }

    fun isSpaceChar(c: Int): Boolean {
        return filter?.isSpaceChar(c)
            ?: (c == ' '.toInt() || c == '\n'.toInt() || c == '\r'.toInt() || c == '\t'.toInt() || c == -1)
    }

    operator fun next(): String {
        return readString()
    }

    interface SpaceCharFilter {
        fun isSpaceChar(ch: Int): Boolean
    }
}

class OutputWriter {
    private val writer: PrintWriter

    constructor(outputStream: OutputStream) {
        writer = PrintWriter(BufferedWriter(OutputStreamWriter(outputStream)))
    }

    constructor(writer: Writer) {
        this.writer = PrintWriter(writer)
    }

    fun print(vararg objects: Any) {
        for (i in objects.indices) {
            if (i != 0)
                writer.print(' ')
            writer.print(objects[i])
        }
    }

    fun printLine(vararg objects: Any) {
        print(*objects)
        writer.println()
    }

    fun close() {
        writer.close()
    }

    fun flush() {
        writer.flush()
    }

}
