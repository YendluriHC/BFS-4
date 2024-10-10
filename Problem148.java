//TC: O(m*n)
//SC: O(m*n)

class Solution {
    // Directions for 8 possible adjacent cells (left, right, up, down, and 4 diagonals)
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];

        // Case 1: If we click on a mine, change it to 'X' and game over.
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }

        // Use BFS to reveal cells
        bfs(board, x, y);
        return board;
    }

    private void bfs(char[][] board, int x, int y) {
        // Initialize the BFS queue
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int currX = curr[0], currY = curr[1];

            // Count adjacent mines
            int minesCount = countMines(board, currX, currY);

            if (minesCount > 0) {
                // If there are adjacent mines, mark the current cell with the mines count
                board[currX][currY] = (char) (minesCount + '0');
            } else {
                // Mark the cell as 'B'
                board[currX][currY] = 'B';
                // Add all its unrevealed neighbors to the queue
                for (int[] dir : DIRECTIONS) {
                    int newX = currX + dir[0], newY = currY + dir[1];
                    if (newX >= 0 && newY >= 0 && newX < board.length && newY < board[0].length && board[newX][newY] == 'E') {
                        board[newX][newY] = 'B'; // To avoid revisiting, mark it as visited before adding to the queue
                        queue.add(new int[]{newX, newY});
                    }
                }
            }
        }
    }

    // Helper function to count adjacent mines around (x, y)
    private int countMines(char[][] board, int x, int y) {
        int count = 0;
        for (int[] dir : DIRECTIONS) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            // Check if there's a mine in the adjacent cells
            if (newX >= 0 && newY >= 0 && newX < board.length && newY < board[0].length && board[newX][newY] == 'M') {
                count++;
            }
        }
        return count;
    }
}
