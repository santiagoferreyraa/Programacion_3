//Diseño de una red de distribución eléctrica
//Una empresa de energía necesita conectar varias estaciones eléctricas en una
//región para asegurar que toda la zona esté alimentada de manera eficiente. Las
//estaciones están ubicadas en diferentes ciudades y los costos de instalación de
//las líneas eléctricas entre ellas varían según la distancia y el terreno.
//Tareas:
//Representar el grafo utilizando una lista de adyacencia.
//Aplicar el algoritmo de Prim para determinar el Árbol de Recubrimiento Mínimo.
//Mostrar el conjunto de conexiones resultante y calcular el costo total.
package prim;

import java.util.*;

public class PrimAlgorithm {
    private static final int INF = Integer.MAX_VALUE;

    public static void primMST(int numVertices, List<List<int[]>> graph) {
        int[] key = new int[numVertices];  // Array to store the minimum weight edge to include in MST
        int[] parent = new int[numVertices];  // Array to store the resultant MST
        boolean[] inMST = new boolean[numVertices];  // Array to track vertices included in MST
        int totalCost = 0; // Variable para almacenar el costo total del MST

        Arrays.fill(key, INF);  // Initialize key values as infinite
        key[0] = 0;  // Start from the first vertex
        parent[0] = -1;  // First node is always the root of the MST

        for (int count = 0; count < numVertices - 1; count++) {
            int u = minKey(numVertices, key, inMST);  // Pick the minimum key vertex not yet included in MST
            inMST[u] = true;  // Include u in MST

            // Update key and parent index of the adjacent vertices of the picked vertex
            for (int[] neighbor : graph.get(u)) {
                int v = neighbor[0];
                int weight = neighbor[1];

                // Update the key only if v is not in MST and weight of (u, v) is smaller than current key of v
                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    parent[v] = u;
                }
            }
        }

        totalCost = printMST(parent, numVertices, graph);
        System.out.println("Total cost of MST: " + totalCost);
    }

    private static int minKey(int numVertices, int[] key, boolean[] inMST) {
        int min = INF, minIndex = -1;

        for (int v = 0; v < numVertices; v++) {
            if (!inMST[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    private static int printMST(int[] parent, int numVertices, List<List<int[]>> graph) {
        int totalCost = 0;
        System.out.println("Edge \tWeight");
        for (int i = 1; i < numVertices; i++) {
            for (int[] neighbor : graph.get(i)) {
                if (neighbor[0] == parent[i]) {
                    System.out.println(parent[i] + " - " + i + "\t" + neighbor[1]);
                    totalCost += neighbor[1]; // Sumar el peso al costo total
                }
            }
        }
        return totalCost;
    }

    public static void main(String[] args) {
        int numVertices = 4;
        List<List<int[]>> graph = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to the graph (vertex1, vertex2, weight)
        graph.get(0).add(new int[]{1, 1});  // A - B with weight 1
        graph.get(0).add(new int[]{2, 4});  // A - C with weight 4
        graph.get(1).add(new int[]{0, 1});  // B - A with weight 1
        graph.get(1).add(new int[]{3, 3});  // B - D with weight 3
        graph.get(2).add(new int[]{0, 4});  // C - A with weight 4
        graph.get(2).add(new int[]{3, 2});  // C - D with weight 2
        graph.get(3).add(new int[]{1, 3});  // D - B with weight 3
        graph.get(3).add(new int[]{2, 2});  // D - C with weight 2

        primMST(numVertices, graph);
    }
}
