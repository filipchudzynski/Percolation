/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 *
 * @author Filip
 */
public class PercolationStats {

    private double[] percolationThresholds;
    private int trials;

    public PercolationStats(int n, int trials) // perform trials independent experiments on an n-by-n grid
    {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        percolationThresholds = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation per = new Percolation(n);
            while (!per.percolates()) {
                per.open(StdRandom.uniform(1, n + 1),
                        StdRandom.uniform(1, n + 1));
            }
            percolationThresholds[i] = (double) per.numberOfOpenSites() / (double) (n * n);
        }

    }

    public double mean() // sample mean of percolation threshold
    {
        return StdStats.mean(percolationThresholds);
    }

    public double stddev() // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(percolationThresholds);
    }

    public double confidenceLo() // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / java.lang.Math.sqrt(trials);
    }

    public double confidenceHi() // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / java.lang.Math.sqrt(trials);
    }

    public static void main(String[] args) // test client (described below)
    {
        if (args.length > 0) {
            int n, trials;
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
            PercolationStats perStats = new PercolationStats(n, trials);
            StdOut.print("mean: " + perStats.mean());
            StdOut.println();
            StdOut.print("stddev: " + perStats.stddev());
            StdOut.println();
            StdOut.print("95% confidence interval: ["
                    + perStats.confidenceLo()
                    + perStats.confidenceHi() + "]");
            StdOut.println();

        }
    }

}
