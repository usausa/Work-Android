package usausa.github.io.work.service.transfer;

import java.net.InetAddress;

public class ConnectInformation {

    private final boolean success;

    private final InetAddress address;

    public boolean isSuccess() {
        return success;
    }

    public InetAddress getAddress() {
        return address;
    }

    public ConnectInformation(boolean success, InetAddress address) {

        this.success = success;
        this.address = address;
    }
}
