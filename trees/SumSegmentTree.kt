class SumSegmentTree(
        input: Array<Int>
) {
    private val tree: Array<Int>
    private val lazy: Array<Int>

    constructor(size: Int) : this(Array(size) { 0 })

    init {
        val size = nextPowerOfTwo(input.size)
        tree = Array(2 * size) { 0 }
        lazy = Array(2 * size) { 0 }
        for (i in 0 until input.size) {
            tree[i + size] = input[i]
        }
        for (i in (size - 1) downTo 1) {
            tree[i] = tree[leftChild(i)] + tree[rightChild(i)]
        }
    }

    private fun updateTree(lowerRange: Int, upperRange: Int, lowerBound: Int, upperBound: Int, index: Int, value: Int) {
        updateLazyNode(index, lowerBound, upperBound, lazy[index])
        if (noOverlap(lowerRange, upperRange, lowerBound, upperBound)) return
        else if (completeOverlap(lowerRange, upperRange, lowerBound, upperBound)) updateLazyNode(index, lowerBound, upperBound, value)
        else {
            updateTree(lowerRange, upperRange, lowerBound, midIndex(lowerBound, upperBound), leftChild(index), value)
            updateTree(lowerRange, upperRange, midIndex(lowerBound, upperBound) + 1, upperBound, rightChild(index), value)
            tree[index] = tree[leftChild(index)] + tree[rightChild(index)]
        }
    }

    private fun queryTree(lowerRange: Int, upperRange: Int, lowerBound: Int, upperBound: Int, index: Int): Int {
        updateLazyNode(index, lowerBound, upperBound, lazy[index])
        if (noOverlap(lowerRange, upperRange, lowerBound, upperBound)) return 0
        else if (completeOverlap(lowerRange, upperRange, lowerBound, upperBound)) return tree[index]
        else {
            return queryTree(lowerRange, upperRange, lowerBound, midIndex(lowerBound, upperBound), leftChild(index)) +
                    queryTree(lowerRange, upperRange, midIndex(lowerBound, upperBound) + 1, upperBound, rightChild(index))
        }
    }

    private fun updateLazyNode(index: Int, lowerBound: Int, upperBound: Int, delta: Int) {
        tree[index] += delta
        if (lowerBound != upperBound) {
            lazy[leftChild(index)] += delta
            lazy[rightChild(index)] += delta
        }
        lazy[index] = 0
    }

    fun getElements(N: Int): List<Int> {
        return tree.copyOfRange(tree.size / 2, tree.size / 2 + N).asList()
    }

    fun update(lowerRange: Int, upperRange: Int, value: Int) {
        updateTree(lowerRange, upperRange, 1, lazy.size / 2, 1, value)
    }

    fun query(lowerRange: Int, upperRange: Int): Int {
        return queryTree(lowerRange, upperRange, 1, lazy.size / 2, 1)
    }

    private fun noOverlap(l: Int, u: Int, lb: Int, ub: Int): Boolean = (lb > u || ub < l)

    private fun completeOverlap(l: Int, u: Int, lb: Int, ub: Int): Boolean = (lb >= l && ub <= u)


    private fun nextPowerOfTwo(num: Int): Int {
        var exponent = 2
        while (true) {
            if (exponent >= num) {
                return exponent
            }
            exponent *= 2
        }
    }

    private fun midIndex(l: Int, r: Int) = (l + r) / 2
    private fun parent(i: Int) = i / 2
    private fun leftChild(i: Int) = 2 * i
    private fun rightChild(i: Int) = 2 * i + 1

}
