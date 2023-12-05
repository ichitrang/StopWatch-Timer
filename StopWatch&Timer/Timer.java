import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Timer extends JFrame {
    private static final long serialVersionUID = 1L;
    private JLabel label;
    private long endTime;

    public Timer(long duration) {
        setTitle("Timer");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        label = new JLabel(formatTime(duration));
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        label.setHorizontalAlignment(JLabel.CENTER);

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");
        JButton resetButton = new JButton("Reset");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                endTime = System.currentTimeMillis() + duration;
                javax.swing.Timer timer = new javax.swing.Timer(100, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        long remainingTime = endTime - System.currentTimeMillis();
                        if (remainingTime <= 0) {
                            ((javax.swing.Timer) e.getSource()).stop();
                            remainingTime = 0;
                        }
                        updateLabel(remainingTime);
                    }
                });
                timer.start();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Stop the timer (if any)
                updateLabel(0);
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateLabel(duration);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        setLayout(new BorderLayout());
        add(label, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateLabel(long remainingTime) {
        String time = formatTime(remainingTime);
        label.setText(time);
    }

    private String formatTime(long timeInMillis) {
        long seconds = timeInMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Set the timer duration in milliseconds (e.g., 10 minutes)
                long duration = 10 * 60 * 1000;
                new Timer(duration).setVisible(true);
            }
        });
    }
}
