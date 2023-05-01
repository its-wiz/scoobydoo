package org.itsrune.scoobydoo.Checks;

import org.itsrune.scoobydoo.Misc;

public class Check {
    public Misc.CheckType type;
    public boolean enabled;

    public Check(Misc.CheckType type, boolean enabled){
       this.type = type;
       this.enabled = enabled;
    }


}
