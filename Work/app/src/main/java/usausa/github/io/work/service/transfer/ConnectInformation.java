package usausa.github.io.work.service.transfer;

import java.net.InetAddress;

public class ConnectInformation {

    private final boolean success;

    private final boolean owner;

    private final InetAddress address;

    public boolean isSuccess() {
        return success;
    }

    public boolean isOwner() {
        return owner;
    }

    public InetAddress getAddress() {
        return address;
    }

    public ConnectInformation(boolean success, boolean owner, InetAddress address) {

        this.success = success;
        this.owner = owner;
        this.address = address;
    }
}
