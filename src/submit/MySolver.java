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

            boolean changesToAnyOut = true;
            // While changes to any out occur, iterate over basic blocks.
            while (changesToAnyOut) {
                qit = new QuadIterator(cfg, true);
                changesToAnyOut = false;

                // For each basic block B different than Entry compute out[B] and in[B].
                while (qit.hasNext()) {
                    Quad q = qit.next();

                    if (!isEntryQuad(q)) {
                        Flow.DataflowObject previousOut = analysis.newTempVar();
                        previousOut.copy(analysis.getOut(q));

                        // in[B] = meet over predecessors P of B of out[P]
                        Flow.DataflowObject meetResult = meetOperation(qit.predecessors(), /* out[P] */ true); 
                        analysis.setIn(q, meetResult);

                        // processQuad also performs the computation:
                        // out[B] = f_b(in[B])
                        analysis.processQuad(q);

                        changesToAnyOut |= (!previousOut.equals(analysis.getOut(q)));
                    }
                }
            }
        } else {
            // TODO Analogous but backwards (hasPrevious, setExit etc.).
        }
        
        // TODO Why Exit doesn't contain result?

        // this needs to come last.
        analysis.postprocess(cfg);
    }

    // Iterator qit can include null value corresponding to Entry/Exit accordingly.
    private Flow.DataflowObject meetOperation(Iterator<Quad> qit, boolean out) {
        Flow.DataflowObject meetResult = analysis.newTempVar();
        Quad q;

        if (qit.hasNext()) {
            q = qit.next();

            // Initialize meetResult with any quad's in/out value.
            if (out) {
                // TODO Is this really needed?
                if (q == null) {
                    meetResult.copy(analysis.getEntry());
                } else {
                  meetResult.copy(analysis.getOut(q));
                } 
            } else {
                if (q == null) {
                    meetResult.copy(analysis.getExit());
                } else {
                   meetResult.copy(analysis.getIn(q));
                }
            }

            // Rely on meet's properties.
            while (qit.hasNext()) {
                q = qit.next();

                if (out) {
                    meetResult.meetWith(analysis.getOut(q));
                } else {
                    meetResult.meetWith(analysis.getIn(q));
                }
            }
        }

        return meetResult;
    }

    // TODO Statics in java?
    private boolean isEntryQuad(Quad q) {
        return q.getID() == 0;
    }

    private boolean isExitQuad(Quad q) {
        return q.getID() == 1;
    }
}
