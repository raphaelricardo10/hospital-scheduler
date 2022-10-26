package services.client;

import java.util.Set;
import java.util.HashSet;

import mock.MockData;
import person.Person;

public class ClientService {
    private Person[] clients;

    public ClientService() throws Exception {
        this.clients = new Person[] { MockData.makePerson("Lucas Ferreira") };
    }

    public Person[] getClients() {
        return this.clients;
    }
}
