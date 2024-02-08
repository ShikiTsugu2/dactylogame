import java.util.List;
import java.util.ArrayList;
import java.util.Random;

//le mode normal en solo
public class Normal extends Game{
    private int timeLimit;

    private Normal(){
        initWords();
        timeLimit = 30;
    }

    //Initialise un texte avec 15 mots aléatoires
    public void initWords(){
        for(int i = 0; i<15; i++){
            Random rand = new Random();
            int index = rand.nextInt(dictionnary.size());
            words.add(dictionnary.get(index));
        }
    }
    
    public void setTimeLimit(int lim){
        timeLimit = lim;
    }

    public int getTimeLimit(){
        return timeLimit;
    }

    public int getLives(){
        return 0;
    }

    public void setLives(int l){}

    public boolean hasLives(){
        return false;
    }

    public int getLevel(){
        return 0;
    }

    public void setLevel(int l){}

    public static Normal initGame(){
        return new Normal();
    }

    //les statistiques de fin de partie : la vitesse de tape en MPM, la précision en % et le score obtenu
    public void stats(int time){
        stats[0] = (int)(charUtiles/((double)time/60))/5;
        stats[1] = (int)Math.ceil(((double)charUtiles/keyPressed)*100);
        stats[2] = score;
    }

    //retire le premier mot et ajoute un nouveau à la fin
    public void removeAndUpdate(){
        words.remove(0);
        Random rand = new Random();
        int index = rand.nextInt(dictionnary.size());
        words.add(dictionnary.get(index));
    }
}