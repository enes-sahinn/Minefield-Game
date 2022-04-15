import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.*;

public class Minefield {
	private JFrame frame;
	private Button[][] board;
	int openedButton;
	
	public Minefield() {
		frame = new JFrame("Minefield Game");
		frame.setBounds(400, 75, 700, 700);
		frame.setLayout(new GridLayout(10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board = new Button[10][10];
		openedButton = 0;
		
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				Button b = new Button(row, col);
				board[row][col] = b;
				frame.add(b);
				b.addMouseListener(new MouseAdapter() {  
					public void mouseClicked(MouseEvent e) {
						Button b = (Button) e.getComponent();
						if (e.getButton() == 1) {
							if (b.isMine()) {
								print();
								JOptionPane.showMessageDialog(frame, "You stepped on a mine. Game Over!");
							}
							else {
								open(b.getRow(), b.getCol());
								if (openedButton == board.length * board[0].length - 10) {
									print();
									JOptionPane.showMessageDialog(frame, "Congrulations! You won the game!");
								}
							}
						}
						else if (e.getButton() == 3) {
							if (!b.isFlag()) {
								b.setIcon(new ImageIcon("flag.png"));
								b.setFlag(true);
							}
							else {
								b.setFlag(false);
								b.setIcon(null);
							}
						}
					}
				});
			}
		}	
		createMine();
		updateCount();
		frame.setVisible(true);
	}

	private void open(int r, int c) {
		if (r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() != 0 || !board[r][c].isEnabled()) {
			return;
		}
		else if (board[r][c].getCount() != 0 ) {
			board[r][c].setEnabled(false);
			board[r][c].setText(board[r][c].getCount() + "");
			openedButton++;
		}
		else {
			board[r][c].setEnabled(false);
			openedButton++;
			open(r-1, c);
			open(r, c-1);
			open(r, c+1);
			open(r+1, c);
		}
	}
	
	public void createMine() {
		int i = 0;
		while (i < 10) {
			int randRow = (int) (Math.random() * board.length);
			int randCol = (int) (Math.random() * board[0].length);
			if (!board[randRow][randCol].isMine()) {
				board[randRow][randCol].setMine(true);
				i++;
			}
		}
	}
	
	private void updateCount() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					counting(row, col);
				}
			}
		}	
	}
	
	private void counting(int row, int col) {
		for (int i = row-1; i <= row+1; i++) {
			for (int j = col-1; j <= col+1; j++) {
				try{
					board[i][j].setCount(board[i][j].getCount() + 1);
				} catch (Exception e) {
				}	
			}
		}
	}
	
	private void print() {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (board[row][col].isMine()) {
					board[row][col].setIcon(new ImageIcon("mine.png"));
				}
				else {
					board[row][col].setText(board[row][col].getCount()+"");
					board[row][col].setEnabled(false);
				}
			}
		}		
	}
	
}
