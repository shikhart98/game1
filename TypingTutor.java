package game;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;// swing is a framework
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

public class TypingTutor extends JFrame implements ActionListener {
	private JLabel lbltimer;
	private JLabel lblscore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;

	private Timer timer = null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score = 0;
	private String[] words = null;

	public TypingTutor(String[] args) {
		words = args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);
		Font font = new Font("Agency FB", 1, 150);
		lbltimer = new JLabel("Time");
		lbltimer.setFont(font);
		super.add(lbltimer);

		lblscore = new JLabel("Score");
		lblscore.setFont(font);
		super.add(lblscore);

		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);

		txtWord = new JTextField("");
		txtWord.setFont(font);
		super.add(txtWord);

		btnStart = new JButton("Start");
		btnStart.setFont(font);
		btnStart.addActionListener(this);
		super.add(btnStart);

		btnStop = new JButton("Stop");
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		super.add(btnStop);

		super.setTitle("Typing Tutor");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
		setupthegame();
	}

	private void setupthegame() {
		timer = new Timer(2000, this);
		running = false;
		timeRemaining = 10;
		score = 0;
		lbltimer.setText("Time: " + timeRemaining);
		lblscore.setText("Score: " + score);
		lblWord.setText("*");
		txtWord.setText("");

		btnStop.setEnabled(false);
		txtWord.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnStart) {
			handelStart();
		} else if (e.getSource() == btnStop) {
			handelStop();
		} else if (e.getSource() == timer) {
			handelTimer();
		}
	}

	private void handelTimer() {
		timeRemaining--;
		if (timeRemaining > 0) {
			String actual = txtWord.getText();
			String expected = lblWord.getText();
			if (actual.equals(expected)) {
				score++;
			}
			lbltimer.setText("Time: " + timeRemaining);
			lblscore.setText("Score: " + score);
			int ridx = (int) (Math.random() * words.length);
			lblWord.setText(words[ridx]);
			txtWord.setText("");
		}
		if (timeRemaining == 0) {
			handelStop();
		}
	}

	private void handelStart() {
		if (running == false) {
			timer.start();
			running = true;
			btnStart.setText("Pause");
			txtWord.setEnabled(true);
			btnStop.setEnabled(true);
		} else {
			timer.stop();
			running = false;
			btnStart.setText("Start");
			txtWord.setEnabled(false);
			btnStop.setEnabled(false);
		}
	}

	private void handelStop() {
		timer.stop();
		int choice= JOptionPane.showConfirmDialog(this, "Do you want to continue? ");
		if(choice == JOptionPane.YES_OPTION){
			setupthegame();
		}else if(choice== JOptionPane.NO_OPTION){
			JOptionPane.showMessageDialog(this, "Score is: "+ score);
			super.dispose();
		}else if(choice == JOptionPane.CANCEL_OPTION){
			if(timeRemaining==0)
				setupthegame();
			else
				timer.start();
		}
	}
}
