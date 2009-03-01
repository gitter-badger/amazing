package com.laamella.amazing.generators.spacefillingcurve;

import com.laamella.amazing.generators.cut_the_knot.LogoProgram;
import com.laamella.amazing.mazemodel.Turtle;

/**
 * <p>
 * http://www.cut-the-knot.org/ctk/Mazes.shtml
 * <p>
 * http://www.cut-the-knot.org/do_you_know/hilbert.shtml
 * <p>
 * <a href="http://www.nio.ntnu.no/archive/2000_2001/2/b1.c">This algorithm from
 * the Norsk Informatikkolympiade for videregående skoler</a> has been used
 * here.
 */
// TODO could be implemented as a kind of LOGO program
public class HilbertCurveProgram implements LogoProgram {
	private final int degree;
	private final boolean mirror;

	public HilbertCurveProgram(final int degree, final boolean mirror) {
		this.degree = degree;
		this.mirror = mirror;
	}

	private void right(Turtle turtle, boolean mirror) {
		if (mirror) {
			turtle.left();
		} else {
			turtle.right();
		}
	}

	private void left(Turtle turtle, boolean mirror) {
		if (mirror) {
			turtle.right();
		} else {
			turtle.left();
		}
	}

	/**
	 * @param mirror
	 *            specifies the orientation of the maze: false: left-turning,
	 *            true: right-turning
	 */
	public void maze(final Turtle turtle, final int degree, final boolean mirror) {
		if (degree == 1) {
			turtle.walk();
			left(turtle, mirror);
			turtle.walk();
			left(turtle, mirror);
			turtle.walk();
		} else {
			left(turtle, mirror);
			maze(turtle, degree - 1, !mirror);
			left(turtle, mirror);
			turtle.walk();
			maze(turtle, degree - 1, mirror);
			right(turtle, mirror);
			turtle.walk();
			right(turtle, mirror);
			maze(turtle, degree - 1, mirror);
			turtle.walk();
			left(turtle, mirror);
			maze(turtle, degree - 1, !mirror);
			left(turtle, mirror);
		}
	}

	public void run(final Turtle turtle) {
		maze(turtle, degree, mirror);
	}
}