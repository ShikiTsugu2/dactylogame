import java.util.Random;
import java.util.concurrent.TimeUnit;

//le mode jeu en solo
public class Jeu extends Game{
    private Jeu(){
        initWords();
        lives = 10;
        level = 0;
    }

    //initialise un texte de 10 mots aléatoires
    public void initWords(){
        for(int i = 0; i<10; i++){
            Random rand = new Random();
            int index = rand.nextInt(dictionnary.size());
            words.add(dictionnary.get(index));
        }
    }

    public static Jeu initGame(){
        return new Jeu();
    }

    public void setTimeLimit(int lim){}

    public int getTimeLimit(){
        return 0;
    }

    public int getLives(){
        return lives;
    }

    public void setLives(int l){
        lives = l;
    }

    public boolean hasLives(){
        return lives>0;
    }

    public int getLevel(){
        return level;
    }

    public void setLevel(int l){
        level = l;
    }

    //statistiques de fin de jeu : niveau atteint, score et la précision en %
    public void stats(int time){
        stats[0] = level;
        if(score < 0){
            score = 0;
        }
        stats[1] = score;
        stats[2] = (int)Math.ceil(((double)charUtiles/keyPressed)*100);
    }

    //ajoute un mot à la fin, si la taille du texte est de 15 mots, on enlève en plus le premier mot
    public void removeAndUpdate(){
        if(words.size() == 15){
            words.remove(0);
        }
        Random rand = new Random();
        int index = rand.nextInt(dictionnary.size());
        words.add(dictionnary.get(index));
    }
}