package submit;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import flow.Flow;

public class VarSet implements Flow.DataflowObject {
    private Set<String> set;
    public static Set<String> universalSet;
    public VarSet() { set = new TreeSet<String>(); }

    public void setToTop() { set = new TreeSet<String>(); }
    public void setToBottom() { 
    	set = new TreeSet<String>(universalSet); 
	}

    /**
     * Perform intersection operation.
     */
    public void meetWith(Flow.DataflowObject o) 
    {
    	this.intersectWithSet(o);
    }
    
    public void removeSet(Flow.DataflowObject o) 
    {
    	this.set.removeAll(((VarSet)o).set);
    }
    
    public void intersectWithSet(Flow.DataflowObject o) 
    {
    	VarSet a = (VarSet)o;
    	Iterator<String> it = this.set.iterator();
    	String s;
    	while (it.hasNext()) {
    		s = it.next();
    		if (!a.set.contains(s)) {
    			this.set.remove(s);
    		}
    	}
    }
    
    public void sumWithSet(Flow.DataflowObject o) 
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
     * TODO
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

    public void addVar(String v) 
    {
    	set.add(v);
	}
    
    public void removeVar(String v) 
    {
    	set.remove(v);
	}
    
    public boolean containsVar(String v)
    {
    	return set.contains(v);
    }
}
