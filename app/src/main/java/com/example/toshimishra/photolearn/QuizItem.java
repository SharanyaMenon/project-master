package com.example.toshimishra.photolearn;

/**
 * Created by toshimishra on 06/03/18.
 */

public class QuizItem extends Item {

    private String question;
    private String itemID;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private boolean isOption1Ans;
    private boolean isOption2Ans;
    private boolean isOption3Ans;
    private boolean isOption4Ans;
    private String ansExp; //For explanation of the option

    public QuizItem(){
        super();
    }

    public QuizItem(String titleID,String itemID,String photoURL,String question, String option1,String option2,String option3,String option4,
                    boolean isOption1Ans, boolean isOption2Ans, boolean isOption3Ans, boolean isOption4Ans,String ansExp) {
        super(titleID, photoURL);
        this.itemID = itemID;

        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;

        this.isOption1Ans = isOption1Ans ;
        this.isOption2Ans = isOption2Ans ;
        this.isOption3Ans = isOption3Ans ;
        this.isOption4Ans = isOption4Ans ;

        this.ansExp = ansExp;
        this.question = question;

    }

    public String getQuestion(){
        return question;
    }

    public void updateQuizItem(String photoURL, String options[],boolean isOptionAns[],String ansExp){
        super.update(photoURL);
        this.ansExp = ansExp;
        int i = 0;
       /* for (String option : options) {
            this.options[i] = options[i];
            this.isOptionAns[i] = isOptionAns[i];
            i++;
        }*/

    }
}
