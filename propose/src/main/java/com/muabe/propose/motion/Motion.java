package com.muabe.propose.motion;

import com.muabe.combination.combiner.ActionCombiner;
import com.muabe.propose.player.Player;

public class Motion extends ActionCombiner<ActionPlugin> {
    Player player;

    public ActionCombiner setPlayer(Player player){
        this.player = player;
        return this;
    }

    public Player getPlayer(){
        return player;
    }

    public Motion(ActionPlugin gesturePlugin) {
        super(gesturePlugin);
    }

    public boolean filter(Object event) {
        return super.filter(event);
    }
}