package submit;

import java.util.Set;
import java.util.TreeSet;
import flow.Flow;

public class VarSet implements Flow.DataflowObject {
    private Set<String> set;
    public static Set<String> universalSet;
    public VarSet() { set = new TreeSet<String>(); }

    public void setToTop() { set = new TreeSet<String>(); }
    public void setToBottom() { set = new TreeSet<String>(universalSet); }

    public void meetWith(Flow.DataflowObject o) 
    {
        VarSet a = (VarSet)o;
        set.addAll(a.set);
    }

    public void copy(Flow.DataflowObject o) 
    {
        VarSet a = (VarSet) o;
        set = new TreeSet<String>(a.set);
    }

    @Override
    public boolean equals(Object o) 
    {
        if (o instanceof VarSet) 
        {
            VarSet a = (VarSet) o;
            return set.equals(a.set);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return set.hashCode();
    }
    
    /**
     * toString() method for the dataflow objects which is used
     * by postprocess() below.  The format of this method must
     * be of the form "[REG0, REG1, REG2, ...]", where each REG is
     * the identifier of a register, and the list of REGs must be sorted.
     * See src/test/TestFaintness.out for example output of the analysis.
     * The output format of your reaching definitions analysis must
     * match this exactly.
     */
    @Override
    public String toString() 
    {
        return set.toString();
    }

    public void genVar(String v) {set.add(v);}
    public void killVar(String v) {set.remove(v);}
}
