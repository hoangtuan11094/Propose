package com.muabe.propose;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.action.TouchActionController;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.ScanResult;
import com.muabe.propose.combination.combiner.ActionModule;

import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-15
 */
public class Propose implements ActionModule.OnActionListener, View.OnTouchListener {
    public static final String ACTION_TOUCH = "ACTION_TOUCH";
    public static final String ACTION_MULTI_TOUCH = "ACTION_MULTI_TOUCH";

    private Context context;
    private HashMap<String, ActionModule> actionModules = new HashMap<>();
    private Motion motion;
    private History history;


    public Propose(Context context){
        this.context = context;
        defaultActionModule();

    }

    private void defaultActionModule(){
        addActionMudle(Propose.ACTION_TOUCH, new TouchActionController(context, false));
        addActionMudle(Propose.ACTION_MULTI_TOUCH, new TouchActionController(context, true));
    }

    public Context getContext(){
        return context;
    }

    public void addActionMudle(String name, ActionModule actionModule){
        actionModule.bind(this);
        actionModules.put(name, actionModule);
    }

    public void setMotion(Motion motion){
        this.motion = motion;
    }

    @Override
    public boolean onAction(Object event) {
        boolean result = false;
        ScanResult<Motion> scanResult = Combine.scan(motion, event);
        for(Motion deleteMotion : scanResult.getDeleteList()){
            deleteMotion.delMotion();
        }

        for(Motion scanMotion : scanResult.getScanList()){
            if(scanMotion.filter(event)){
                result = scanMotion.actMotion(event);
                if(result){

                }
            }
        }

        return result;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        TouchActionController touchAction = (TouchActionController) actionModules.get(Propose.ACTION_TOUCH);
        return touchAction.onTouch(view, motionEvent);
    }
}
