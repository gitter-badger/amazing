package com.laamella.daedalousy.generators;

import java.awt.Graphics;

/**
 * This maze generator taken from Wikipedia.
 * <p>
 * Maze, generated by my algorithm
 * <p>Source edited for clarity by quin/10-24-06
 * <p>Source edited again for clarity and reusability by DebateG/1-25-09
 */
public class Wikipedia {
	public static final int WALL_ABOVE = 1;
	public static final int WALL_BELOW = 2;
	public static final int WALL_LEFT = 4;
	public static final int WALL_RIGHT = 8;
	public static final int QUEUED = 16;
	public static final int IN_MAZE = 32;

	/* The width and height (in cells) of the maze */
	private int width;
	private int height;
	private int maze[][];

	/* The width in pixels of each cell */
	private int cell_width;

	/**
	 * Initialization method that will be called after the applet is loaded into
	 * the browser.
	 */
	public void init() {
		createMaze(20, 20);
		cell_width = 20;
	}

	private void createMaze(int width, int height) {
		int x, y, n, d;
		int dx[] = { 0, 0, -1, 1 };
		int dy[] = { -1, 1, 0, 0 };
		this.width = width;
		this.height = height;

		int todo[] = new int[height * width], todonum = 0;

		/* We want to create a maze on a grid. */
		maze = new int[width][height];

		/* We start with a grid full of walls. */
		for (x = 0; x < width; ++x) {
			for (y = 0; y < height; ++y) {
				if (x == 0 || x == width - 1 || y == 0 || y == height - 1) {
					maze[x][y] = IN_MAZE;
				} else {
					maze[x][y] = 63;
				}
			}
		}

		/* Select any square of the grid, to start with. */
		x = 1 + (int) (Math.random() * (width - 2));
		y = 1 + (int) (Math.random() * (height - 2));

		/* Mark this square as connected to the maze. */
		maze[x][y] &= ~48;

		/* Remember the surrounding squares, as we will */
		for (d = 0; d < 4; ++d) {
			if ((maze[x + dx[d]][y + dy[d]] & QUEUED) != 0) {
				/* want to connect them to the maze. */
				todo[todonum++] = ((x + dx[d]) << QUEUED) | (y + dy[d]);
				maze[x + dx[d]][y + dy[d]] &= ~QUEUED;
			}
		}

		/* We won't be finished until all is connected. */
		while (todonum > 0) {
			/* We select one of the squares next to the maze. */
			n = (int) (Math.random() * todonum);
			x = todo[n] >> 16; /* the top 2 bytes of the data */
			y = todo[n] & 65535; /* the bottom 2 bytes of the data */

			/* We will connect it, so remove it from the queue. */
			todo[n] = todo[--todonum];

			/* Select a direction, which leads to the maze. */
			do {
				d = (int) (Math.random() * 4);
			} while ((maze[x + dx[d]][y + dy[d]] & IN_MAZE) != 0);

			/* Connect this square to the maze. */
			maze[x][y] &= ~((1 << d) | IN_MAZE);
			maze[x + dx[d]][y + dy[d]] &= ~(1 << (d ^ 1));

			/* Remember the surrounding squares, which aren't */
			for (d = 0; d < 4; ++d)
				if ((maze[x + dx[d]][y + dy[d]] & QUEUED) != 0) {
					/* connected to the maze, and aren't yet queued to be. */
					todo[todonum++] = ((x + dx[d]) << QUEUED) | (y + dy[d]);
					maze[x + dx[d]][y + dy[d]] &= ~QUEUED;
				}
			/* Repeat until finished. */
		}

		/* Add an entrance and exit. */
		maze[1][1] &= ~WALL_ABOVE;
		maze[width - 2][height - 2] &= ~WALL_BELOW;
	}

	public void paint(Graphics g) {
		int x, y;

		for (x = 1; x < width - 1; ++x) {
			for (y = 1; y < height - 1; ++y) {
				if ((maze[x][y] & WALL_ABOVE) != 0)
					g.drawLine(x * cell_width, y * cell_width, x * cell_width + cell_width, y * cell_width);
				if ((maze[x][y] & WALL_BELOW) != 0)
					g.drawLine(x * cell_width, y * cell_width + cell_width, x * cell_width + cell_width, y * cell_width + cell_width);
				if ((maze[x][y] & WALL_LEFT) != 0)
					g.drawLine(x * cell_width, y * cell_width, x * cell_width, y * cell_width + cell_width);
				if ((maze[x][y] & WALL_RIGHT) != 0)
					g.drawLine(x * cell_width + cell_width, y * cell_width, x * cell_width + cell_width, y * cell_width + cell_width);
			}
		}
	}
}