public class SchedulerSJF implements Scheduler {
    // Shortest Job First
    // Use PriorityQueue class (built-in)
    final Platform platform;

    public SchedulerSJF(Platform platform) {
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
