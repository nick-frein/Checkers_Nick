//game

package checkers;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Game {
	int[][] tiles = new int[8][8];
	int[] move = new int[4];
	boolean firstClick = true;
	boolean turnEnd = false;
	boolean secondJump = false;
	int turn = 2;

	final int JUMP_LEFT = -2;
	final int JUMP_RIGHT = 2;
	final int JUMP_UP = -2;
	final int JUMP_DOWN = 2;
	final int DOWN_ONE = 1;
	final int UP_ONE = -1;
	final int RIGHT_ONE = 1;
	final int LEFT_ONE = -1;
	final int BLACK = 2;
	final int RED = 3;
	final int BLACK_KING = 4;
	final int RED_KING = 5;
	final int GREY_SPACE = 0;
	final int BROWN_SPACE = 1;
	final int BOARD_DIMENSION = 8;
	
	String jumpSoundFile = "C:/Users/Nick/workspace/Checkers_New/src/sounds/jump.wav";
	String kingSoundFile = "C:/Users/Nick/workspace/Checkers_New/src/sounds/king.wav";

	public Game() {
		// Sets up the gameboard
		for (int x = 0; x < BOARD_DIMENSION; x++) {
			for (int y = 0; y < BOARD_DIMENSION; y++) {
				if ((x + y) % 2 == 0) {
					if (x < 3) {
						tiles[x][y] = RED;
					} else if (x > 4) {
						tiles[x][y] = BLACK;
					} else {
						tiles[x][y] = BROWN_SPACE;
					}
				} else {
					tiles[x][y] = GREY_SPACE;
				}
			}
		}
	}

	public int getTile(int x, int y) {
		return tiles[x][y];
	}

	public String getTurn() {
		if (turn == BLACK) {
			return ("BLACK");
		} else {
			return ("RED");
		}
	}

	// Sets the moves in an array. First two addresses are for the first
	// move's dimensions and the second two are for the second move's
	// dimensions
	public void setMove(int x, int y) {
		if (turnEnd == false) {
			if (firstClick) {
				// Restarts player's turn if they clicked an empty tile on first
				// click.
				if (tiles[x][y] == GREY_SPACE || tiles[x][y] == BROWN_SPACE) {
					return;
				}
				// If correct, store the first click values
				move[0] = x;
				move[1] = y;
				firstClick = false;
			} else {
				// If player doesn't click a blank space to move to, it scraps
				// the
				// move and player gets to restart turn.
				if (tiles[x][y] == RED || tiles[x][y] == BLACK) {
					move[0] = 0;
					move[1] = 0;
					firstClick = true;
					return;
				}

				// If correct, store second values
				move[2] = x;
				move[3] = y;
				firstClick = true;
				// Goes through to determine what the player did that move
				if (secondJump==false) {
					isMoveValid();
				}
				isJumpValid();
				if (secondJump==false) {
					kingMove();
				}
			}
		}
	}

	public void checkForKing() {
		if (turn == BLACK) {
			for (int scanTopRow = 0; scanTopRow < BOARD_DIMENSION; scanTopRow++) {
				if (tiles[0][scanTopRow] == BLACK) {
					tiles[0][scanTopRow] = BLACK_KING;
					playSound(kingSoundFile);
				}
			}
		} else if (turn == RED) {
			for (int scanBottomRow = 0; scanBottomRow < BOARD_DIMENSION; scanBottomRow++) {
				if (tiles[7][scanBottomRow] == RED) {
					tiles[7][scanBottomRow] = RED_KING;
					playSound(kingSoundFile);
				}
			}
		}
	}

	public void kingMove() {
		if (turn == BLACK && tiles[move[0]][move[1]] == BLACK_KING) {
			if (move[0] == (move[2] + DOWN_ONE) || move[0] == (move[2] + UP_ONE)) {
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
				}
			}
		}
		if (turn == RED && tiles[move[0]][move[1]] == RED_KING) {
			if (move[0] == (move[2] + DOWN_ONE) || move[0] == (move[2] + UP_ONE)) {
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					turn();
				}
			}
		}
	}

	// Checks if the button's pressed are a valid move
	public boolean isMoveValid() {
		// Checks to see if BLACK player clicks a BLACK checker
		if (turn == BLACK && tiles[move[0]][move[1]] == BLACK) {
			// Checks if BLACK player's second click 1 row above
			if (move[0] == (move[2] + DOWN_ONE)) {
				// Checks to see if the BLACK player's second click is diagonal
				// of the first click
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					checkForKing();
					turn();
					return true;
				}
			}

		}
		// Checks to see if RED player clicks a RED checker
		if (turn == RED && tiles[move[0]][move[1]] == RED) {
			// Checks if RED player's second click 1 row down
			if (move[0] == (move[2] + UP_ONE)) {
				// Checks to see if the RED player's second click is diagonal of
				// the first click
				if ((move[1] == (move[3] + LEFT_ONE)) || (move[1] == (move[3] + RIGHT_ONE))) {
					turnEnd = true;
					swapTiles(move[0], move[1], move[2], move[3]);
					checkForKing();
					turn();
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}

	public boolean isJumpValid() {
		if (turn == BLACK) {
			// up right
			if (move[0] > move[2] && move[1] < move[3]) {
				if (tiles[move[0] - 1][move[1] + 1] == RED || tiles[move[0] - 1][move[1] + 1] == RED_KING) {
					if (move[0] == move[2] + 2 && move[1] == move[3] - 2 && tiles[move[2]][move[3]] == BROWN_SPACE) {
						jump(RIGHT_ONE, UP_ONE);
					}
				}
			}
			// up left
			if (move[0] > move[2] && move[1] > move[3]) {
				if (tiles[move[0] - 1][move[1] - 1] == RED || tiles[move[0] - 1][move[1] - 1] == RED_KING) {
					if (move[0] == move[2] + 2 && move[1] == move[3] + 2 && tiles[move[2]][move[3]] == BROWN_SPACE) {
						jump(LEFT_ONE, UP_ONE);
					}
				}
			}
			// down right
			if (tiles[move[0]][move[1]] == BLACK_KING) {
				if (move[0] < move[2] && move[1] < move[3]) {
					if (tiles[move[0] + 1][move[1] + 1] == RED || tiles[move[0] + 1][move[1] + 1] == RED_KING) {
						if (move[0] + 2 == move[2] && move[1] + 2 == move[3]) {
							jump(RIGHT_ONE, DOWN_ONE);
						}
					}
				}
			}
			// down left
			if (tiles[move[0]][move[1]] == BLACK_KING) {
				if (move[0] < move[2] && move[1] > move[3]) {
					if (tiles[move[0] + 1][move[1] - 1] == RED || tiles[move[0] + 1][move[1] - 1] == RED_KING) {
						if (move[0] + 2 == move[2] && move[1] - 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump(LEFT_ONE, DOWN_ONE);
						}
					}
				}
			}
		}

		if (turn == RED) {
			// up right
			if (tiles[move[0]][move[1]] == RED_KING) {
				if (move[0] > move[2] && move[1] < move[3]) {
					if (tiles[move[0] - 1][move[1] + 1] == BLACK || tiles[move[0] - 1][move[1] + 1] == BLACK_KING) {
						if (move[0] - 2 == move[2] && move[1] + 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump(RIGHT_ONE, UP_ONE);
						}
					}
				}
			}
			// up left
			if (tiles[move[0]][move[1]] == RED_KING) {
				if (move[0] > move[2] && move[1] > move[3]) {
					if (tiles[move[0] - 1][move[1] - 1] == BLACK || tiles[move[0] - 1][move[1] - 1] == BLACK_KING) {
						if (move[0] - 2 == move[2] && move[1] - 2 == move[3]
								&& tiles[move[2]][move[3]] == BROWN_SPACE) {
							jump(LEFT_ONE, UP_ONE);
						}
					}
				}
			}
			// down right
			if (move[0] < move[2] && move[1] < move[3]) {
				if (tiles[move[0] + 1][move[1] + 1] == BLACK || tiles[move[0] + 1][move[1] + 1] == BLACK_KING) {
					if (move[0] + 2 == move[2] && move[1] + 2 == move[3] && tiles[move[2]][move[3]] == BROWN_SPACE) {
						jump(RIGHT_ONE, DOWN_ONE);
					}
				}
			}

			// down left
			if (move[0] < move[2] && move[1] > move[3]) {
				if (tiles[move[0] + 1][move[1] - 1] == BLACK || tiles[move[0] + 1][move[1] - 1] == BLACK_KING) {
					if (move[0] + 2 == move[2] && move[1] - 2 == move[3] && tiles[move[2]][move[3]] == BROWN_SPACE) {
						jump(LEFT_ONE, DOWN_ONE);
					}
				}
			}
		}
		return false;
	}

	public void jump(int horizontal, int vertical) {
		tiles[(move[0] + move[2]) / 2][(move[1] + move[3]) / 2] = BROWN_SPACE;
		swapTiles(move[0], move[1], move[2], move[3]);
		checkForKing();
		isjumpavailable(tiles[move[2]][move[3]]);
		playSound(jumpSoundFile);
	}

	public boolean isjumpavailable(int color){
		if (color == BLACK  || color ==BLACK_KING){
			try{
				if(tiles[move[2]-1][move[3]+1] == RED ||tiles[move[2]-1][move[3]+1] == RED_KING){
					if(tiles[move[2]-2][move[3]+2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
			try{
				if(tiles[move[2]-1][move[3]-1] == RED ||tiles[move[2]-1][move[3]-1] == RED_KING){
					if(tiles[move[2]-2][move[3]-2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		if (color ==BLACK_KING){
			try{
				if(tiles[move[2]+1][move[3]+1] == RED ||tiles[move[2]+1][move[3]+1] == RED_KING){
					if(tiles[move[2]+2][move[3]+2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
			try{
				if(tiles[move[2]+1][move[3]-1] == RED ||tiles[move[2]+1][move[3]-1] == RED_KING){
					if(tiles[move[2]+2][move[3]-2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		
		if (color == RED  || color ==RED_KING){
			try{
				if(tiles[move[2]+1][move[3]+1] == BLACK ||tiles[move[2]+1][move[3]+1] == BLACK_KING){
					if(tiles[move[2]+2][move[3]+2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				if(tiles[move[2]+1][move[3]-1] == BLACK ||tiles[move[2]+1][move[3]-1] == BLACK_KING){
					if(tiles[move[2]+2][move[3]-2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		if (color == RED_KING){
			try {
				if(tiles[move[2]-1][move[3]+1] == BLACK ||tiles[move[2]-1][move[3]+1] == BLACK_KING){
					if(tiles[move[2]-2][move[3]+2] == BROWN_SPACE){
						secondJump = true;
						return true;
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {}
			
			try {
			if(tiles[move[2]-1][move[3]-1] == BLACK ||tiles[move[2]-1][move[3]-1] == BLACK_KING){
				if(tiles[move[2]-2][move[3]-2] == BROWN_SPACE){
					secondJump = true;
					return true;
				}
			}
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		turn();
		return false;
	}

	// Every time this is called, it switches turns
	public void turn() {
		if (turn == BLACK) {
			turnEnd = false;
			secondJump = false;
			turn = RED;
		} else {
			turnEnd = false;
			secondJump = false;
			turn = BLACK;
		}
	}

	// Swaps the two values of the tiles for a normal move.
	public void swapTiles(int firstX, int firstY, int secondX, int secondY) {
		int tempTileValue = tiles[firstX][firstY];

		tiles[firstX][firstY] = tiles[secondX][secondY];
		tiles[secondX][secondY] = tempTileValue;
	}

	// Use for debugging purposes only
	public void debugCheckRealBoard() {
		for (int x = 0; x < 8; x++) {
			System.out.println();
			for (int y = 0; y < 8; y++) {
				System.out.print(tiles[x][y]);
			}
		}
	}
	
	public void playSound(String fileName) {
		try{
			File soundFile = new File(fileName);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(audioStream);
			clip.start();
		} catch(Exception cantFindFile) {
			System.out.println("can't find WAV file");
		}
	}
}