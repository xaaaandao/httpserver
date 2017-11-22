package functions;

public class Friends {

    String address;
    int portHttp;

    public Friends(String address, int portHttp) {
        address = address.replace("/", "");
        this.address = address;
        this.portHttp = portHttp;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPortHttp() {
        return portHttp;
    }

    public void setPortHttp(int portHttp) {
        this.portHttp = portHttp;
    }

}
