package main;

import java.awt.*;

public class MyCanvas extends Canvas {

	Image buffImage;
	Graphics buffg;
	Ball ball;

	Room[][] map = new Room[5][4];

	Initialize li = new Initialize();

	Control c;

	int boxHeight = 200;
	int boxWidth = 200;
	int gap = 50;

	public MyCanvas(Control c, Ball ball) {
		this.c = c;
		this.ball = ball;
		c.arr.addElement("현재 레벨은: " + c.load() + "\n");
		new MyThread().start();
	}

	@Override
	public void paint(Graphics g) {
		if (buffg == null) {
			buffImage = createImage(getWidth(), getHeight());
			if (buffImage == null) {
				System.out.println("오프 스크린 생성 실패");
			} else {
				buffg = buffImage.getGraphics();
			}
		}
		update(g);
	}

	public void setDoorColor(Graphics buffg, int value) {

		if (value == -1) {
			buffg.setColor(Color.blue);
		} else if (value == 1) {
			buffg.setColor(Color.DARK_GRAY);
		} else if (value == 2) {
			buffg.setColor(Color.red);
		} else if (value == 3) {
			buffg.setColor(Color.green);
		} else if (value == 4) {
			buffg.setColor(Color.yellow);
		} else {
			buffg.setColor(Color.white);
		}
	}

	@Override
	public void update(Graphics g) {

		if (buffg != null) {

			buffg.setColor(Color.white);
			buffg.fillRect(0, 0, 1300, 1050);

			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					buffg.setColor(Color.DARK_GRAY);
					buffg.fillRect((i * (boxWidth + gap)) + gap, (j * (boxHeight + gap)) + gap, boxWidth, boxHeight);
					// (몇번째박스*(박스너비+갭)+처음좌표)

					if (j != 0) {
						setDoorColor(buffg, map[i][j].up);
						buffg.fillRect((i * (boxWidth + gap)) + gap + gap, (j * (boxHeight + gap)) + gap - gap,
								boxWidth - 100, 50);
					}

					if (j != map[0].length - 1) {
						setDoorColor(buffg, map[i][j].down);
						buffg.fillRect((i * (boxWidth + gap)) + gap + gap, (j * (boxHeight + gap)) + gap + boxHeight,
								boxWidth - 100, 50);
					}

					if (i != map.length - 1) {
						setDoorColor(buffg, map[i][j].right);
						buffg.fillRect((i * (boxWidth + gap)) + gap + boxWidth, (j * (boxHeight + gap)) + gap + gap, 50,
								100);
					}

					if (i != 0) {
						setDoorColor(buffg, map[i][j].left);
						buffg.fillRect((i * (boxWidth + gap)) + gap - gap, (j * (boxHeight + gap)) + gap + gap, 50,
								100);
					}

				}
			}

			Image img = (Toolkit.getDefaultToolkit()).getImage("my.png");
			buffg.drawImage(img,
					(ball.arrPosX * boxWidth) + (ball.arrPosX + 1) * gap + (boxWidth / 2) - (ball.width / 2),
					(ball.arrPosY * boxWidth) + (ball.arrPosY + 1) * gap + ((boxWidth / 2) - (ball.width / 2)),
					ball.width, ball.height, this);

			g.drawImage(buffImage, 0, 0, this);
		}
	}

	void eventCheck() {

		if (map[ball.arrPosX][ball.arrPosY].eventType == 3) {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[0].length; j++) {
					if (map[i][j].eventType == -1) {
						ball.arrPosX = i;
						ball.arrPosY = j;
						ball.posX = ((i * boxWidth) + gap * (i + 1) + ((boxWidth / 2) - (ball.width / 2)));
						ball.posY = ((j * boxHeight) + gap * (j + 1) + (boxHeight / 2) - (ball.height / 2));
					}
				}
			}
		}

		if (map[ball.arrPosX][ball.arrPosY].eventType == 4) {
			if (!ball.keyRed) { // 이렇게 안하면 들어갈떄마다 빨간키를 획득했다고 뜬다 
				c.arr.addElement("빨간키획득");

			}
			c.answerTf.setVisible(false);
			c.answerBt.setVisible(false);
			ball.keyRed = true;

		}
		if (map[ball.arrPosX][ball.arrPosY].eventType == 2) {
			if (!ball.keyBlue) {
				c.arr.addElement("파란키획득");

			}
			ball.keyBlue = true;
		}
		if (map[ball.arrPosX][ball.arrPosY].eventType == -1) {
			c.arr.addElement("게임끝! 3초후 자동종료");
			c.save();

			try {
				Thread.sleep(3000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

/////////////////두번쨰
	class MyThread extends Thread {
		@Override
		public void run() {

			while (true) {

				li.initialize(map);

				if (c.Up) {
					if (c.canMove(map[ball.arrPosX][ball.arrPosY].up)) {
						c.arr.addElement("위로이동");
						ball.up();
						eventCheck();
					}
				}
				if (c.Down) {
					if (c.canMove(map[ball.arrPosX][ball.arrPosY].down)) {
						c.arr.addElement("아래로이동");
						ball.down();
						eventCheck();
					}
				}
				if (c.Right) {
					if (c.canMove(map[ball.arrPosX][ball.arrPosY].right)) {
						c.arr.addElement("오른쪽으로이동");
						ball.right();
						eventCheck();
					}
				}
				if (c.Left) {
					if (c.canMove(map[ball.arrPosX][ball.arrPosY].left)) {
						c.arr.addElement("왼쪽으로이동");
						ball.left();
						eventCheck();
					}
				}

				try {
					Thread.sleep(150);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				repaint();

			}
		}
	}
}
