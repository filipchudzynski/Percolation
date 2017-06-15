
import edu.princeton.cs.algs4.WeightedQuickUnionUF;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Filip
 */
public class Percolation {

    private boolean[][] grid;
    private int[] ind, sizesOfTrees, connectedToExit;
    private WeightedQuickUnionUF wquf;
    private int size, openSites, lastConnectedIndex;

    public Percolation(int n)// create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new IllegalArgumentException("Size of the grid has to be a "
                    + "non zero positive number");
        }
        size = n;
        grid = new boolean[n][n];
        ind = new int[n * n + 2];
        wquf = new WeightedQuickUnionUF(n*n+2);
        connectedToExit = new int[n];
        lastConnectedIndex = 0;
        for (int i = 0; i < n * n + 2; ++i) {
            ind[i] = i;
        }
        sizesOfTrees = new int[n * n + 2];
        for (int j = 0; j < n * n; ++j) {
            sizesOfTrees[j] = 1;
        }
    }

    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IndexOutOfBoundsException("Index has to be in range from "
                    + "1 to n");
        }
        int i = row - 1, j = col - 1;
        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            ++openSites;
        } else {
            return;
        }
        if (i > 0 && isOpen(row - 1, col)) {
            wquf.union(i * size + j, (i - 1) * size + j);
        } else if (row == 1) {
            wquf.union(i * size + j, size * size);
// extra index which serves as a root of percolation
        }
        if (row < size && isOpen(row + 1, col)) {
            wquf.union(i * size + j, (i + 1) * size + j);
        } else if (row == size) {
            connectedToExit[lastConnectedIndex] = j;
            ++lastConnectedIndex;
            // union(i * size + j, size * size + 1);
// extra index which serves as a exit of percolation
        }
        if (j > 0 && isOpen(row, col - 1)) {
            wquf.union(i * size + j, (i) * size + j - 1);
        }
        if (col < size && isOpen(row, col + 1)) {
            wquf.union(i * size + j, (i) * size + j + 1);
        }

        
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IndexOutOfBoundsException("Index has to be in range from"
                    + " 1 to n");
        }
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        return isOpen(row, col) ? wquf.connected((row - 1) * size + col - 1, size * size) : false;
    } // is site (row, col) full?

    public int numberOfOpenSites() {
        return openSites;
    } // number of open sites

    public boolean percolates() {
        for (int k = 0; k < lastConnectedIndex; ++k) {
            if (wquf.connected(connectedToExit[k] + size * (size - 1), size * size)) {
                wquf.union(connectedToExit[k] + size * (size - 1), size * size + 1);
            }
        }
        return wquf.connected(size * size, size * size + 1);
    }

//    private void union(int p, int q) {
//        int i = root(p);
//        int j = root(q);
//        if (i == j) {
//            return;
//        }
//        if (sizesOfTrees[i] > sizesOfTrees[j]) {
//            ind[j] = i;
//            sizesOfTrees[i] += sizesOfTrees[j];
//        } else {
//            ind[i] = j;
//            sizesOfTrees[j] += sizesOfTrees[i];
//        }
//    }
//    private boolean connected(int p, int q) {
//        return root(p) == root(q);
//    }
//    private int root(int index) {
//        while (ind[index] != index) {
//            index = ind[index];
//        }
//        return index;
//    }

    public static void main(String[] args) {

    }
}
