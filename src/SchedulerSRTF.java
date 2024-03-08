// Start with P1, increase to P2 (compare for shortest and run that), then increase to P3 and compare, 

public class SchedulerSRTF extends SchedulerBase implements Scheduler {
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
