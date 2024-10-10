//TC: O(n^2)
//SC: O(n^2)
class Solution {
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int[] flattenedBoard = flattenBoard(board, n);

        // Queue to perform BFS, starting from position 1 (index 0)
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0}); // (index, steps)
        boolean[] visited = new boolean[n * n]; // Track visited positions
        visited[0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int index = current[0];
            int steps = current[1];

            // Explore all dice rolls (1 to 6)
            for (int i = 1; i <= 6; i++) {
                int nextIndex = index + i;
                if (nextIndex >= n * n) break;

                // If there's a snake or ladder, move to the destination
                int destination = flattenedBoard[nextIndex] == -1 ? nextIndex : flattenedBoard[nextIndex] - 1;

                // If we've reached the last square
                if (destination == n * n - 1) {
                    return steps + 1;
                }

                // If this position has not been visited yet
                if (!visited[destination]) {
                    visited[destination] = true;
                    queue.offer(new int[]{destination, steps + 1});
                }
            }
        }
        return -1; // If we can't reach the last square
    }

    // Helper method to convert 2D board to a 1D array
    private int[] flattenBoard(int[][] board, int n) {
        int[] flattened = new int[n * n];
        boolean leftToRight = true;
        int index = 0;

        for (int r = n - 1; r >= 0; r--) {
            if (leftToRight) {
                for (int c = 0; c < n; c++) {
                    flattened[index++] = board[r][c];
                }
            } else {
                for (int c = n - 1; c >= 0; c--) {
                    flattened[index++] = board[r][c];
                }
            }
            leftToRight = !leftToRight;
        }
        return flattened;
    }
}
