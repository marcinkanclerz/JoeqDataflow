package submit;

// some useful things to import. add any additional imports you need.
import java.util.Set;
import java.util.TreeSet;

import joeq.Compiler.Quad.*;
import joeq.Compiler.Quad.Operand.RegisterOperand;
import joeq.Main.Helper;
import flow.Flow;

public class Faintness implements Flow.Analysis {

    /**
     * Dataflow objects for the interior and entry/exit points
     * of the CFG. in[ID] and out[ID] store the entry and exit
     * state for the input and output of the quad with identifier ID.
     *
     * You are free to modify these fields, just make sure to
     * preserve the data printed by postprocess(), which relies on these.
     */
    private VarSet[] in, out;
    private VarSet entry, exit;
    private FaintnessTransferFunction transferfn = new FaintnessTransferFunction();
    
    public void processQuad(Quad q) {
    	transferfn.val.copy(out[q.getID()]);
    	Helper.runPass(q, transferfn);
        in[q.getID()].copy(transferfn.val);
    }

    /**
     * This method initializes the datflow framework.
     *
     * @param cfg  The control flow graph we are going to process.
     */
    public void preprocess(ControlFlowGraph cfg) {
        // this line must come first.
        System.out.println("Method: " + cfg.getMethod().getName().toString());

        /* Generate initial conditions. */
        // get the amount of space we need to allocate for the in/out arrays.
        QuadIterator qit = new QuadIterator(cfg);
        int max = 0;
        while (qit.hasNext()) {
            int id = qit.next().getID();
            if (id > max) 
                max = id;
        }
        max += 1;

        // allocate the in and out arrays.
        in = new VarSet[max];
        out = new VarSet[max];
        
        /* Arguments are always there. */
        Set<String> s = new TreeSet<String>();
        VarSet.universalSet = s;
        
        int numargs = cfg.getMethod().getParamTypes().length;
        for (int i = 0; i < numargs; i++) {
            s.add("R"+i);
        }

        while (qit.hasNext()) {
            Quad q = qit.next();
            for (RegisterOperand def : q.getDefinedRegisters()) {
                s.add(def.getRegister().toString());
            }
            for (RegisterOperand use : q.getUsedRegisters()) {
                s.add(use.getRegister().toString());
            }
        }

        // initialize the contents of in and out.
        qit = new QuadIterator(cfg);
        while (qit.hasNext()) {
        	// TODO Is this proper initialization?
            int id = qit.next().getID();
            in[id] = new VarSet();
            in[id].setToBottom();
            out[id] = new VarSet();
            out[id].setToBottom();
        }

        // initialize the entry and exit points.
        entry = new VarSet();
        exit = new VarSet();
        exit.setToBottom();
        
        transferfn.val = new VarSet();
    }

    /**
     * This method is called after the fixpoint is reached.
     * It must print out the dataflow objects associated with
     * the entry, exit, and all interior points of the CFG.
     * Unless you modify in, out, entry, or exit you shouldn't
     * need to change this method.
     *
     * @param cfg  Unused.
     */
    public void postprocess (ControlFlowGraph cfg) {
        System.out.println("entry: " + entry.toString());
        for (int i=1; i<in.length; i++) {
            if (in[i] != null) {
                System.out.println(i + " in:  " + in[i].toString());
                System.out.println(i + " out: " + out[i].toString());
            }
        }
        System.out.println("exit: " + exit.toString());
    }

    /**
     * Other methods from the Flow.Analysis interface.
     * See Flow.java for the meaning of these methods.
     * These need to be filled in.
     */
    
    public boolean isForward() { 
    	return false; 
	}
    
    /* Routines for interacting with dataflow values. */

    public Flow.DataflowObject getEntry() 
    { 
        Flow.DataflowObject result = newTempVar();
        result.copy(entry); 
        return result;
    }
    public Flow.DataflowObject getExit() 
    { 
        Flow.DataflowObject result = newTempVar();
        result.copy(exit); 
        return result;
    }
    public Flow.DataflowObject getIn(Quad q) 
    {
        Flow.DataflowObject result = newTempVar();
        result.copy(in[q.getID()]); 
        return result;
    }
    public Flow.DataflowObject getOut(Quad q) 
    {
        Flow.DataflowObject result = newTempVar();
        result.copy(out[q.getID()]); 
        return result;
    }
    public void setIn(Quad q, Flow.DataflowObject value) 
    { 
        in[q.getID()].copy(value); 
    }
    public void setOut(Quad q, Flow.DataflowObject value) 
    { 
        out[q.getID()].copy(value); 
    }
    public void setEntry(Flow.DataflowObject value) 
    { 
        entry.copy(value); 
    }
    public void setExit(Flow.DataflowObject value) 
    { 
        exit.copy(value); 
    }

    public Flow.DataflowObject newTempVar() { 
    	return new VarSet(); 
	}
}
