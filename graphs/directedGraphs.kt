open class DirectedGraph<T>(private val vertices: Set<T>, private val edges: MutableMap<T, MutableSet<T>>) {
    /**
     * used in cycle detection
     */
    private val grey = HashSet<T>()
    private val black = HashSet<T>()
    private val white = HashSet<T>()


    /**
     * used in topological sorting
     */
    private val visited = HashSet<T>()
    private val sorted = Stack<T>()


    private fun isCyclePresent(): Boolean {
        white.addAll(vertices)
        while (white.isNotEmpty()) {
            if (dfs(white.iterator().next())) return true
        }
        return false
    }

    private fun dfs(node: T): Boolean {
        white.remove(node)
        grey.add(node)

        if (edges.containsKey(node)) {
            edges[node]!!.forEach {
                if (!black.contains(it)) {
                    if (grey.contains(it)) return true
                    if (dfs(it)) return true
                }
            }
        }

        grey.remove(node)
        black.add(node)
        return false
    }

    fun topoSort(): List<T>? {
        if (isCyclePresent()) return null
        vertices.forEach {
            if (!visited.contains(it)) {
                dfs2(it)
            }
        }
        return sorted.toList()
    }

    private fun dfs2(node: T) {
        visited.add(node)
        if (edges.containsKey(node)) {
            edges[node]!!.forEach {
                if (!visited.contains(it)) {
                    dfs2(it)
                }
            }
        }
        sorted.push(node)
    }
}
