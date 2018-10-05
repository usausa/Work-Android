package usausa.github.io.work.service.transfer;

public class DeviceInformation {

    private final String name;

    private final String address;

    private final int status;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getStatus() {
        return status;
    }

    public DeviceInformation(String name, String address, int status) {
        this.name = name;
        this.address = address;
        this.status = status;
    }
}
