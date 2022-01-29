package main;

public class Initialize {
	
	public void initialize(Room[][] map) { 
		
		for(int i=0; i<map.length;i++) {
			for(int j=0; j< map[0].length; j++) {
				map[i][j] = new Room();
			}
		}
		
		// -1 �Ķ���, 1 ��繮, 2 ������, 3 �ڷ���Ʈ(�ʷϹ�), 4 ����� 
		
		
		map[0][0].right = 3;
		map[0][0].eventType = 3;
		map[0][0].targetX = 4;
		map[0][0].targetY = 2;
		
		
		map[1][0].right = 1;
		map[1][0].left = 3;
		
		map[2][0].left = 1;
		map[2][0].right = 1;
		map[2][0].down = 1;
		
		map[3][0].left = 1;
		map[3][0].right = 4;
		 		
		map[4][0].left = 4;

		map[2][1].down = 1;
		map[2][1].up = 1;
		
		map[0][2].down = 2;
		
		map[2][2].up = 1;
		map[2][2].down = 1;
		map[2][2].right = 1;
		
		map[3][2].left = 1;
		
		map[0][3].up = 2;
		map[0][3].right = 1;
		
		map[1][3].right = 1;
		map[1][3].left = 1;
		
		map[2][3].left = 1;
		map[2][3].right = 1;
		map[2][3].up = 1;
		
		map[3][3].left = 1;
		map[3][3].right = 1;
		
		map[4][3].left = 1;
		map[4][3].up = -1;
		
		map[0][2].eventType = 2;
		map[4][0].eventType = 4;
		map[4][2].eventType = -1;
		
	
	}
}
