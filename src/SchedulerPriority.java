//import PQ.PQ;


public class SchedulerPriority implements Scheduler {
    // Use PriorityQueue class (built-in) named PQ
    final Platform platform;

    public SchedulerPriority(Platform platform) {
        this.platform = platform;
    }

    
    @Override
    public int getNumberOfContextSwitches() {
        return 0;
    }


    @Override
    public void notifyNewProcess(Process p) {

    }


    @Override
    public Process update(Process cpu) {
        return null;
    }
}
