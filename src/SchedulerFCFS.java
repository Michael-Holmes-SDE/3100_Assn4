public class SchedulerFCFS implements Scheduler {
    final Platform platform;

    public SchedulerFCFS(Platform platform) {
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
