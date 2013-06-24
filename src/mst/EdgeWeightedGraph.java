package mst;


/**
 *  The <tt>EdgeWeightedGraph</tt> class represents an undirected graph of vertices
 *  named 0 through V-1, where each edge has a real-valued weight.
 *  It supports the following operations: add an edge to the graph,
 *  in the graph, iterate over all of the neighbors incident to a vertex.
 *  Parallel edges and self-loops are permitted.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/43mst">Section 4.3</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */


public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private Bag<Edge>[] adj;
    
   /**
     * Create an empty edge-weighted graph with V vertices.
     */
    @SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V) {
        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");
        this.V = V;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<Edge>();
        }
    }

   /**
     * Create a random edge-weighted graph with V vertices and E edges.
     * The expected running time is proportional to V + E.
     */
    public EdgeWeightedGraph(int V, int E) {
        this(V);
        if (E < 0) throw new IllegalArgumentException("Number of edges in a Graph must be nonnegative");
        for (int i = 0; i < E; i++) {
            int v = (int) (Math.random() * V);
            int w = (int) (Math.random() * V);
            double weight = Math.round(100 * Math.random()) / 100.0;
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }

   /*
   Create a weighted graph from input stream.
    public EdgeWeightedGraph(In in) {
        this(in.readInt());
        int E = in.readInt();
        for (int i = 0; i < E; i++) {
            int v = in.readInt();
            int w = in.readInt();
            double weight = in.readDouble();
            Edge e = new Edge(v, w, weight);
            addEdge(e);
        }
    }
    */

   /**
     * Copy constructor.
     */
    public EdgeWeightedGraph(EdgeWeightedGraph G) {
        this(G.V());
        this.E = G.E();
        for (int v = 0; v < G.V(); v++) {
            // reverse so that adjacency list is in same order as original
            Stack<Edge> reverse = new Stack<Edge>();
            for (Edge e : G.adj[v]) {
                reverse.push(e);
            }
            for (Edge e : reverse) {
                adj[v].add(e);
            }
        }
    }

   /**
     * Return the number of vertices in this graph.
     */
    public int V() {
        return V;
    }

   /**
     * Return the number of edges in this graph.
     */
    public int E() {
        return E;
    }


   /**
     * Add the undirected edge e to this graph.
     */
    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }


   /**
     * Return the edges incident to vertex v as an Iterable.
     * To iterate over the edges incident to vertex v, use foreach notation:
     * <tt>for (Edge e : graph.adj(v))</tt>.
     */
    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

   /**
     * Return all edges in this graph as an Iterable.
     * To iterate over the edges in the graph, use foreach notation:
     * <tt>for (Edge e : G.edges())</tt>.
     */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<Edge>();
        for (int v = 0; v < V; v++) {
            int selfLoops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                // only add one copy of each self loop
                else if (e.other(v) == v) {
                    if (selfLoops % 2 == 0) list.add(e);
                    selfLoops++;
                }
            }
        }
        return list;
    }



   /**
     * Return a string representation of this graph.
     */
    public String toString() {
        String NEWLINE = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }


}

