package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Control {

	public static DefaultListModel arr = new DefaultListModel();

	JList list = new JList(arr);

	int level = 0;

	Ball ball = new Ball(2, 2, 100, 100, load());
	Game g = new Game();

	JTextField answerTf = new JTextField();
	JButton answerBt = new JButton("확인");

	boolean Down;
	boolean Up;
	boolean Left;
	boolean Right;
	boolean Jump;
	boolean Yellow;

	public void init() {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame("Maze");
		frame.setLocation(screen.width / 2 - 700, screen.height / 2 - 575);
		frame.setSize(1500, 1250);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MyCanvas can = new MyCanvas(this,ball);
		can.setLocation(50, 50);
		can.setSize(1300, 1050);

		JFrame listFrame = new JFrame("information");
		listFrame.setLocation(screen.width / 2 + 800, screen.height / 2 - 575);
		listFrame.setSize(400, 1250);
		listFrame.setLayout(null);

		list.setLocation(10, 5);
		list.setSize(380, 1200);
		listFrame.add(list);

		answerTf.setLocation(10, 1100);
		answerTf.setSize(250, 80);
		list.add(answerTf);
		answerTf.setVisible(false);

		answerBt.setLocation(265, 1100);
		answerBt.setSize(90, 80);
		list.add(answerBt);
		answerBt.setVisible(false);

		JScrollPane sp = new JScrollPane(list);
		sp.setLocation(10, 5);
		sp.setSize(365, 1200);
		listFrame.add(sp);
		listFrame.setVisible(true);

		arr.addElement("노란문 -> 빨간문 -> 파란  (초록문은 비밀문)");

		can.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
/////////////////네번째
			public void keyReleased(KeyEvent e) {

				if (e.getKeyCode() == 38) {
					Up = false;
				}
				if (e.getKeyCode() == 40) {
					Down = false;
				}
				if (e.getKeyCode() == 39) {
					Right = false;
				}
				if (e.getKeyCode() == 37) {
					Left = false;
				}

			}

			@Override
/////////////////첫번째 
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == 38) {
					Up = true;
				}

				if (e.getKeyCode() == 40) {
					Down = true;
				}

				if (e.getKeyCode() == 39) {
					Right = true;
				}

				if (e.getKeyCode() == 37) {
					Left = true;
				}
			}
		});

		frame.add(can);

		frame.setVisible(true);
	}

	

	public boolean canMove(int direction) {

		arr.clear();
		arr.addElement("현재 레벨은: " + level + "\n");
		arr.addElement("노란문 -> 빨간문 -> 파란  (초록문은 비밀문)");
		
		boolean isMove = false; // false면 이동불가 true 면 이동가능
		

		if (direction == -1) {// 파란문
			if (ball.keyBlue)
				isMove = true;
			else
				arr.addElement("파란키를 가져와!!");

		} else if (direction == 1) { // 모든문
			isMove = true;
		}

		else if (direction == 2) { // 빨간문
			if (ball.keyRed)
				isMove = true;
			else
				arr.addElement("빨간키를 가져와!!");
		}

		else if (direction == 3) { // 이벤트 문(초록문)
			if (level >= 30)
				isMove = true;
			else
				arr.addElement("이동불가 레벨부족!!");
		}

		else if (direction == 4) { // 노란문

			if (Yellow) {
				isMove = true;
			} else {
				g.game();
				answerTf.setVisible(true);
				answerBt.setVisible(true);
				
				while (true) { // 버튼 클릭할때까지 대기 
					answerBt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if (answerTf.getText().equals("cat")) {
								arr.addElement("정답!! 이동가능");	
								Yellow = true;
								//isMove = true 넣는법 
								
							} else {
								arr.addElement("문제틀림! 이동못해");		
							}		
						}	
					});break;			
				}			
			}
		} else
			arr.addElement("문이 없습니다!!");

		return isMove;
	}

	int load() {

		File file = new File("c:/temp/level.txt");

		if (file.exists() == true) {
			try {
				// 한 줄씩 읽기

				BufferedReader br = new BufferedReader(new FileReader(file));

				while (true) {

					String line = br.readLine();
					if (line == null)
						break;
					level = Integer.parseInt(line);
				}

				System.out.println("정상실행");
				br.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return level;
	}

	void save() {
		FileWriter fw;
		try {
			fw = new FileWriter("c:/temp/level.txt"); // 이때 true 가아닌 공백 or false 를 사용하면 덮어쓰기 true 면 기존파일에 붙여넣기

			String data = String.valueOf(++level);
			fw.write(data);

			fw.close();
			System.out.println("정상실행");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
