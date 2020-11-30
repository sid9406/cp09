class LCA(val n: Int, val graph: MutableMap<Int, MutableList<Int>>) {

    val levelArray = IntArray(n + 1) { 0 }
    val up = Array(n + 1) { IntArray(19) { -1 } }

    init {
        dfs(1, -1)
        buildUp()
    }

    fun dfs(root: Int, parent: Int) {
        if (graph.containsKey(root)) {
            graph[root]!!.forEach {
                if (it != parent) {
                    levelArray[it] = levelArray[root] + 1
                    up[it][0] = root
                    dfs(it, root)
                }
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

    fun distance(a: Int, b: Int): Int {
        //calculates distance between node a and b
        return levelArray[a] + levelArray[b] - 2 * levelArray[lca(a, b)]
    }

    fun lift(node: Int, c: Int): Int {
        //find the node which we get after lifting a by c
        var cur = node
        for (i in 0 until 19)
            if (c shr i and 1 == 1)
                cur = up[cur][i]

        return cur
    }


    fun lca(uu: Int, vv: Int): Int { // 5 and 4
        var u = uu
        var v = vv

        if (levelArray[v] < levelArray[u]) {
            return lca(v, u)
        }

        val diff = levelArray[v] - levelArray[u]

        v = lift(v, diff)

        if (u == v)
            return u

        for (i in 18 downTo 0) {
            if (up[u][i] != up[v][i]) {
                u = up[u][i]
                v = up[v][i]
            }
        }

        return up[u][0]
    }
}
