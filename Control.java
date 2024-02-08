import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.*;
import java.beans.*;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public interface Control{
    final int REQUIREMENT = 5;

    //Activation du bouton "solo" permettant l'affichage de l'écran en mode solo
    public static void enableSolo(DactyloGame game, JButton solo){
        solo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.soloScreen();
            }
        });
    }

    //Activation du bouton "retour" permettant de retourner à l'écran précédent selon où le bouton retour se trouve
    public static void enableRetour(DactyloGame game, JButton retour, int config){
        retour.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(config == 1){
                    game.mainScreen();
                }else if(config == 2){
                    game.soloScreen();
                }
            }
        });
    }

    //Activation du bouton "normal" permettant l'affichage de l'écran en mode normal, en solo
    public static void enableNormal(DactyloGame game, JButton normal){
        normal.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.normalScreen();
            }
        });
    }

    //Activation du bouton "jeu" permettant l'affichage de l'écran en mode jeu, en solo
    public static void enableJeu(DactyloGame game, JButton jeu){
        jeu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                game.jeuScreen();
            }
        });
    }

    //Action des boutons de choix du temps dans le mode normal en solo : boutons 30s, 60s et 120s
    //Après choix du bouton, le timer est réglé au temps mentionné sur le bouton.
    //On peut toujours changer du moment que le bouton start n'est pas cliqué.
    public static void selectTimer(JButton first, JButton second, JButton third, JLabel timer){
        first.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                first.setEnabled(false);
                second.setEnabled(true);
                third.setEnabled(true);
                timer.setText(first.getText());
            }
        });
        second.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                first.setEnabled(true);
                second.setEnabled(false);
                third.setEnabled(true);
                timer.setText(second.getText());
            }
        });
        third.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                first.setEnabled(true);
                second.setEnabled(true);
                third.setEnabled(false);
                timer.setText(third.getText());
            }
        });
    }
    
    //Mise à jour du texte affiché durant la partie (suppression d'un mot, ajout d'un mot etc..)
    public static void updateText(Game partie, StyledDocument doc, JTextPane wordsToType){
        if(partie instanceof Normal){
            partie.removeAndUpdate();
            SimpleAttributeSet style = new SimpleAttributeSet();
            StyleConstants.setForeground(style, new Color(162,162,162));
            try {
                doc.remove(0, wordsToType.getCaretPosition()+1);
                doc.insertString(doc.getLength(), " "+partie.getWords().get(partie.getWords().size()-1), style); 
            }catch (BadLocationException e){}
        }else if(partie instanceof Jeu){
            try {
                List<String> words = partie.getWords();
                doc.remove(0, wordsToType.getCaretPosition()+1);
                words.remove(0);
                partie.setWords(words);
            }catch (BadLocationException e){}
        }
        wordsToType.setCaretPosition(0);
    }

    //Ajout d'un keyListener sur la zone de texte permettant de regarder les entrées de l'utilisateur sur le clavier.
    //Cela permet de comparer les inputs et vérifier s'ils correspondent au texte affiché, similaire à "MonkeyType".
    public static void startTyping(Game partie, JTextPane wordsToType, JLabel score, JLabel level, JLabel vies, int timer){
        wordsToType.addKeyListener(new KeyAdapter() {
            String userInput = "";
            String currWord = "";
            int charUtiles = 0;
            int keyPressed = 0;
            int pts = 0;
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                StyledDocument doc = wordsToType.getStyledDocument();
                SimpleAttributeSet white = new SimpleAttributeSet();
                StyleConstants.setForeground(white, Color.WHITE);
                SimpleAttributeSet blue = new SimpleAttributeSet();
                StyleConstants.setForeground(blue, new Color(80,191,255));
                SimpleAttributeSet red = new SimpleAttributeSet();
                StyleConstants.setForeground(red, Color.RED);

                //Si l'input est un caractère et que le timer ou les vies sont > 0 
                //alors on regarde ce caractère et le compare au caractère courant dans la zone de texte
                if((Character.isAlphabetic(c) || c == 32) && (timer > 0 || partie.hasLives())){
                    keyPressed++;
                    partie.setKeyPressed(keyPressed);
                    try{
                        //Si le caractère est différent d'espace (input et texte), 
                        //on compare l'input au caractère courant pour voir s'il est valide ou non,
                        //s'il est valide il devient blanc, sinon rouge
                        if(c!=32 && doc.getText(wordsToType.getCaretPosition(), 1).charAt(0)!=32){
                            partie.setUserInput(userInput += c);
                            currWord += doc.getText(wordsToType.getCaretPosition(), 1).charAt(0);
                            if (c == doc.getText(wordsToType.getCaretPosition(), 1).charAt(0)) {
                                Element el = doc.getCharacterElement(wordsToType.getCaretPosition());
                                AttributeSet attributes = el.getAttributes();
                                Color color = (Color) attributes.getAttribute(StyleConstants.Foreground);
                                if(color.equals(new Color(96,134,156))){
                                    doc.remove(wordsToType.getCaretPosition(), 1);
                                    doc.insertString(wordsToType.getCaretPosition(), String.valueOf(c), blue);
                                }else{
                                    doc.remove(wordsToType.getCaretPosition(), 1);
                                    doc.insertString(wordsToType.getCaretPosition(), String.valueOf(c), white);
                                }
                                partie.setScore(pts += 2);
                                partie.setLives(partie.getLives());
                                score.setText("Score : " + pts);
                            } else {
                                doc.remove(wordsToType.getCaretPosition(), 1);
                                doc.insertString(wordsToType.getCaretPosition(), String.valueOf(c), red);
                                partie.setScore(pts -= 2);
                                if(partie instanceof Jeu){
                                    partie.setLives(partie.getLives()-1);
                                    vies.setText("Vies : " + partie.getLives());
                                }
                                if(pts < 0){
                                    partie.setScore(pts = 0);
                                }
                                score.setText("Score : " + pts);
                            }
                            //Maintenant si l'input est espace et que le caractère courant est aussi espace, 
                            //on valide le mot si tous les caractères correspondent et on incrémente le score etc..
                            //Dans tous les cas, le mot est supprimé.
                        }else if(c == 32 && doc.getText(wordsToType.getCaretPosition(), 1).charAt(0) == 32){
                            if(userInput.equals(currWord)){
                                Element el = doc.getCharacterElement(wordsToType.getCaretPosition()-1);
                                AttributeSet attributes = el.getAttributes();
                                Color color = (Color) attributes.getAttribute(StyleConstants.Foreground);
                                if(color.equals(new Color(80,191,255))){
                                    partie.setLives(partie.getLives()+currWord.length());
                                    vies.setText("Vies : " + partie.getLives());
                                }
                                charUtiles += currWord.length();
                                partie.setCorrectWords(partie.getCorrectWords()+1);
                                if(partie.getCorrectWords() >= REQUIREMENT && partie instanceof Jeu){
                                    partie.setLevel(partie.getLevel()+1);
                                    level.setText("Niveau : " + partie.getLevel());
                                }
                                partie.setCharUtiles(charUtiles);
                                partie.setScore(pts += 5);
                                score.setText("Score : " + pts);
                            }
                            updateText(partie, doc, wordsToType);
                            userInput = "";
                            currWord = "";
                        }
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    //Activation des spécificités du mode normal en solo, notamment le Temps.
    public static void enableNormalGameSpecs(DactyloGame game, Game partie, JPanel menu, JLabel timer){
        Component[] componentList = menu.getComponents();
        for(Component c : componentList){
            if(c instanceof JButton){
                menu.remove(c);
            }
        }
        menu.add(timer);
        DactyloGame.updateComponent(menu);
        Timer t = new Timer(1000, new ActionListener(){
            int count = Integer.valueOf(timer.getText());
            public void actionPerformed(ActionEvent e){
                count--;
                timer.setText(Integer.toString(count));
                if(count == 0){
                    ((Timer)e.getSource()).stop();
                    partie.stats(partie.getTimeLimit());
                    int[] stats = partie.getStats();
                    game.showStats(stats, partie);
                }
            }
        });
        t.start();
    }

    //20% de chance de convertir un mot ajouté en mot bonus dans le mode jeu en solo.
    public static void chanceOfBonus(String newWord, StyledDocument doc, SimpleAttributeSet style){
        Random rand = new Random();
        double probability = 0.2;  // 20% chance of getting true
        boolean randomBoolean = rand.nextDouble() < probability;
        try{
            if(randomBoolean){
                StyleConstants.setForeground(style, new Color(96,134,156));
                doc.insertString(doc.getLength(), " "+newWord, style);
            }else{
                StyleConstants.setForeground(style, new Color(162,162,162));
                doc.insertString(doc.getLength(), " "+newWord, style);
            }
        }catch (BadLocationException ex){}
    }

    //Activation des spécificités du mode jeu en solo notamment les vies, l'apparition des mots selon un temps et le niveau.
    public static void enableJeuGameSpecs(DactyloGame game, Game partie, JTextPane wordsToType, JPanel menu, JLabel score, JLabel vies){
        double f = 3 * Math.pow(0.9, partie.getLevel());
        Timer t = new Timer((int)f * 1000, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                StyledDocument doc = wordsToType.getStyledDocument();
                SimpleAttributeSet style = new SimpleAttributeSet();
                StyleConstants.setForeground(style, new Color(162,162,162));
                try {
                    if(partie.getCorrectWords() >= REQUIREMENT){
                        partie.setCorrectWords(0);
                        ((Timer)e.getSource()).setDelay((int)(3*Math.pow(0.9, partie.getLevel())) * 1000);
                    }
                    if(partie.getWords().size() == 15){
                        doc.remove(0, partie.getWords().get(0).length()+1);
                        String userInput = partie.getUserInput();
                        if(!userInput.equals(partie.getWords().get(0))){
                            int removePts = (partie.getWords().get(0).length() - userInput.length());
                            int newLives = partie.getLives() - removePts;
                            int newScore = partie.getScore() - removePts;
                            if(newScore < 0){
                                partie.setScore(newScore = 0);
                            }else{
                                partie.setScore(newScore);
                            }
                            if(newLives < 0){
                                partie.setLives(newLives = 0);
                            }else{
                                partie.setLives(newLives);
                            }
                            score.setText("Score : " + newScore);
                            vies.setText("Vies : " + newLives);
                            game.updateComponent(menu);
                            partie.setUserInput("");
                        }
                    }
                    partie.removeAndUpdate();
                    String newWord = partie.getWords().get(partie.getWords().size()-1);
                    chanceOfBonus(newWord, doc, style); 
                }catch (BadLocationException ex){}
            }
        });
        t.start();
        vies.addPropertyChangeListener("text", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                if(partie.getLives() == 0){
                    t.stop();
                    partie.stats(0);
                    int[] stats = partie.getStats();
                    vies.setText("Vies : " + partie.getLives());
                    score.setText("Score : " + partie.getScore());
                    game.updateComponent(menu);
                    game.showStats(stats, partie);
                }
            }
        });
    }

    //Activation du bouton start à l'écran du mode de jeu choisi, 
    //cela permet de commencer la partie en autorisant la lecture de l'input de utilisateur, en activant le système de temps/niveau/vies etc..
    public static void enableStart(DactyloGame game, Game partie, JButton start, JTextPane wordsToType, JPanel menu, JLabel score, JLabel level, JLabel vies, JLabel timer){
        if(timer != null){
            partie.setTimeLimit(Integer.valueOf(timer.getText()));
        }
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(partie instanceof Normal){
                    enableNormalGameSpecs(game, partie, menu, timer);
                }else if(partie instanceof Jeu){
                    enableJeuGameSpecs(game, partie, wordsToType, menu, score, vies);
                }
                wordsToType.setCaretPosition(0);
                wordsToType.setCaretColor(Color.white);
                wordsToType.getCaret().setVisible(true);
                wordsToType.setFocusTraversalKeysEnabled(false);
                wordsToType.setFocusable(true);
                wordsToType.requestFocus();
                if(timer!=null){
                    startTyping(partie, wordsToType, score, level, vies, Integer.valueOf(timer.getText()));
                }else{
                    startTyping(partie, wordsToType, score, level, vies, 0);
                }
                start.setEnabled(false);
            }
        });
    }
}