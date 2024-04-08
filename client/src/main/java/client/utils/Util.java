package client.utils;

import client.scenes.MainCtrl;
import jakarta.annotation.Resource;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.client.ClientConfig;

import java.net.InetAddress;

public class Util {

    /**
     * @return the current server address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * @param address  setter for the server address parameter
     */
    public void setAddress(String address) {
        this.address = address;
    }

    protected String serverAddress;

    protected String address;
    @Resource
    protected Client clientBuilder;
    private MainCtrl mainCtrl;

    /**
     * Constructor for Util and all of ServerUtils
     */
    public Util() {
        this.clientBuilder = ClientBuilder.newClient(new ClientConfig());
        this.mainCtrl = new MainCtrl();
        setServerAddress(mainCtrl.getServerAddress());
    }

    /**
     * Getter method for the current ClientBuilder object
     * @return a current ClientBuilder Object
     */
    public Client getClient() {
        return clientBuilder;
    }


    /**
     * Getter method for the current ClientBuilder object
     *
     * @return a current ClientBuilder Object
     */
    public Client getClientBuilder() {
        return clientBuilder;
    }

    /**
     * Setter method for the current ClientBuilder Object.
     * @param clientBuilder The new clientBuilder to change to
     */
    public void setClientBuilder(ClientBuilder clientBuilder) {
        this.clientBuilder = clientBuilder.build();
    }

    /**
     * Sets the address of the server
     * @param newAddress Server address
     * @return boolean whether connection was successful
     */
    public boolean setServerAddress(String newAddress){
        if(!isValidServerAddress(newAddress)){
            return false;
        }

        this.address = newAddress;
        this.serverAddress = "http://" + address + "/";
        return true;
    }

    /**
     * Checks if a server address is valid
     * @param newAddress Address to check
     * @return Boolean whether the address is valid
     */
    private static boolean isValidServerAddress(String newAddress) {
        try {
            InetAddress targetAddress;

            if(newAddress.equals("localhost:8080")){
                targetAddress = InetAddress.getLocalHost();
            }else{
                targetAddress = InetAddress.getByName("http://" + newAddress + "/");
            }

            return targetAddress.isReachable(1500);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets the address of the server
     * @return Returns the server address
     */
    public String getServerAddress() {
        return this.serverAddress;
    }

}
