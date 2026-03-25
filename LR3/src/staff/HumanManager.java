package staff;

/**
 * HumanManager - Человек
 */
public class HumanManager implements Worker, MeetingParticipant, OrderTask, Slacker, BreakTaker {
    @Override
    public void processOrder() {
        System.out.println("Manager is processing logic...");
    }

    @Override
    public void attendMeeting() {
        System.out.println("Manager is boring at the meeting...");
    }

    @Override
    public void getRest() {
        System.out.println("Manager is taking a break...");
    }

    @Override
    public void swingingTheLead() {
        System.out.println("Manager is watching reels...");
    }

    @Override
    public void performRoutine() {
        processOrder();
        attendMeeting();
        getRest();
        swingingTheLead();
    }
}
