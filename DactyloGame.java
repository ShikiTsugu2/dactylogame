import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class DactyloGame extends JFrame{
    private final int width,height;
    private JPanel screen = new JPanel();

    //Toute cette partie est liée à la création de la fenêtre du jeu
    private DactyloGame(int w, int h) {
        width = w;
        height = h;
        getContentPane().add(screen);
        setSize(width, height);
        setTitle("Dactylo Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static class DactyloGameBuilder{
        private int width,height;
        private DactyloGameBuilder initSize(int w, int h){
            width = w;
            height = h;
            return this;
        }

        private DactyloGame build(){
            return new DactyloGame(width, height);
        }
    }

    public JPanel getScreen(){
        return screen;
    }

    //Affichage du menu principal
    public void mainScreen(){
        int config = 0;
        screen.removeAll();

        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setBackground(new Color(54,54,54));

        JLabel title = new JLabel("Dactylo Game");
        title.setFont(new Font("Arial", Font.PLAIN, 80));
        title.setForeground(new Color(229,212,167));
        title.setBorder(BorderFactory.createEmptyBorder(200,0,50,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        screen.add(title);

        JButton solo = new JButton("SOLO");
        solo.setFont(new Font("Arial", Font.BOLD, 20));
        solo.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableSolo(this, solo);
        screen.add(solo);
        screen.add(Box.createVerticalStrut(40));

        JButton multi = new JButton("MULTIPLAYER");
        multi.setFont(new Font("Arial", Font.BOLD, 20));
        multi.setAlignmentX(Component.CENTER_ALIGNMENT);
        screen.add(multi);

        updateComponent(screen);
    }

    //Affichage de l'écran de sélection des modes en solo
    public void soloScreen(){
        int config = 1;
        screen.removeAll();

        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setBackground(new Color(54,54,54));

        JLabel title = new JLabel("SOLO");
        title.setFont(new Font("Arial", Font.PLAIN, 80));
        title.setForeground(new Color(229,212,167));
        title.setBorder(BorderFactory.createEmptyBorder(200,0,50,0));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        screen.add(title);

        JButton normal = new JButton("MODE NORMAL");
        normal.setFont(new Font("Arial", Font.BOLD, 20));
        normal.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableNormal(this, normal);
        screen.add(normal);
        screen.add(Box.createVerticalStrut(20));

        JButton jeu = new JButton("MODE JEU");
        jeu.setFont(new Font("Arial", Font.BOLD, 20));
        jeu.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableJeu(this, jeu);
        screen.add(jeu);
        screen.add(Box.createVerticalStrut(60));

        JButton retour = new JButton("RETOUR");
        retour.setFont(new Font("Arial", Font.BOLD, 20));
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableRetour(this, retour, config);
        screen.add(retour);

        updateComponent(screen);
    }

    //Affichage du mode normal en solo
    public void normalScreen(){
        int config = 2;
        Game normal = Normal.initGame();
        List<String> words = normal.getWords();
        screen.removeAll();
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setBackground(new Color(54,54,54));

        JPanel menu = new JPanel(new FlowLayout());
        menu.setOpaque(false);
        JLabel score = new JLabel("Score : 0");
        score.setFont(new Font("Arial", Font.PLAIN, 30));
        score.setForeground(new Color(229,212,167));

        JLabel time = new JLabel("Time :");
        time.setFont(new Font("Arial", Font.PLAIN, 30));
        time.setForeground(new Color(229,212,167));

        JButton first = new JButton("30");
        first.setFont(new Font("Arial", Font.BOLD, 20));
        first.setEnabled(false);
        JButton second = new JButton("60");
        second.setFont(new Font("Arial", Font.BOLD, 20));
        JButton third = new JButton("120");
        third.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel timer = new JLabel("30");
        timer.setFont(new Font("Arial", Font.PLAIN, 30));
        timer.setForeground(new Color(229,212,167));

        menu.add(score);
        menu.add(Box.createHorizontalStrut(100));
        menu.add(time);
        menu.add(Box.createHorizontalStrut(10));
        menu.add(first);
        menu.add(Box.createHorizontalStrut(10));
        menu.add(second);
        menu.add(Box.createHorizontalStrut(10));
        menu.add(third);
        Control.selectTimer(first, second, third, timer);

        screen.add(Box.createVerticalStrut(200));
        screen.add(menu);

        JTextPane wordsToType = new JTextPane();
        wordsToType.setText(String.join(" ", words));
        wordsToType.setEditable(false);
        wordsToType.setOpaque(false);
        wordsToType.setFont(new Font("Arial", Font.PLAIN, 30));
        wordsToType.setForeground(new Color(162,162,162));

        StyledDocument doc = wordsToType.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        wordsToType.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        screen.add(Box.createVerticalStrut(50));
        screen.add(wordsToType);

        JButton start = new JButton("START");
        start.setFont(new Font("Arial", Font.BOLD, 20));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableStart(this, normal, start, wordsToType, menu, score, null, null, timer);
        screen.add(start);
        screen.add(Box.createVerticalStrut(20));

        JButton retour = new JButton("RETOUR");
        retour.setFont(new Font("Arial", Font.BOLD, 20));
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableRetour(this, retour, config);
        screen.add(retour);
        screen.add(Box.createVerticalStrut(120));

        updateComponent(screen);
    }
    
    //Génération d'un mot bonus pour le mode Jeu en solo
    public void setRandomWordBonus(List<String> words, JTextPane wordsToType, StyledDocument doc){
        Random rand = new Random();
        int index = rand.nextInt(words.size());
        String randWord = words.get(index);
        String s = wordsToType.getText();
        int i = s.indexOf(randWord);
        try{
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(96,134,156));
            doc.remove(i, randWord.length());
            doc.insertString(i, randWord, style);
        }catch (BadLocationException e){
            System.out.println("Not Found");
        }
    }

    //Affichage du mode Jeu en solo
    public void jeuScreen(){
        int config = 2;
        Game jeu = Jeu.initGame();
        List<String> words = jeu.getWords();
        screen.removeAll();
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        screen.setBackground(new Color(54,54,54));

        JPanel menu = new JPanel(new FlowLayout());
        menu.setOpaque(false);
        JLabel score = new JLabel("Score : 0");
        score.setFont(new Font("Arial", Font.PLAIN, 30));
        score.setForeground(new Color(229,212,167));

        JLabel level = new JLabel("Niveau : 0");
        level.setFont(new Font("Arial", Font.PLAIN, 30));
        level.setForeground(new Color(229,212,167));

        JLabel vies = new JLabel("Vies : " + jeu.getLives());
        vies.setFont(new Font("Arial", Font.PLAIN, 30));
        vies.setForeground(new Color(229,212,167));
        menu.add(score);
        menu.add(Box.createHorizontalStrut(100));
        menu.add(level);
        menu.add(Box.createHorizontalStrut(100));
        menu.add(vies);

        screen.add(Box.createVerticalStrut(200));
        screen.add(menu);

        JTextPane wordsToType = new JTextPane();
        wordsToType.setText(String.join(" ", words));
        wordsToType.setEditable(false);
        wordsToType.setOpaque(false);
        wordsToType.setFont(new Font("Arial", Font.PLAIN, 30));
        wordsToType.setForeground(new Color(162,162,162));

        StyledDocument doc = wordsToType.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        setRandomWordBonus(words, wordsToType, doc);

        wordsToType.setBorder(BorderFactory.createEmptyBorder(0,100,0,100));
        screen.add(Box.createVerticalStrut(50));
        screen.add(wordsToType);

        JButton start = new JButton("START");
        start.setFont(new Font("Arial", Font.BOLD, 20));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableStart(this, jeu, start, wordsToType, menu, score, level, vies, null);
        screen.add(start);
        screen.add(Box.createVerticalStrut(20));

        JButton retour = new JButton("RETOUR");
        retour.setFont(new Font("Arial", Font.BOLD, 20));
        retour.setAlignmentX(Component.CENTER_ALIGNMENT);
        Control.enableRetour(this, retour, config);
        screen.add(retour);
        screen.add(Box.createVerticalStrut(120));

        updateComponent(screen);
    }

    //Affichage des statistiques à la fin d'une partie
    public void showStats(int[] stats, Game partie){
        String msg = "";
        if(partie instanceof Normal){
            msg = "Vitesse : " + stats[0] + " MPM, Précision : " + stats[1] + " %, Score : " + stats[2];
        }else if(partie instanceof Jeu){
            msg = "Niveau : " + stats[0] + ", Score : " + stats[1] + ", Précision : " + stats[2] + " %";
        }
        JOptionPane d = new JOptionPane();
        String[] options = {"REJOUER", "RETOUR"};
        int x = d.showOptionDialog(screen, msg, "Stats", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        switch (x) {
        case 0:
            if(partie instanceof Normal){
                normalScreen();
                d.getRootFrame().dispose();
            }else if(partie instanceof Jeu){
                jeuScreen();
                d.getRootFrame().dispose();
            }
            break;
        case 1:
            soloScreen();
            d.getRootFrame().dispose();
            break;
        }
    }

    //Mise à jour d'un "component"
    public static void updateComponent(Component c){
        c.revalidate();
        c.repaint();
    }

    //Le main..
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                DactyloGameBuilder dactyloGameBuilder = new DactyloGameBuilder().initSize(1200,800);
                DactyloGame dactyloGame = dactyloGameBuilder.build();
                dactyloGame.mainScreen();
                dactyloGame.setVisible(true);
            }
        });
    }
}