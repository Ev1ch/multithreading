package bounce.table;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class TableCanvas extends JPanel {
    public Table table = new Table(this);
    public TableThread thread;

    public TableCanvas() {
        this.thread = new TableThread(table);
        thread.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        super.paintComponent(g);
        table.draw(g2);
    }
}