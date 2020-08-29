class MaxFlow {

    int[][] paths;
    int src;
    int sink;
    int max_flow = 0;
    Map<Integer, Integer> map = new HashMap<>();

    MaxFlow(int[][] paths, int src, int sink) {
        this.paths = paths;
        this.src = src;
        this.sink = sink;
    }

    public int getMaxFlow() {
        while (bfs()) {
            //count the flow in the critical edge
            int cur = sink;
            int flow = Integer.MAX_VALUE;
            do {
                flow = Math.min(flow, paths[map.get(cur)][cur]);
                cur = map.get(cur);
            } while (cur != src);

            //subtract this flow from all the flows and add to reverse flows
            cur = sink;
            do {
                paths[map.get(cur)][cur] -= flow;
                paths[cur][map.get(cur)] += flow;
                cur = map.get(cur);
            } while (cur != src);
            max_flow += flow;
        }
        return max_flow;
    }

    private boolean bfs() {
        boolean possible = false;
        map.clear();
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        HashSet<Integer> visited = new HashSet<>();
        queue.add(src);
        visited.add(src);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int i = 0; i < paths[0].length; i++) {
                if (!visited.contains(i) && paths[cur][i] > 0) {
                    map.put(i, cur);
                    visited.add(i);
                    queue.add(i);
                    if (i == sink) {
                        possible = true;
                        break;
                    }
                }
            }
        }
        return possible;
    }

}
