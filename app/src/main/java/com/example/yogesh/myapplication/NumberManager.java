package com.example.yogesh.myapplication;

import android.app.Activity;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

class NumberManager extends Helper{
    private String msg = "NumberManager Class ::";
    Activity myActivity;
    public ArrayList<Integer> randomNumberArr=new ArrayList<>();
    public ArrayList<Integer> visibleNumberArr=new ArrayList<>();
    public int answerArr[]=new int[3];
    Map<String, Integer> variablesMap = new Hashtable<>(   );
    GameTimer gameTimer;

    public NumberManager(Activity myActivity,GameTimer gameTimer) {
        this.myActivity = myActivity;
        this.gameTimer=gameTimer;
        createAllNumbersArr();
        variablesMap.put("wrongAns",0);
        variablesMap.put("correctAns",0);
    }

    public boolean createAllNumbersArr(){
        randomNumberArr.clear();
        visibleNumberArr.clear();

        for(int i=0; i<99;i++) {
            randomNumberArr.add(i);
        }
        Collections.shuffle(randomNumberArr);
        for(int i=0; i<10;i++) {
            visibleNumberArr.add(randomNumberArr.get(0));
            randomNumberArr.remove(0);
        }
        answerArr[0]=visibleNumberArr.get(7);
        answerArr[1]=visibleNumberArr.get(2);
        answerArr[2]=visibleNumberArr.get(4);
        Log.d(msg, "randomNumberArrr = "+randomNumberArr.toString());
        return true;
    }

    public void assignNumbers(){
        Log.d(msg, "assignNumbers :");
        for(int i = 0; i<=8;i++) {
            buttonShowText("a"+i,visibleNumberArr.get(i).toString(),myActivity);

        }
        setNewAnswerButtonTxt(1);
        setNewAnswerButtonTxt(2);
        setNewAnswerButtonTxt(3);
    }

    public void setNewAnswerButtonTxt(int i) {
        Log.d(msg, "setNewAnswer : ");
        Random myRandom = new Random();

        //Log.d(msg, "setNewAnswer : answersList.size :" + answersList.size());
        Log.d(msg,"Answer Array===="+ visibleNumberArr.toString());
        ArrayList<Integer> tempVisibleArr = (ArrayList<Integer>) visibleNumberArr.clone();
        if(i!=3) {
            tempVisibleArr.remove(tempVisibleArr.indexOf(answerArr[2]));
        }
        if(i!=2){
            tempVisibleArr.remove(tempVisibleArr.indexOf(answerArr[1]));
        }
        if(i!=1) {
            tempVisibleArr.remove(tempVisibleArr.indexOf(answerArr[0]));
        }
        Log.d(msg,"i="+i+"  Answer Array===="+ tempVisibleArr.toString());

        switch(i) {
            case 3:
                answerArr[2]=tempVisibleArr.get(myRandom.nextInt(6));
                textViewShowText(R.id.ans3, Integer.toString(answerArr[2]), myActivity);
                break;
            case 2:
                answerArr[1]=tempVisibleArr.get(myRandom.nextInt(6));
                textViewShowText(R.id.ans2, Integer.toString(answerArr[1]), myActivity);
                break;
            case 1:
                answerArr[0]=tempVisibleArr.get(myRandom.nextInt(6));
                textViewShowText(R.id.ans1, Integer.toString(answerArr[0]), myActivity);
                break;
        }
    }


    public void checkMyAnswer(View v){
        Button answerButton = (Button)v;
        String buttonText = answerButton.getText().toString();

         Log.d(msg, "Check my Answer ==== gameTimer.countDown="+gameTimer.countDown);
        if(gameTimer.countDown<=0)
            return;
        Log.d(msg, "checkMyAnswer : ");
        if(buttonText.equals(Integer.toString(answerArr[0]))){
            int a = Integer.parseInt(answerButton.getTag().toString()) ;
            visibleNumberArr.set(a,randomNumberArr.get(0));
            randomNumberArr.remove(0);
            answerButton.setText(visibleNumberArr.get(a).toString());
            setNewAnswerButtonTxt(1);
            variablesMap.put("correctAns",(int)variablesMap.get("correctAns")+1);
        }
        else if(buttonText.equals(Integer.toString(answerArr[1]))){
            int a = Integer.parseInt(answerButton.getTag().toString()) ;
            visibleNumberArr.set(a,randomNumberArr.get(0));
            randomNumberArr.remove(0);
            answerButton.setText(visibleNumberArr.get(a).toString());
            setNewAnswerButtonTxt(2);
            variablesMap.put("correctAns",(int)variablesMap.get("correctAns")+1);
        }
        else if(buttonText.equals(Integer.toString(answerArr[2]))){
            int a = Integer.parseInt(answerButton.getTag().toString()) ;
            visibleNumberArr.set(a,randomNumberArr.get(0));
            randomNumberArr.remove(0);
            answerButton.setText(visibleNumberArr.get(a).toString());
            setNewAnswerButtonTxt(3);
            variablesMap.put("correctAns",(int)variablesMap.get("correctAns")+1);

        }
        else{
            Collections.shuffle(visibleNumberArr);
            for(int i = 0; i<=8;i++) {
                buttonShowText("a"+i,visibleNumberArr.get(i).toString(),myActivity);
            }
            gameTimer.countDown-=2000;
            gameTimer.progressInt+=1.65*2;
            variablesMap.put("wrongAns", (int)variablesMap.get("wrongAns")+1);

            Vibrator vib= (Vibrator) myActivity.getSystemService(VIBRATOR_SERVICE);
            vib.vibrate(200);
        }

        textViewShowText(R.id.correctAns,"" +variablesMap.get("correctAns"),myActivity);
        textViewShowText(R.id.wrongAns,"" + variablesMap.get("wrongAns"),myActivity);

        Log.d(msg, "End====");

    }
}
