package testutil;

public abstract class AbstractTestWithContext {

    private final TestContext testContext = new TestContext();

    public synchronized void interact() {
        testContext.interact();
    }

    public synchronized void reset() {
        testContext.reset();
    }

    public boolean hasBeenInteractedWith() {
        return testContext.hasBeenInteractedWith();
    }

}
