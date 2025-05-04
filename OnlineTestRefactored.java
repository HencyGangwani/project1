import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineTestRefactored extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JLabel label;
    JRadioButton[] radioButton = new JRadioButton[4];
    JButton btnNext, btnBookmark;
    ButtonGroup bg;
    int count = 0, current = 0, x = 1, now = 0;
    int[] m = new int[10];
    JButton[] bookmarks = new JButton[10];

    String[] questions = {
            "Which of these is the Capital of UAE?",
            "Who created Github Application?",
            "What would be the heart rate if output=5L, EDV=100ml, ESV=50ml?",
            "Which is the host country of G20 summit this year?",
            "What is the parent company of ChatGPT?",
            "How to read entire file in one line using Java 8?",
            "Hydrolysis of Sucrose is catalysed by?",
            "Who is NOT a part of Mahabharata?",
            "What was formed in S.Miller's experiment?",
            "Which is NOT an Environmental Burden?"
    };

    String[][] options = {
            {"Dubai", "Abu Dhabi", "Fujera", "Ras-al-Khaimah"},
            {"Bill Gates", "Maroor Chethan Pai", "Elon Musk", "Tom Preston Werner"},
            {"100 beats per minute", "70 beats per minute", "92 beats per minute", "The person has died :("},
            {"Brazil", "Indonesia", "India", "China"},
            {"OpenAI", "Microsoft", "Meta", "NeuroLink"},
            {"Files.readAllLines()", "Files.read()", "Files.readFile()", "Files.lines()"},
            {"H+", "enzymes", "Mineral acids", "all of the above"},
            {"Ghatotkatcha", "Hanuman", "Khumbhakaran", "Pandu"},
            {"amino acids", "lipids", "UV radiations", "Nucleic acid"},
            {"Oligotrophication", "Eutrophication", "Dystrophy", "None of the above"}
    };

    int[] answers = {1, 3, 0, 2, 0, 0, 3, 2, 0, 0};

    public OnlineTestRefactored(String title) {
        super(title);

        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 14));

        bg = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            radioButton[i] = new JRadioButton();
            bg.add(radioButton[i]);
        }

        btnNext = new JButton("Next");
        btnBookmark = new JButton("Bookmark");
        btnNext.addActionListener(this);
        btnBookmark.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        for (JRadioButton rb : radioButton) {
            panel.add(rb);
        }

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnNext);
        btnPanel.add(btnBookmark);

        add(panel, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        setQuestion();

        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void setQuestion() {
        bg.clearSelection();
        label.setText("Q" + (current + 1) + ": " + questions[current]);
        for (int i = 0; i < 4; i++) {
            radioButton[i].setText(options[current][i]);
        }
    }

    boolean checkAnswer() {
        return radioButton[answers[current]].isSelected();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNext) {
            if (checkAnswer()) count++;
            current++;
            if (current < questions.length) {
                setQuestion();
                if (current == questions.length - 1) {
                    btnNext.setEnabled(false);
                    btnBookmark.setText("Result");
                }
            }
        } else if (e.getSource() == btnBookmark) {
            if (btnBookmark.getText().equals("Result")) {
                if (checkAnswer()) count++;
                JOptionPane.showMessageDialog(this, "Correct answers: " + count);
                dispose();
            } else {
                bookmarks[x] = new JButton("Bookmark" + x);
                bookmarks[x].setBounds(480, 20 + 30 * x, 100, 30);
                add(bookmarks[x]);
                bookmarks[x].addActionListener(this);
                m[x] = current;
                x++;
                current++;
                if (current < questions.length) setQuestion();
                if (current == questions.length - 1) {
                    btnNext.setEnabled(false);
                    btnBookmark.setText("Result");
                }
                revalidate();
                repaint();
            }
        } else {
            for (int i = 1; i < x; i++) {
                if (e.getSource() == bookmarks[i]) {
                    if (checkAnswer()) count++;
                    now = current;
                    current = m[i];
                    setQuestion();
                    bookmarks[i].setEnabled(false);
                    current = now;
                }
            }
        }
    }

    public static void main(String[] args) {
        new OnlineTestRefactored("Online Test App");
    }
}
