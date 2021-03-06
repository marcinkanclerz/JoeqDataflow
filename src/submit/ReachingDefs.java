package submit;

// some useful things to import. add any additional imports you need.
import java.util.*;
import joeq.Compiler.Quad.*;
import joeq.Compiler.Quad.Operand.*;
import flow.Flow;

/**
 * Skeleton class for implementing a reaching definition analysis
 * using the Flow.Analysis interface.
 */
public class ReachingDefs implements Flow.Analysis {

    /**
     * Class for the dataflow objects in the ReachingDefs analysis.
     * You are free to change this class or move it to another file.
     */
    public static class DefSet implements Flow.DataflowObject {
        // Maps definitions(int) and their existence
        private boolean[] defExistsMap;

        // Maps definitions(int) and corresponding var(String)
        private static String[] defVarMap;
        private static void defVarInit(int size){
            defVarMap = new String[size];
            for (int i = 0; i < defVarMap.length; i++) {
                defVarMap[i] = "";
            }
        }
        private static void defVarSetmap(int def, String var){
            defVarMap[def] = var;
        }

        // Constructor
        public DefSet() {
            defExistsMap = new boolean[defVarMap.length];
        }

        /**
         * Methods from the Flow.DataflowObject interface.
         * See Flow.java for the meaning of these methods.
         * These need to be filled in.
         */
        public void setToTop() {
            for(int i=0; i<defExistsMap.length; i++) {
                defExistsMap[i] = false;
            }
        }
        public void setToBottom() {
            for(int i=0; i<defExistsMap.length; i++) {
                defExistsMap[i] = true;
            }  
        }
        public void meetWith (Flow.DataflowObject o) {
            DefSet a = (DefSet) o;
            for (int i=0; i<defExistsMap.length; i++) {
                defExistsMap[i] |= a.defExistsMap[i];
            }
        }
        public void copy (Flow.DataflowObject o) {
            DefSet a = (DefSet) o;
            for (int i=0; i<defExistsMap.length; i++) {
                defExistsMap[i] = a.defExistsMap[i];
            }
        }
        public void applyKill (String killVar) {
            //System.out.println(killVar);
            for (int i=0; i<defVarMap.length; i++) {
                if(defVarMap[i].equals(killVar)) defExistsMap[i] = false;
            }
        }
        public void applyGen (int genDef) {
            defExistsMap[genDef] = true; 
        }

        /**
         * toString() method for the dataflow objects which is used
         * by postprocess() below.  The format of this method must
         * be of the form "[ID0, ID1, ID2, ...]", where each ID is
         * the identifier of a quad defining some register, and the
         * list of IDs must be sorted.  See src/test/test.rd.out
         * for example output of the analysis.  The output format of
         * your reaching definitions analysis must match this exactly.
         */
        @Override
        public String toString() { 
            String output = "[";
            for (int i=0; i<defExistsMap.length; i++) {
                if(defExistsMap[i]){
                    output+=i;  // Debug: defVarMap[i]
                    output+=", ";
                }
            }
            if(output.length() > 1) output = output.substring(0, output.length()-2); //remove the last ", "
            output += "]";
            return output;
        }

        @Override
        public boolean equals (Object o) {
            if (o instanceof DefSet) {
                return  Arrays.equals(((DefSet)o).defExistsMap,defExistsMap);
            }
            return false;
        }
    }

    /**
     * Dataflow objects for the interior and entry/exit points
     * of the CFG. in[ID] and out[ID] store the entry and exit
     * state for the input and output of the quad with identifier ID.
     *
     * You are free to modify these fields, just make sure to
     * preserve the data printed by postprocess(), which relies on these.
     */
    private DefSet[] in, out;
    private DefSet entry, exit;

    /**
     * This method initializes the datflow framework.
     *
     * @param cfg  The control flow graph we are going to process.
     */
    public void preprocess(ControlFlowGraph cfg) {
        // this line must come first.
        System.out.println("Method: "+cfg.getMethod().getName().toString());

        // get the amount of space we need to allocate for the in/out arrays.
        QuadIterator qit = new QuadIterator(cfg);
        int max = 0;
        while (qit.hasNext()) {
            int id = qit.next().getID();
            if (id > max) 
                max = id;
        }
        max += 1;

        // set up size of tracking vector in DefSet defn.<=>var
        // has to be done before new Defset() is called because
        // defn.<=>exists vector is set up based on defn.<=>var size
        DefSet.defVarInit(max);

        // allocate the in and out arrays.
        in = new DefSet[max];
        out = new DefSet[max];

        //System.out.println(in.length);

        // initialize the contents of in and out.
        qit = new QuadIterator(cfg);
        while (qit.hasNext()) {
            Quad q = qit.next();
            int id = q.getID();
            //System.out.println(id);
            in[id] = new DefSet();
            out[id] = new DefSet();
            // map up the contents of defVarMap while at it
            String defVar = "";
            for (RegisterOperand def : q.getDefinedRegisters()) {
                defVar = def.getRegister().toString();
            } 
            DefSet.defVarSetmap(id,defVar);
        }

        // initialize the entry and exit points.
        entry = new DefSet();
        exit = new DefSet();

        /************************************************
         * Your remaining initialization code goes here *
         ************************************************/
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
        for (int i=0; i<in.length; i++) {
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
    public boolean isForward () { return true; }
    public Flow.DataflowObject getEntry() { 
        Flow.DataflowObject result = newTempVar();
        result.copy(entry); 
        return result;
    }
    public Flow.DataflowObject getExit() { 
        Flow.DataflowObject result = newTempVar();
        result.copy(exit); 
        return result;
    }
    public void setEntry(Flow.DataflowObject value) {
        entry.copy(value);
    }
    public void setExit(Flow.DataflowObject value) {
         exit.copy(value);
    }
    public Flow.DataflowObject getIn(Quad q) { 
        Flow.DataflowObject result = newTempVar();
        result.copy(in[q.getID()]); 
        return result;
    }
    public Flow.DataflowObject getOut(Quad q) { 
        Flow.DataflowObject result = newTempVar();
        result.copy(out[q.getID()]); 
        return result;
    }
    public void setIn(Quad q, Flow.DataflowObject value) {
        in[q.getID()].copy(value);
    }
    public void setOut(Quad q, Flow.DataflowObject value) {
        out[q.getID()].copy(value);
    }
    public Flow.DataflowObject newTempVar() { return new DefSet(); }

    public void processQuad(Quad q) {
        String defVar = "";
        for (RegisterOperand def : q.getDefinedRegisters()) {
            defVar = def.getRegister().toString();
        } 
        int id = q.getID();

        out[id].copy(in[id]);       //out = x
        if(!defVar.equals("")){
            out[id].applyKill(defVar);  //out = x-kill
            out[id].applyGen(id);       //out = (x-kill)Ugen
        }
    }
}

