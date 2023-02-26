package bounce;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bounce.balls.Ball;
import bounce.balls.BallColor;
import bounce.events.Handler;
import bounce.table.TableCanvas;

public class BounceFrame extends JFrame {

  private TableCanvas canvas;
  public static final int WIDTH = 450;
  public static final int HEIGHT = 350;

  public BounceFrame() {
    this.setSize(WIDTH, HEIGHT);
    this.setTitle("Bounce programm");

    this.canvas = new TableCanvas();
    System.out.println("In Frame Thread name = "
        + Thread.currentThread().getName());
    Container content = this.getContentPane();
    content.add(this.canvas, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.lightGray);

    JButton buttonStart = new JButton("Add a blue ball");
    JButton buttonStartRed = new JButton("Add a red ball");
    JButton buttonStop = new JButton("Stop");
    JButton buttonPools = new JButton("Add pools");

    JLabel label = new JLabel();

    buttonStart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        canvas.table.addBall(BallColor.BLUE);
        // canvas.table.addBall(BallColor.BLUE);
        // canvas.table.addBall(BallColor.BLUE);
        // canvas.table.addBall(BallColor.BLUE);
        // canvas.table.addBall(BallColor.RED);
      }
    });

    buttonStartRed.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        canvas.table.addBall(BallColor.RED);
      }
    });

    buttonStop.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        System.exit(0);
      }

    });

    buttonPools.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        canvas.table.addPools();
      }
    });

    canvas.table.addOnBallInPoolHandler(new Handler<Ball>() {
      @Override
      public void run(Ball ball) {
        label.setText("Balls in pools: " + canvas.table.getInPoolsBalls().size());
      }
    });

    buttonPanel.add(buttonStop);
    buttonPanel.add(buttonStart);
    buttonPanel.add(buttonStartRed);
    buttonPanel.add(buttonPools);
    buttonPanel.add(label);

    content.add(buttonPanel, BorderLayout.SOUTH);
  }
}