public final class VisualClientFactory {
    private static VisualClient client;

    public static VisualClient getVisualClient() {
        if (client == null) {
            try {
                if (!System.getenv("debug").isEmpty()) {
                    client = new VisualClientImpl();
                } else {
                    client = new VisualClientStub();
                }
            } catch (Throwable e) {
                client = new VisualClientStub();
            }
        }
        return client;
    }
}
