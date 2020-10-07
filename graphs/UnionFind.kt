abstract class AbstractUnionFind<ID, E : IdentifiableEntity<ID>>{

    val map = mutableMapOf<ID, AbstractNode<ID, E>>()

    fun makeSet(elements : List<E>){
        elements.forEach {
            map[it.id] = createNewNode(it)
        }
    }

    abstract fun createNewNode(ele : E) : AbstractNode<ID, E>

    fun union(id1 : ID, id2 : ID){
        val node1 = find(id1)
        val node2 = find(id2)

        when {
            node1.rank > node2.rank -> node2.parent = node1
            node1.rank < node2.rank -> node1.parent = node2
            else -> {
                node1.rank = node1.rank + 1
                node2.parent = node1
            }
        }
    }

    fun find(x : ID) : AbstractNode<ID, E>{
        var cur= map[x]!!
        while (cur.parent != null){
            cur = cur.parent!!
        }
        return cur
    }
}

abstract class AbstractNode<ID, E : IdentifiableEntity<ID>>{
    abstract val id : ID
    abstract val data : E
    abstract var rank : Int
    abstract var parent : AbstractNode<ID, E>?
}

interface IdentifiableEntity<ID>{
    var id : ID
}
