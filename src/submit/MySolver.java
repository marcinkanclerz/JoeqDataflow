package submit;

import java.util.*;
import joeq.Compiler.Quad.*;
import flow.Flow;

/**
 * Skeleton class for implementing the Flow.Solver interface.
 */
public class MySolver implements Flow.Solver {

    protected Flow.Analysis analysis;

    /**
     * Sets the analysis.  When visitCFG is called, it will
     * perform this analysis on a given CFG.
     *
     * @param analyzer The analysis to run
     */
    public void registerAnalysis(Flow.Analysis analyzer) {
        this.analysis = analyzer;
    }

    /**
     * Runs the solver over a given control flow graph.  Prior
     * to calling this, an analysis must be registered using
     * registerAnalysis
     *
     * @param cfg The control flow graph to analyze.
     */
    public void visitCFG(ControlFlowGraph cfg) {

        // this needs to come first.
        analysis.preprocess(cfg);

        /**
         * TODO
         * - QuadIterator has constructor defining it's direction, is it applicable?
         * - Look for optimizations through JoeQ APIs.
         */


        if (analysis.isForward()) {
            QuadIterator qit = new QuadIterator(cfg, true);

            // Set out[Entry].
            // TODO Do I need to do this at all?
            Flow.DataflowObject entryInitVal = analysis.newTempVar();
            entryInitVal.setToTop();
            analysis.setEntry(entryInitVal);

            // Set out[B] for each basic block B except Entry.
            while (qit.hasNext()) {
                Quad q = qit.next();

                if (!isEntryQuad(q)) {
                    Flow.DataflowObject blockInitVal = analysis.newTempVar();
                    blockInitVal.setToTop();
                    analysis.setOut(q, analysis.newTempVar());
                }
            }

            boolean changesToAnyOut = false;
            // While changes to any out occur, iterate over basic blocks.
            while (changesToAnyOut) {
                qit = new QuadIterator(cfg, true);

                // For each basic block B different than Entry compute out[B] and in[B].
                while (qit.hasNext()) {
                    Quad q = qit.next();

                    if (!isEntryQuad(q)) {
                        // in[B] = meet over predecessors P of B of out[P]
                        Flow.DataflowObject meetResult = meetOperation(qit.predecessors(), /* out[P] */ true); 
                        analysis.setIn(q, meetResult);

                        // out[B] = f_b(in[B])
                        // TODO Do I need to perform out[B] at all?
                        analysis.processQuad(q); 
                    }
                }
            }
        } else {
            // TODO Analogous but backwards (hasPrevious, setExit etc.).
        }

        // this needs to come last.
        analysis.postprocess(cfg);
    }

    private Flow.DataflowObject meetOperation(Iterator<Quad> entityIt, boolean out) {
        while (entityIt.hasNext()) {
           Quad pred = entityIt.next();
           // TODO
        }

        return null;
    }

    private boolean isEntryQuad(Quad q) {
        // TODO
        return false;
    }
}
