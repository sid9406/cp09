class DFS(val n  : Int, val root : Int, val graph : MutableMap<Int, MutableList<Pair<Int, Int>>>){

    val sum = LongArray(n + 1){ 0L }
    val levelArray  = IntArray(n + 1) { 0 }
    val up = Array(n + 1) { IntArray(19) { -1 }}

    init {
        dfs(root, -1)
        buildUp()
    }

    fun dfs(root : Int, parent : Int){
        graph[root]!!.forEach {
            if (it.first != parent){
                sum[it.first] = it.second + sum[root]
                levelArray[it.first] = levelArray[root] + 1
                up[it.first][0] = root
                dfs(it.first, root)
            }
        }
    }


    fun buildUp() {
        for (i in 1 until 19) {
            for (j in 1 until n + 1) {
                if (up[j][i - 1] != -1)
                    up[j][i] = up[up[j][i - 1]][i - 1]
            }
        }
    }


    fun lca(u: Int, v: Int): Int {
        var u = u
        var v = v
        if (levelArray[v] < levelArray[u]) {
            u += v
            v = u - v
            u -= v
        }

        val diff = levelArray[v] - levelArray[u]

        for (i in 0 until 19)
            if (diff shr i and 1 == 1)
                v = up[v][i]

        if (u == v)
            return u

        for (i in 18 downTo 0)
            if (up[u][i] != up[v][i]) {
                u = up[u][i]
                v = up[v][i]
            }

        return up[u][0]
    }
}
