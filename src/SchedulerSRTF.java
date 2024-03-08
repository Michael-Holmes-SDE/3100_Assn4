public class SchedulerSRTF implements Scheduler {
    // Shortest Remaining Time First
    final Platform platform;

    public SchedulerSRTF(Platform platform) {
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
