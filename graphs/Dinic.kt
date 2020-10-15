class DinitzMaxFlowSolver(val n : Int, val s : Int,val  t : Int, val graph : MutableMap<Int, MutableList<Edge>>, val edgeMap : MutableMap<Pair<Int,Int>, Edge>){

    private val level =  IntArray(n + 1) { -1 }  //storing levels of each vertex in level graph
    var maxFlow = 0L

    init {
        solve()
    }

    fun solve(){
        val next =  IntArray(n + 1) { 0 }
        while (bfs()) {
            Arrays.fill(next, 0)
            var flow = 0L
            do {
                maxFlow += flow
                flow = dfs(s, next, Long.MAX_VALUE)
            } while (flow != 0L)
        }
    }

    private fun dfs(at : Int, next : IntArray, flow : Long) : Long{
        if (at == t)  return flow
        var size = 0
        if (graph.containsKey(at)) size = graph[at]!!.size
        while (next[at] < size){
            val edge = graph[at]!!.get(next[at])
            if (edge.remainingCapacity() > 0 && level[edge.to] == level[at]+  1){
                val bottleNeck = dfs(edge.to, next, Math.min(flow, edge.remainingCapacity()))
                if (bottleNeck > 0){
                    edgeMap[Pair(edge.from, edge.to)]!!.flow+= bottleNeck
                    edgeMap[Pair(edge.to , edge.from)]!!.flow-= bottleNeck
                    return  bottleNeck
                }
            }
            next[at]++
        }
        return 0
    }

    private fun bfs() : Boolean{
        Arrays.fill(level, -1)
        val curLevel = ArrayDeque<Int>()
        curLevel.add(s)
        level[s] = 0

        while (curLevel.isNotEmpty()){
            val top = curLevel.poll()
            if (graph.containsKey(top)){
                graph[top]!!.forEach {
                    if (it.remainingCapacity() > 0 && level[it.to] == -1){
                        level[it.to] = level[top] +  1
                        curLevel.offer(it.to)
                    }
                }
            }
        }
        return level[t]!=-1
    }


}

class Edge(val from  : Int, val to : Int, val capacity : Long){
    var flow =0L
    fun remainingCapacity() : Long{
        return capacity - flow
    }
}
