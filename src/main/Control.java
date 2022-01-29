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
	JButton answerBt = new JButton("Ȯ��");

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

		arr.addElement("����� -> ������ -> �Ķ�  (�ʷϹ��� ��й�)");

		can.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
/////////////////�׹�°
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
/////////////////ù��° 
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
		arr.addElement("���� ������: " + level + "\n");
		arr.addElement("����� -> ������ -> �Ķ�  (�ʷϹ��� ��й�)");
		
		boolean isMove = false; // false�� �̵��Ұ� true �� �̵�����
		

		if (direction == -1) {// �Ķ���
			if (ball.keyBlue)
				isMove = true;
			else
				arr.addElement("�Ķ�Ű�� ������!!");

		} else if (direction == 1) { // ��繮
			isMove = true;
		}

		else if (direction == 2) { // ������
			if (ball.keyRed)
				isMove = true;
			else
				arr.addElement("����Ű�� ������!!");
		}

		else if (direction == 3) { // �̺�Ʈ ��(�ʷϹ�)
			if (level >= 30)
				isMove = true;
			else
				arr.addElement("�̵��Ұ� ��������!!");
		}

		else if (direction == 4) { // �����

			if (Yellow) {
				isMove = true;
			} else {
				g.game();
				answerTf.setVisible(true);
				answerBt.setVisible(true);
				
				while (true) { // ��ư Ŭ���Ҷ����� ��� 
					answerBt.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							if (answerTf.getText().equals("cat")) {
								arr.addElement("����!! �̵�����");	
								Yellow = true;
								//isMove = true �ִ¹� 
								
							} else {
								arr.addElement("����Ʋ��! �̵�����");		
							}		
						}	
					});break;			
				}			
			}
		} else
			arr.addElement("���� �����ϴ�!!");

		return isMove;
	}

	int load() {

		File file = new File("c:/temp/level.txt");

		if (file.exists() == true) {
			try {
				// �� �پ� �б�

				BufferedReader br = new BufferedReader(new FileReader(file));

				while (true) {

					String line = br.readLine();
					if (line == null)
						break;
					level = Integer.parseInt(line);
				}

				System.out.println("�������");
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
			fw = new FileWriter("c:/temp/level.txt"); // �̶� true ���ƴ� ���� or false �� ����ϸ� ����� true �� �������Ͽ� �ٿ��ֱ�

			String data = String.valueOf(++level);
			fw.write(data);

			fw.close();
			System.out.println("�������");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
