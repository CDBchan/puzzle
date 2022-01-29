package main;

import java.util.Scanner;

public class Room {
	
	int up; 
	int down; 
	int left;
	int right;
	int eventType;  // ,-1 클리어,  0 없음, 1 A이벤트 , 2 B이벤트, 3 순간이동 이벤트,4 노란문(퀴즈)
	int posX; // 처음 사진 좌표
	int posY;// 처음 사진 좌표 
	int targetX;// 순간이동이벤트때 사용
	int targetY;// 순간이동 이벤트때 사용
	
	
}
