package main;

public class Ball {
	int posX;// Äµ¹ö½º»ó ÁÂÇ¥
	int posY;
	int arrPosX; // ¹è¿­»ó ÁÂÇ¥
	int arrPosY;
	int width;
	int height;
	int level;
	int move = 250;
	
	public Ball( int arrPosX, int arrPosY, int width, int height, int level) {
		this.arrPosX = arrPosX;
		this.arrPosY = arrPosY;
		this.width = width;
		this.height = height;
		this.level = level;
	}
	public Ball() {
		
	}

	boolean keyRed = false;
	boolean keyBlue = false;
	
	
	public void up() {
		posY -= move;
		arrPosY--;
	}

	public void down() {
		posY += move;
		arrPosY++;
	}

	public void left() {
		posX -= move;
		arrPosX--;
	}

	public void right() {
		posX += move;
		arrPosX++;
	}

}
