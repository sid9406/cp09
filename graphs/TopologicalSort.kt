class DirectedGraph<T>(val vertices : Set<T>, private val edges : MutableList<Pair<T, T>>){

    val adjacencyListRepresentation = mutableMapOf<T, MutableList<T>>()

    init {
        edges.forEach {
            if (!adjacencyListRepresentation.containsKey(it.first)) adjacencyListRepresentation[it.first] = mutableListOf()
            adjacencyListRepresentation[it.first]!!.add(it.second)
        }
    }
}

class TopologicalSort<T>(private val graph: DirectedGraph<T>){
    private val visitedNodes = HashSet<T>() //gray + black
    private val fullyExploredNodes = HashSet<T>() // black
    private val reverseTopologicalOrder = mutableListOf<T>()
    private var containsCycle = false


    init {
        performTopologicalSort()
    }

    private fun performTopologicalSort(){
        graph.vertices.forEach {
            if (!visitedNodes.contains(it)){
                dfs(it)
            }
        }
    }

    private fun dfs(node : T){
        visitedNodes.add(node)
        if (graph.adjacencyListRepresentation.containsKey(node)){
            graph.adjacencyListRepresentation[node]!!.forEach {
                if (!visitedNodes.contains(it)){
                    //white
                    dfs(it)
                }else{
                    if (!fullyExploredNodes.contains(it)){
                        //gray
                        containsCycle = true
                        return
                    }
                }
            }
        }
        fullyExploredNodes.add(node)
        reverseTopologicalOrder.add(node)
    }

    fun getTopologicalOrder() : List<T> {
        if (containsCycle) return listOf()
        return reverseTopologicalOrder.reversed()
    }
}
