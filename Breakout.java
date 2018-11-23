
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 70;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** Delay time for the pause */
	private static final int DELAY = 10;

	/** count the number of bricks in game */
	private static int brickcounter = NBRICKS_PER_ROW * NBRICK_ROWS;
	private static int life = NTURNS;

	public void init() {
		/*
		 * Initialise the game environment, bricks, ball, paddle and their starting
		 * positions. Add mouselisteners to the game;
		 */
		// createGameEnvironment(); // ??
		addMouseListeners();

	}

	/* Method: run() */
	/** Runs the Breakout program. */

	public void run() {
		/*
		 * The Breakout game continues until the number of turns is not null and there
		 * are number of bricks in game still left; If player ends the game with any
		 * number of lives and zero bricks, program prints the suitable result
		 */
		// waitForClick();
		for (int i = 0; i < life; i++) {
			createGameEnvironment(); // creates game environment until number of turns/lives is greater than zero
			waitForClick(); // // wait until player clicks the screen
			playBreakout();
			if (brickcounter == 0) {
				/*
				 * if there are still some life left and brick number is equal to zero, game is
				 * terminated and printLabel() method is invoked
				 */
				ball.setVisible(false);
				paddle.setVisible(false);
				printWinnerLabel();
				break;
			}
			if (brickcounter > 0) {
				removeAll(); // removes everything from canvas
			}
		}
		if (brickcounter > 0) {
			/*
			 * if there are no lives left and bricks - on the contrary, it means player has
			 * lost the game
			 */
			printLoserLabel();
		}

	}

	private boolean hasWon() {
		if (brickcounter == 0) {
			isWinner = true;
		}
		return isWinner;
	}

	/*
	 * Label for user, when she/he loses the game
	 */
	private void printLoserLabel() {
		// creates new GLabel and sets its location to the center of canvas
		GLabel loser = new GLabel("LOOOSER!");
		loser.setFont("Helvetica-bold-36");
		loser.setColor(Color.BLACK);
		add(loser, (getWidth() - loser.getWidth()) / 2, (getHeight() - loser.getAscent()) / 2);
	}

	/*
	 * Label for user, when she/he won the game
	 */
	private void printWinnerLabel() {
		// Create new GLabel, add in the centre of canvas
		GLabel winner = new GLabel("Congrats, you have won!");
		winner.setFont("SansSerif-bold-28");
		winner.setColor(Color.MAGENTA);
		add(winner, (getWidth() - winner.getWidth()) / 2, (getHeight() - winner.getAscent()) / 2);
	}

	/*
	 * method #createGameEnvironment - sets the beginning environment for the
	 * breakout game
	 */
	private void createGameEnvironment() {
		// three call for ball,paddle and brick drawing functions
		drawBricks();
		drawBall();
		drawPaddle();
	}

	/*
	 * Method #drawBricks() creates GRect and gives it initial location, with proper
	 * Colouring
	 */
	private void drawBricks() {
		// create GObject and add it to the canvas using nested loop.
		int firstYOffset = BRICK_Y_OFFSET;
		int firstXOffset = (WIDTH - ((NBRICKS_PER_ROW * BRICK_WIDTH) + ((NBRICKS_PER_ROW - 1) * BRICK_SEP))) / 2;
		int changexcor; //
		int changeycor;
		for (int i = 0; i < NBRICKS_PER_ROW; i++) {
			for (int j = 0; j < NBRICKS_PER_ROW; j++) {
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				changexcor = firstXOffset + ((BRICK_WIDTH + BRICK_SEP) * i);
				changeycor = firstYOffset + ((BRICK_HEIGHT + BRICK_SEP) * j);
				add(brick, changexcor, changeycor);
				colorBricks(j);

			}
		}

	}

	/*
	 * Method #colorBricks receives an integer parameter for brick row numbering and
	 * gives them indicated colours
	 */
	public static void colorBricks(int rows) {
		// Colour the brick Object with switch case loop;
		switch (rows) {
		case 0:
		case 1:
			brick.setColor(Color.RED);
			brick.setFilled(true);
			break;
		case 2:
		case 3:
			brick.setColor(Color.ORANGE);
			brick.setFilled(true);
			break;
		case 4:
		case 5:
			brick.setColor(Color.YELLOW);
			brick.setFilled(true);
			break;
		case 6:
		case 7:
			brick.setColor(Color.GREEN);
			brick.setFilled(true);
			break;
		case 8:
		case 9:
			brick.setColor(Color.CYAN);
			brick.setFilled(true);
			break;
		default:
			brick.setFilled(true);

		}

	}

	private void drawBall() {
		// draws the GOval on canvas and adds it to the centre
		int balldiameter = BALL_RADIUS * 2;
		ball = new GOval(balldiameter, balldiameter);
		ball.setFilled(true);
		int ballxcor = (getWidth() - balldiameter) / 2;
		int ballycor = (getHeight() - balldiameter) / 2;
		add(ball, ballxcor, ballycor);
	}

	private void drawPaddle() {
		/*
		 * draw the paddle and add it to the centre of canvas
		 */
		int paddlex = (getWidth() - PADDLE_WIDTH) / 2;
		int paddley = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle, paddlex, paddley);

		// addMouseListeners();

	}

	/*
	 * The main aim of #playBreakout() method is to ensure moving of ball, bouncing
	 * it back and checking collisions and
	 */
	private void playBreakout() {

		while (true) {
			moveTheBall();
			if (ball.getY() + balldiameter > getHeight() - paddle.getHeight()) {
				// life--;
				break;
			}
			if (brickcounter == 0) {
				break;
			}
		}

	}

	private void moveTheBall() {
		// set bound for the bouncing ball

		int balldm = BALL_RADIUS * 2;
		ball.move(vx, vy);
		if ((ball.getX() < 0) || (ball.getX() + balldm > getWidth())) {
			vx = -vx;
		}
		if ((ball.getY() < 0) || (ball.getY() + balldm > getHeight())) {
			vy = -vy;
		}
		checkForCollisions(); // collisions are checked while ball is moving
		pause(DELAY); // ball floating delay time
	}

	private void checkForCollisions() {
		GObject collider = getCollidingObject(); // gets the the object collided to the ball

		if (collider == paddle) {
			// if collided object is paddle, ball is bounced back
			vy = -vy;
			// vx = -vx;
		}
		if ((collider != null) && collider != paddle) {
			// if collided object is not null and is not a paddle, it means it is brick and
			// should be removed from canvas
			brickcounter--; // decrement number of bricks
			remove(collider);
			vy = -vy;
		}

	}

	private void getInitialVelocity() {
		// ball initial x axis velocity and direction is generated randomly
		vx = rgen.nextDouble(1.0, 3.0); // choose speed
		if (rgen.nextBoolean(0.5)) // choose left or right
			vx = -vx;
		vy = 3.0; // y axis velocity is stable
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acm.program.Program#mouseMoved(java.awt.event.MouseEvent)
	 */
	public void mouseMoved(MouseEvent e) {
		/*
		 * paddle x coordinate changing according to mouse x coordinate, y is static;
		 * paddle does not moves until there is no initial click for game beginning
		 */
		if (isStarted) {
			paddle.setLocation(e.getX(), paddle.getY());
			// correct the x coordinate of paddle,in case it crosses the horizontal
			// boundaries
			if (paddle.getX() + paddle.getWidth() > getWidth()) {
				paddle.setLocation(getWidth() - paddle.getWidth(), paddle.getY());
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see acm.program.Program#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		// ball gets initial velocity after mouse is clicked on canvas; boolean
		// #isStared changes its default value after that, indicating game has started
		getInitialVelocity();
		ball.move(vx, vy);
		isStarted = true;

	}

	private GObject getCollidingObject() {
		// return GObject at given coordinates, null otherwise
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			return getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX() + balldiameter, ball.getY()) != null) {
			return getElementAt(ball.getX() + balldiameter, ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + balldiameter) != null) {
			return getElementAt(ball.getX(), ball.getY() + balldiameter);
		} else if (getElementAt(ball.getX() + balldiameter, ball.getY() + balldiameter) != null) {
			return getElementAt(ball.getX() + balldiameter, ball.getY() + balldiameter);
		} else {
			return null;
		}
	}

	private RandomGenerator rgen = RandomGenerator.getInstance(); // make a random generator instance variable
	private boolean isWinner; // make a boolean instance variable for game final result
	private static int balldiameter = BALL_RADIUS * 2;
	private double vx, vy; // ball initial velocity instance variables
	private static GRect brick; // GRect object instance
	private static GRect paddle; // GRect object instance
	private static GOval ball; // GOval object instance
	private static boolean isStarted; // boolean variable for game initial state
}
